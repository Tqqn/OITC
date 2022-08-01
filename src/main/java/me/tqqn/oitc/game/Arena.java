package me.tqqn.oitc.game;

import lombok.Getter;
import me.tqqn.oitc.players.PluginPlayer;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Arena {

    @Getter
    private final String displayName;
    @Getter
    private final int minimumPlayers;
    @Getter
    private final int maximumPlayers;
    @Getter
    private final int killsForGameToEnd;
    @Getter
    private int currentKills = 0;
    @Getter
    private PluginPlayer winner = null;

    private boolean isPowerUpSpawned;

    private final List<Location> spawnLocations;
    private final List<Location> powerUpLocations;
    @Getter
    private Map<UUID, PluginPlayer> playersInArena = new HashMap<>();

    public Arena(String displayName, int minimumPlayers, int maximumPlayers, int killsforgametoend, List<Location> spawnLocations, List<Location> powerUpLocations) {
        this.displayName = displayName;
        this.minimumPlayers = minimumPlayers;
        this.maximumPlayers = maximumPlayers;
        this.killsForGameToEnd = killsforgametoend;
        this.spawnLocations = spawnLocations;
        this.powerUpLocations = powerUpLocations;
    }

    public void addPlayerToArena(PluginPlayer player) {
        if (playersInArena.containsKey(player.getUuid())) return;
        playersInArena.put(player.getUuid(), player);
    }

    public boolean isPowerUpSpawned() {
        return this.isPowerUpSpawned;
    }

    public void setPowerUpSpawned(boolean isPowerUpSpawned) {
        this.isPowerUpSpawned = isPowerUpSpawned;
    }

    public Location getRandomSpawnLocation() {
        return spawnLocations.get(ThreadLocalRandom.current().nextInt(spawnLocations.size()));
    }

    public Location getRandomPowerUpLocation() {
        return powerUpLocations.get(ThreadLocalRandom.current().nextInt(powerUpLocations.size()));
    }

    public void addKillToCurrentKills() {
        this.currentKills = (this.currentKills+1);
    }

    public void calculateGameWinner() {
        PluginPlayer winner = null;

        for (PluginPlayer player : this.playersInArena.values()) {
            if (winner == null) {
                winner = player;
            }
            if (player.getPlayerStats().getKills() > winner.getPlayerStats().getKills()) {
                winner = player;
            }
            this.winner = winner;
        }
    }

    public boolean isArenaFull() {
        return playersInArena.size() >= this.maximumPlayers;
    }
    public boolean canStart() {
        return playersInArena.size() >= this.minimumPlayers;
    }

    public PluginPlayer getPlayerInArena(UUID uuid) {
        return playersInArena.get(uuid);
    }

}
