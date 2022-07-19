package me.tqqn.oitc.tasks;

import me.tqqn.oitc.GameState;
import me.tqqn.oitc.Messages;
import me.tqqn.oitc.OITC;
import me.tqqn.oitc.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class ActiveGameTask extends BukkitRunnable {

    private int gameDuration;
    private final GameManager gameManager;

    public ActiveGameTask(OITC plugin, GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameDuration = plugin.getPluginConfig().getGameDuration();
    }

    @Override
    public void run() {
        if (gameDuration == 0) {
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
