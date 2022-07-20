package me.tqqn.oitc.events;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.OITC;
import me.tqqn.oitc.managers.GameManager;
import me.tqqn.oitc.players.PlayerStats;
import me.tqqn.oitc.players.PluginPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final GameManager gameManager;
    private final OITC plugin;

    public PlayerJoinListener(GameManager gameManager, OITC plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().teleport(plugin.getPluginConfig().getLobbyLocation());
        PlayerStats playerStats = new PlayerStats(event.getPlayer().getUniqueId());
        PluginPlayer player = new PluginPlayer(playerStats.getUuid(), event.getPlayer().getDisplayName(), playerStats);
        gameManager.getArena().addPlayerToArena(player);

        int onlinePlayers = Bukkit.getOnlinePlayers().size();

        gameManager.broadcast(Messages.PLAYER_JOIN.getMessage(String.valueOf(onlinePlayers), String.valueOf(gameManager.getArena().getMaximumPlayers()), player.getDisplayName()));

        if (onlinePlayers < gameManager.getArena().getMinimumPlayers()) return;
        gameManager.setGameState(GameState.STARTING);
    }
}
