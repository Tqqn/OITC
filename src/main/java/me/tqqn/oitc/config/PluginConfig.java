package me.tqqn.oitc.config;

import me.tqqn.oitc.OITC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class PluginConfig {

    private final OITC plugin;

    public PluginConfig(OITC plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public String getDataBaseName() {
        return plugin.getConfig().getString("database.name");
    }
    public String getDataBaseHost() {
        return plugin.getConfig().getString("database.host");
    }
    public int getDataBasePort() {
        return plugin.getConfig().getInt("database.port");
    }
    public String getDataBaseUsername() {
        return plugin.getConfig().getString("database.username");
    }
    public String getDataBasePassword() {
        return plugin.getConfig().getString("database.password");
    }

    public String getArenaName() {
        return plugin.getConfig().getString("arena.name");
    }
    public int getMinimumPlayers() {
        return plugin.getConfig().getInt("arena.minimumPlayers");
    }
    public int getMaximumPlayers() {
        return plugin.getConfig().getInt("arena.maximumPlayers");
    }
    public int getKillsToEndGame() {
        return plugin.getConfig().getInt("arena.killsToEndGame");
    }
    public List<Location> getArenaSpawnLocations() {
        List<Location> spawnlocs = new ArrayList<>();
        for (String locationkey : plugin.getConfig().getConfigurationSection("arena.spawn-locations.").getKeys(false)) {
            spawnlocs.add(getArenaSpawnLocation(locationkey));
        }
        return spawnlocs;
    }

    public Location getArenaSpawnLocation(String key) {
        return new Location(
                getArenaWorld(),
                getLocationX(key),
                getLocationY(key),
                getLocationZ(key),
                getLocationPitch(key),
                getLocationYaw(key));
    }

    private double getLocationX(String key) {
        return plugin.getConfig().getDouble("arena.spawn-locations." + key + ".x");
    }
    private double getLocationY(String key) {
        return plugin.getConfig().getDouble("arena.spawn-locations." + key + ".y");
    }
    private double getLocationZ(String key) {
        return plugin.getConfig().getDouble("arena.spawn-locations." + key + ".z");
    }
    private float getLocationPitch(String key) {
        return (float) plugin.getConfig().getDouble("arena.spawn-locations." + key + ".pitch");
    }
    private float getLocationYaw(String key) {
        return (float) plugin.getConfig().getDouble("arena.spawn-locations." + key + ".yaw");
    }
    private World getArenaWorld() {
        return Bukkit.getWorld(plugin.getConfig().getString("arena.world"));
    }

    public Location getLobbyLocation() {
        return new Location(
                Bukkit.getWorld(plugin.getConfig().getString("lobby.world")),
                plugin.getConfig().getDouble("lobby.x"),
                plugin.getConfig().getDouble("lobby.y"),
                plugin.getConfig().getDouble("lobby.z"),
                (float) plugin.getConfig().getDouble("lobby.pitch"),
                (float) plugin.getConfig().getDouble("lobby.yaw"));
    }

    public int getGameDuration() {
        return plugin.getConfig().getInt("game-duration");
    }
}
