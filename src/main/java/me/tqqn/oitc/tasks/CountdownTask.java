package me.tqqn.oitc.tasks;

import me.tqqn.oitc.Messages;
import me.tqqn.oitc.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTask extends BukkitRunnable {

    private int startGameTime = 10;

    private final PlayerManager playerManager;

    public CountdownTask(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public void run() {
        if (startGameTime == 0) {
            sendMessagetoAllPlayers(Messages.GAME_START.getMessage());
            giveAllOnlinePlayersKit();
            cancel();
        }

        sendMessagetoAllPlayers(Messages.GAME_START_COOLDOWN.getMessage() + startGameTime);

        startGameTime--;
    }

    private void sendMessagetoAllPlayers(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    private void giveAllOnlinePlayersKit() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerManager.givePlayerBowAndArrow(player);
        }
    }
}
