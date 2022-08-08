package me.tqqn.oitc.managers;

import lombok.Getter;
import me.tqqn.oitc.game.gameevents.*;
import me.tqqn.oitc.game.Arena;
import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.menubuilder.MenuListener;
import me.tqqn.oitc.players.PlayerStats;
import me.tqqn.oitc.players.PluginPlayer;
import me.tqqn.oitc.tasks.*;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.OITC;
import me.tqqn.oitc.config.PluginConfig;
import me.tqqn.oitc.utils.Permissions;
import me.tqqn.oitc.utils.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.UUID;

public class GameManager {

    @Getter
    public GameState gameState = GameState.LOBBY;

    private final OITC plugin;
    @Getter
    private final PlayerManager playerManager;
    @Getter
    private final ScoreboardManager scoreboardManager;
    private final Arena arena;

    private ActiveGameTask activeGameTask;
    private PowerUpCooldownTask powerUpCooldownTask;
    private CountdownTask countdownTask;
    private EndGameTask endGameTask;

    /**
     * GameManager that manages the game functions.
     * @param plugin Main class instance.
     */
    public GameManager(OITC plugin) {
        this.plugin = plugin;
        this.playerManager = new PlayerManager();
        this.scoreboardManager = new ScoreboardManager();
        PluginConfig pluginConfig = plugin.getPluginConfig();
        this.arena = new Arena(pluginConfig.getArenaName(),
                pluginConfig.getMinimumPlayers(),
                pluginConfig.getMaximumPlayers(),
                pluginConfig.getKillsToEndGame(),
                pluginConfig.getArenaSpawnLocations(),
                pluginConfig.getPowerUpLocations());
        registerEvents();
    }

    /**
     * Sets the state that is given.
     * @param gameState GameState enum.
     */
    public void setGameState(GameState gameState) {
        //Checks if the new gameState is the same as the old gameState.
        if (this.gameState == gameState) return;
        //Checks if the current gameState is active and the new gameState is not lobby or starting.
        if (this.gameState == GameState.ACTIVE && gameState == GameState.LOBBY) return;
        if (this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) return;

        //Switch statement to handle the current GameState.
        switch (gameState) {
            case STARTING -> startCountdownToStartGame();
            case ACTIVE -> {
                if (this.countdownTask != null) this.countdownTask.cancel();
                startGame();
            }
            case END -> {
                if (this.activeGameTask != null) this.activeGameTask.cancel();
                if (this.powerUpCooldownTask != null) this.powerUpCooldownTask.cancel();
                endGame();
            }
            case RESTARTING -> {
                if (this.endGameTask != null) this.endGameTask.cancel();
                restartGame();
            }
        }
        this.gameState = gameState;
    }

    /**
     * Void method to add a new Player to the arena.
     * @param player Player that joins.
     */
    public void addNewPlayerToArena(Player player) {
        PlayerStats playerStats = new PlayerStats(player.getUniqueId());
        PluginPlayer pluginPlayer = new PluginPlayer(player.getUniqueId(), player.getDisplayName(), playerStats);
        this.arena.addPlayerToArena(pluginPlayer);

        if (!scoreboardManager.doesPlayerHaveScoreboard(player.getUniqueId())) {
            scoreboardManager.registerNewPlayerScoreboard(playerStats, player.getUniqueId());
        }
    }

    /**
     * Boolean to check if the player is allowed to join.
     * @param player Player that joins.
     * @return true/false
     */
    public boolean canJoin(Player player) {
        if (player.hasPermission(Permissions.JOIN_ARENA_ACTIVE_PERMISSION.getPermission())) return true;
        if (isGameRunning()) return false;
        if (!arena.isArenaFull()) return true;

        return player.hasPermission(Permissions.JOIN_ARENA_FULL_PERMISSION.getPermission());
    }

    public void broadcast(String message) {
        Bukkit.broadcastMessage(message);
    }

    public Location getRandomArenaSpawnLocation() {
        return arena.getRandomSpawnLocation();
    }

    public Location getRandomPowerUpLocation() {
        return arena.getRandomPowerUpLocation();
    }

    public Location getLobbyLocation() {
        return plugin.getPluginConfig().getLobbyLocation();
    }

    /**
     * Boolean check if the arena is on its maxKills.
     * @param playerStats Current game stats of the Player.
     * @return true/false
     */
    public boolean isArenaOnMaxKills(PlayerStats playerStats) {
        return (playerStats.getKills() >= arena.getKillsForGameToEnd());
    }

    public boolean canGameStart() {
        return arena.canStart();
    }

    public int getArenaMaxPlayers() {
        return arena.getMaximumPlayers();
    }

