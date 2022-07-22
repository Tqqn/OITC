package me.tqqn.oitc.tasks;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.managers.GameManager;
import me.tqqn.oitc.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTask extends BukkitRunnable {

    private int startGameTime = 10;

    private final PlayerManager playerManager;

    private final GameManager gameManager;

    public CountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
        this.playerManager = gameManager.getPlayerManager();
    }

    @Override
    public void run() {
        if (startGameTime == 0) {
            cancel();
            gameManager.broadcast(Messages.GAME_START.getMessage());
            gameManager.setGameState(GameState.ACTIVE);
            return;
        }

        gameManager.broadcast(Messages.GAME_START_COUNTDOWN.getMessage(String.valueOf(startGameTime)));

        startGameTime--;
    }
}
