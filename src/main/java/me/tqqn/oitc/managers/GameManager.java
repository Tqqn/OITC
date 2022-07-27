package me.tqqn.oitc.managers;

import lombok.Getter;
import me.tqqn.oitc.game.gameevents.PlayerShootListener;
import me.tqqn.oitc.game.Arena;
import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.menubuilder.MenuListener;
import me.tqqn.oitc.players.PlayerStats;
import me.tqqn.oitc.players.PluginPlayer;
import me.tqqn.oitc.tasks.*;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.OITC;
import me.tqqn.oitc.config.PluginConfig;
import me.tqqn.oitc.game.gameevents.PlayerHitListener;
import me.tqqn.oitc.game.gameevents.PlayerJoinListener;
import me.tqqn.oitc.game.gameevents.PlayerQuitListener;
import me.tqqn.oitc.utils.Permissions;
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
    private CountdownTask countdownTask;
    private EndGameTask endGameTask;

    public GameManager(OITC plugin) {
        this.plugin = plugin;
        this.playerManager = new PlayerManager(this);
        this.scoreboardManager = new ScoreboardManager();
        PluginConfig pluginConfig = plugin.getPluginConfig();
        this.arena = new Arena(pluginConfig.getArenaName(),
                pluginConfig.getMinimumPlayers(),
                pluginConfig.getMaximumPlayers(),
                pluginConfig.getKillsToEndGame(),
                pluginConfig.getArenaSpawnLocations());

        registerEvents();
    }

    public void setGameState(GameState gameState) {
        if (this.gameState == gameState) return;
        if (this.gameState == GameState.ACTIVE && gameState == GameState.LOBBY) return;
        if (this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) return;

        switch (gameState) {
            case STARTING -> startCountdownToStartGame();
            case ACTIVE -> {
                if (this.countdownTask != null) this.countdownTask.cancel();
                startGame();
            }
            case END -> {
                if (this.activeGameTask != null) this.activeGameTask.cancel();
                endGame();
            }
            case RESTARTING -> {
                if (this.endGameTask != null) this.endGameTask.cancel();
                restartGame();
            }
        }
        this.gameState = gameState;
    }

    public void addNewPlayerToArena(Player player) {
        PlayerStats playerStats = new PlayerStats(player.getUniqueId());
        PluginPlayer pluginPlayer = new PluginPlayer(player.getUniqueId(), player.getDisplayName(), playerStats);
        this.arena.addPlayerToArena(pluginPlayer);

        if (!scoreboardManager.doesPlayerHaveScoreboard(player.getUniqueId())) {
            scoreboardManager.registerNewPlayerScoreboard(playerStats, player.getUniqueId());
        }

        player.teleport(arena.getRandomSpawnLocation());
    }

    public boolean canJoin(Player player) {
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

    public boolean isArenaOnMaxKills() {
        return arena.isMaxKills();
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

    public void addKillToArenaKills() {
        arena.addKillToCurrentKills();
    }

    public void startArrowCountdown(Player player) {

        PluginPlayer pluginPlayer = arena.getPlayerInArena(player.getUniqueId());

        if (pluginPlayer.isArrowCountdown()) return;
        ArrowCountdownTask arrowXPCountdown = new ArrowCountdownTask(pluginPlayer);
        arrowXPCountdown.runTaskTimerAsynchronously(plugin, 0, 20);
    }

    public boolean isGameRunning() {
        return gameState != GameState.LOBBY && gameState != GameState.STARTING;
    }

    private void startCountdownToStartGame() {
        this.countdownTask = new CountdownTask(this);
        this.countdownTask.runTaskTimer(plugin, 0, 20);
    }

    private void startGame() {
        this.activeGameTask = new ActiveGameTask(plugin,this);
        this.activeGameTask.runTaskTimer(plugin, 0, 20);

        UpdateScoreboardTask updateScoreboardTask = new UpdateScoreboardTask(this);
        updateScoreboardTask.runTaskTimerAsynchronously(plugin, 0, 20);

        for (PluginPlayer pluginPlayer : arena.getPlayersInArena().values()) {
            Player player = Bukkit.getPlayer(pluginPlayer.getUuid());
            if (player == null) return;
            playerManager.givePlayerBowAndArrow(player);
        }
    }

    private void endGame() {
            arena.calculateGameWinner();
            broadcast(Messages.WIN_MESSAGE.getMessage(arena.getWinner().getDisplayName()));
            this.endGameTask = new EndGameTask(this);
            this.endGameTask.runTaskTimer(plugin, 0, 20);
    }

    private void restartGame() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(Messages.KICK_PLAYER_ON_ENDGAME.getMessage());
        }
        Bukkit.getServer().shutdown();
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerHitListener(this),plugin);
        pluginManager.registerEvents(new PlayerJoinListener(this), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(this), plugin);
        pluginManager.registerEvents(new PlayerShootListener(this), plugin);
        pluginManager.registerEvents(new MenuListener(), plugin);
    }
}
