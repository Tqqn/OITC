package me.tqqn.oitc.managers;

import me.tqqn.oitc.players.PlayerStats;
import me.tqqn.oitc.utils.PluginScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    private final Map<UUID, PluginScoreboard> pluginScoreboardMap = new HashMap<>();

    public void registerNewPlayerScoreboard(PlayerStats playerStats, UUID uuid) {
        PluginScoreboard pluginScoreboard = new PluginScoreboard(playerStats, uuid);
        pluginScoreboard.setPlayerScoreboard();
        pluginScoreboardMap.put(uuid, pluginScoreboard);
    }

    public void removePlayerScoreboard(Player player) {
        if (!doesPlayerHaveScoreboard(player.getUniqueId())) return;
        PluginScoreboard pluginScoreboard = pluginScoreboardMap.get(player.getUniqueId());
        pluginScoreboard.removeScoreboard(player);
        pluginScoreboardMap.remove(player.getUniqueId());
    }

    public PluginScoreboard getPluginScoreboard(UUID uuid) {
        return pluginScoreboardMap.get(uuid);
    }

    public boolean doesPlayerHaveScoreboard(UUID uuid) {
        return pluginScoreboardMap.containsKey(uuid);
    }

    public void clearScoreboards() {
        pluginScoreboardMap.values().forEach(pluginScoreboard -> pluginScoreboard.removeScoreboard(pluginScoreboard.getPlayer()));
    }
}