    public PluginPlayer getPlayerInArena(UUID uuid) {
        return arena.getPlayerInArena(uuid);
    }

    public boolean isPowerUpSpawnedInArena() {
        return arena.isPowerUpSpawned();
    }

    public void setArenaPowerUpSpawned(boolean isPowerUpSpawned) {
        arena.setPowerUpSpawned(isPowerUpSpawned);
    }

    /**
     * Start the Arrow Countdown Runnable/Task.
     * @param player Player that shot his arrow and it missed.
     */
    public void startArrowCountdown(Player player) {

        PluginPlayer pluginPlayer = arena.getPlayerInArena(player.getUniqueId());

        //Check if player has already a countdown active.
        if (pluginPlayer.isArrowCountdown()) return;

        ArrowCountdownTask arrowXPCountdown = new ArrowCountdownTask(pluginPlayer);
        //Run arrowCountdownTask for given player, that will run every 20 ticks (every second).
        arrowXPCountdown.runTaskTimerAsynchronously(plugin, 0, 20);
    }

    public boolean isGameRunning() {
        return gameState != GameState.LOBBY && gameState != GameState.STARTING;
    }

    public boolean isGameActive() {
        return gameState == GameState.ACTIVE;
    }

    /**
     * Start the Start Game Countdown Runnable/Task
     * This runnable will usually run when the game can start. This will run every second.
     */
    private void startCountdownToStartGame() {
        this.countdownTask = new CountdownTask(this);
        this.countdownTask.runTaskTimer(plugin, 0, 20);
    }

    /**
     * Void method to start the game.
     */
    private void startGame() {
        //ActiveGameTask/Runnable that runs every second when the game started.
        this.activeGameTask = new ActiveGameTask(plugin, this);
        this.activeGameTask.runTaskTimer(plugin, 0, 20);

        //UpdateScoreboardTask, this will run every second to update the player scoreboards.
        UpdateScoreboardTask updateScoreboardTask = new UpdateScoreboardTask(this);
        updateScoreboardTask.runTaskTimerAsynchronously(plugin, 0, 20);

        //Teleport every player that is in the game to a random location. After they have been teleported, give them the Bow and Arrow.
        arena.getPlayersInArena().values().forEach(pluginPlayer -> {
            Player player = Bukkit.getPlayer(pluginPlayer.getUuid());
            if (player == null) return;

            Location location = getRandomArenaSpawnLocation();

            player.teleport(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
            playerManager.givePlayerBowAndArrow(player);
        });

        //This task is managing the coolDown between PowerUps.
        this.powerUpCooldownTask = new PowerUpCooldownTask(this, plugin);
        powerUpCooldownTask.runTaskTimer(plugin, 0, 20);

        //Sends a sound to all players. And broadcast the start of the game.
        Bukkit.getOnlinePlayers().forEach(Sounds.GAME_START::playPacketSound);
        broadcast(Messages.GAME_START.getMessage());
    }

    /**
     * Void method to end the game.
     */
    private void endGame() {
        //Calculate the winner, and broadcast it. Send a title to the player that won. Activate the endGameTask.
        arena.calculateGameWinner();
        broadcast(Messages.WIN_MESSAGE.getMessage(arena.getWinner().getDisplayName()));
        if (Bukkit.getPlayer(arena.getWinner().getUuid()) != null) {
            Bukkit.getPlayer(arena.getWinner().getUuid()).sendTitle(Messages.TITLE_PLAYER_WIN.getMessage(), "", 20, 70, 20);
        }
        this.endGameTask = new EndGameTask(this);
        this.endGameTask.runTaskTimer(plugin, 0, 20);
    }

    /**
     * Void method to restart the game.
     */
    private void restartGame() {
        //Get all online players and kick them. After that shutdown the server.
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(Messages.KICK_PLAYER_ON_ENDGAME.getMessage()));
        Bukkit.getServer().shutdown();
    }

    /**
     * Method to register the game events.
     */
    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerHitListener(this), plugin);
        pluginManager.registerEvents(new PlayerJoinListener(this), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(this), plugin);
        pluginManager.registerEvents(new ProjectileHitListener(this), plugin);
        pluginManager.registerEvents(new MenuListener(), plugin);
        pluginManager.registerEvents(new CreatureSpawnListener(), plugin);
        pluginManager.registerEvents(new PlayerDamageListener(), plugin);
        pluginManager.registerEvents(new InventoryClickListener(this), plugin);
        pluginManager.registerEvents(new ItemDropListener(), plugin);
        pluginManager.registerEvents(new HungerChangeListener(), plugin);
        pluginManager.registerEvents(new WeatherChangeListener(), plugin);
    }
}
