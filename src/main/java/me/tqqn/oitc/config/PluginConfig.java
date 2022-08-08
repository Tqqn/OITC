package me.tqqn.oitc.config;

import me.tqqn.oitc.OITC;
import me.tqqn.oitc.game.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class PluginConfig {

    private final OITC plugin;

    public PluginConfig(OITC plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public String getMode() {
        return plugin.getConfig().getString("plugin-mode");
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
        return Integer.parseInt(plugin.getConfig().getString("arena.minimumPlayers"));
    }
    public int getMaximumPlayers() {
        return Integer.parseInt(plugin.getConfig().getString("arena.maximumPlayers"));
    }
    public int getKillsToEndGame() {
        return Integer.parseInt(plugin.getConfig().getString("arena.killsToEndGame"));
    }

    /**
     * Gets all arena spawnLocations from the config.
     * Returns a list with all arena spawnLocations.
     * @return List<Location>
     */
    public List<Location> getArenaSpawnLocations() {
        List<Location> spawnlocs = new ArrayList<>();
        for (String locationkey : plugin.getConfig().getConfigurationSection("arena.spawn-locations.").getKeys(false)) {
            spawnlocs.add(getArenaSpawnLocation(locationkey));
        }
        return spawnlocs;
    }

    /**
     * Gets all powerUp spawnLocations from the config.
     * Returns a list with all powerUp spawnLocations.
     * @return List<Location>
     */
    public List<Location> getPowerUpLocations() {
        List<Location> powerUpLocs = new ArrayList<>();
        for (String locationkey : plugin.getConfig().getConfigurationSection("arena.powerup-locations").getKeys(false)) {
            powerUpLocs.add(getPowerUpLocation(locationkey));
        }
        return powerUpLocs;
    }

    private Location getArenaSpawnLocation(String key) {
        return new Location(
                Bukkit.getWorld(plugin.getConfig().getString("arena.world")),
                plugin.getConfig().getDouble("arena.spawn-locations." + key + ".x"),
                plugin.getConfig().getDouble("arena.spawn-locations." + key + ".y"),
                plugin.getConfig().getDouble("arena.spawn-locations." + key + ".z"),
                (float) plugin.getConfig().getDouble("arena.spawn-locations." + key + ".pitch"),
                (float) plugin.getConfig().getDouble("arena.spawn-locations." + key + ".yaw"));
    }

    private Location getPowerUpLocation(String key) {
        return new Location(
                Bukkit.getWorld(plugin.getConfig().getString("arena.world")),
                plugin.getConfig().getDouble("arena.powerup-locations." + key + ".x"),
                plugin.getConfig().getDouble("arena.powerup-locations." + key + ".y"),
                plugin.getConfig().getDouble("arena.powerup-locations." + key + ".z"),
                (float) plugin.getConfig().getDouble("arena.powerup-locations." + key + ".pitch"),
                (float) plugin.getConfig().getDouble("arena.powerup-locations." + key + ".yaw"));
    }

    //Gets the lobby spawn location from config.
    public Location getLobbyLocation() {
        return new Location(
                Bukkit.getWorld(plugin.getConfig().getString("lobby.world")),
                plugin.getConfig().getDouble("lobby.x"),
                plugin.getConfig().getDouble("lobby.y"),
                plugin.getConfig().getDouble("lobby.z"),
                (float) plugin.getConfig().getDouble("lobby.pitch"),
                (float) plugin.getConfig().getDouble("lobby.yaw"));
    }

    //Saves the List<Location> to the config.
    public void saveArenaLocations(List<Location> spawnLocations) {
        int i = 0;
        plugin.getConfig().set("arena.world", spawnLocations.get(0).getWorld().getName());
        for (Location location : spawnLocations) {
            plugin.getConfig().set("arena.spawn-locations." + i + ".x", location.getX());
            plugin.getConfig().set("arena.spawn-locations." + i + ".y", location.getY());
            plugin.getConfig().set("arena.spawn-locations." + i + ".z", location.getZ());
            plugin.getConfig().set("arena.spawn-locations." + i + ".pitch", location.getPitch());
            plugin.getConfig().set("arena.spawn-locations." + i + ".yaw", location.getYaw());
            i++;
            plugin.saveConfig();
        }
    }
    //Saves the List<Location> to the config.
    public void savePowerUpLocations(List<Location> powerUpLocations) {
        int i = 0;
        plugin.getConfig().set("arena.world", powerUpLocations.get(0).getWorld().getName());
        for (Location location : powerUpLocations) {
            plugin.getConfig().set("arena.powerup-locations." + i + ".x", location.getX());
            plugin.getConfig().set("arena.powerup-locations." + i + ".y", location.getY());
            plugin.getConfig().set("arena.powerup-locations." + i + ".z", location.getZ());
            plugin.getConfig().set("arena.powerup-locations." + i + ".pitch", location.getPitch());
            plugin.getConfig().set("arena.powerup-locations." + i + ".yaw", location.getYaw());
            i++;
            plugin.saveConfig();
        }
    }
    //Saves the Location to the config.
    public void saveLobbyLocation(Location location) {
        plugin.getConfig().set("lobby.world", location.getWorld().getName());
        plugin.getConfig().set("lobby.x", location.getX());
        plugin.getConfig().set("lobby.y", location.getY());
        plugin.getConfig().set("lobby.z", location.getZ());
        plugin.getConfig().set("lobby.pitch", location.getPitch());
        plugin.getConfig().set("lobby.yaw", location.getYaw());

        plugin.saveConfig();
    }

    public int getGameDuration() {
        return plugin.getConfig().getInt("game-duration");
    }
}
