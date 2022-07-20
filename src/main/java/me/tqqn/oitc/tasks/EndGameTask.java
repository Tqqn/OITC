package me.tqqn.oitc.tasks;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EndGameTask extends BukkitRunnable {

    private final GameManager gameManager;

    private int endCountdown = 10;

    public EndGameTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (endCountdown == 0) {
            cancel();
            clearPlayerInventory();
            //kickAllPlayers();
            gameManager.setGameState(GameState.RESTARTING);

            return;
        }

        gameManager.broadcast(Messages.GAME_END_COUNTDOWN_MESSAGE.getMessage((String.valueOf(endCountdown))));

        endCountdown--;
    }

    private void clearPlayerInventory() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.SPECTATOR) return;
            player.getInventory().clear();
        }
    }

    private void kickAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(Messages.KICK_PLAYER_ON_ENDGAME.getMessage());
        }
    }
}
