package me.tqqn.oitc.events;

import me.tqqn.oitc.Messages;
import me.tqqn.oitc.managers.GameManager;
import me.tqqn.oitc.players.PlayerStats;
import me.tqqn.oitc.players.PluginPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final GameManager gameManager;

    public PlayerJoinListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerStats playerStats = new PlayerStats(event.getPlayer().getUniqueId());
        PluginPlayer player = new PluginPlayer(playerStats.getUuid(), event.getPlayer().getDisplayName(), playerStats);
        gameManager.getArena().addPlayerToArena(player);

        int onlinePlayers = Bukkit.getOnlinePlayers().size();

        gameManager.broadcast(Messages.PLAYER_JOIN.getMessage(String.valueOf(onlinePlayers), String.valueOf(gameManager.getArena().getMaximumPlayers()), player.getDisplayName()));
    }
}