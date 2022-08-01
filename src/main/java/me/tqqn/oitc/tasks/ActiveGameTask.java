package me.tqqn.oitc.tasks;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.OITC;
import me.tqqn.oitc.managers.GameManager;
import org.bukkit.scheduler.BukkitRunnable;

public class ActiveGameTask extends BukkitRunnable {

    private int gameDuration;
    private final GameManager gameManager;
    private final OITC plugin;
    private PowerUpCooldownTask powerUpCooldownTask;

    public ActiveGameTask(OITC plugin, GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameDuration = plugin.getPluginConfig().getGameDuration();
        this.plugin = plugin;
    }

    @Override
    public void run() {

        if (!gameManager.isPowerUpSpawnedInArena()) {
            this.powerUpCooldownTask = new PowerUpCooldownTask(gameManager, plugin);
            powerUpCooldownTask.runTaskTimerAsynchronously(plugin, 600, 600);
            gameManager.setArenaPowerUpSpawned(true);
        }

        if (gameDuration == 0) {
            this.powerUpCooldownTask.cancel();
            cancel();
            gameManager.broadcast(Messages.GAME_END_MESSAGE.getMessage());
            gameManager.setGameState(GameState.END);
            return;
        }

        if (gameDuration < 10) {
            gameManager.broadcast(Messages.GAME_END_COUNTDOWN.getMessage(String.valueOf(gameDuration)));
        }

        gameDuration--;



    }
}
