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
    private PluginPlayer winner = null;

    private boolean isPowerUpSpawned;

    private final List<Location> spawnLocations;
    private final List<Location> powerUpLocations;
    @Getter
    private Map<UUID, PluginPlayer> playersInArena = new HashMap<>();

    /**
     * Creates a new arena object that can be used to create an arena.
     *
     * @param displayName The arena name.
     * @param minimumPlayers The minimum players to start.
     * @param maximumPlayers The maximum players to join.
     * @param killsforgametoend The max kills to end the game.
     * @param spawnLocations List of spawn locations.
     * @param powerUpLocations List of powerUp locations.
     */
    public Arena(String displayName, int minimumPlayers, int maximumPlayers, int killsforgametoend, List<Location> spawnLocations, List<Location> powerUpLocations) {
        this.displayName = displayName;
        this.minimumPlayers = minimumPlayers;
        this.maximumPlayers = maximumPlayers;
        this.killsForGameToEnd = killsforgametoend;
        this.spawnLocations = spawnLocations;
        this.powerUpLocations = powerUpLocations;
    }

    /**
     * Add PluginPlayer object to the arena.
     * @param player The PluginPlayer that is joining.
     */
    public void addPlayerToArena(PluginPlayer player) {
        if (playersInArena.containsKey(player.getUuid())) return;
        playersInArena.put(player.getUuid(), player);
    }

    /**
     * Boolean to check if the powerUp is spawned.
     * @return boolean whether the powerUp is spawned or not.
     */
    public boolean isPowerUpSpawned() {
        return this.isPowerUpSpawned;
    }

    /**
     * Set the boolean to true or false.
     * Used when PowerUp is spawning or getting removed.
     * @param isPowerUpSpawned boolean true or false.
     */
    public void setPowerUpSpawned(boolean isPowerUpSpawned) {
        this.isPowerUpSpawned = isPowerUpSpawned;
    }

    /**
     * Returns a random location from the spawnLocations List.
     * @return random location.
     */
    public Location getRandomSpawnLocation() {
        return spawnLocations.get(ThreadLocalRandom.current().nextInt(spawnLocations.size()));
    }

    /**
     * Returns a random location from the powerUpLocations List.
     * @return random location.
     */
    public Location getRandomPowerUpLocation() {
        return powerUpLocations.get(ThreadLocalRandom.current().nextInt(powerUpLocations.size()));
    }

    /**
     * Method to calculate the winner.
     * Used normally when the game ends.
     */
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

    /**
     * Boolean if the arena is full.
     * @return true/false
     */
    public boolean isArenaFull() {
        return playersInArena.size() >= this.maximumPlayers;
    }

    /**
     * Boolean if the arena can start.
     * @return true/false
     */
    public boolean canStart() {
        return playersInArena.size() >= this.minimumPlayers;
    }

    /**
     * Getter to get the PluginPlayer object as value from the playersInArena map.
     * @param uuid map key.
     * @return PluginPlayer.
     */
    public PluginPlayer getPlayerInArena(UUID uuid) {
        return playersInArena.get(uuid);
    }

}
