package me.tqqn.oitc.managers;

import lombok.Getter;
import me.tqqn.oitc.Arena;
import me.tqqn.oitc.GameState;
import me.tqqn.oitc.Messages;
import me.tqqn.oitc.OITC;
import me.tqqn.oitc.config.PluginConfig;
import me.tqqn.oitc.events.PlayerHitListener;
import me.tqqn.oitc.events.PlayerJoinListener;
import me.tqqn.oitc.events.PlayerQuitListener;
import me.tqqn.oitc.tasks.ActiveGameTask;
import me.tqqn.oitc.tasks.CountdownTask;
import me.tqqn.oitc.tasks.EndGameTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class GameManager {

    public GameState gameState = GameState.LOBBY;

    private final OITC plugin;
    @Getter
    private final PlayerManager playerManager;
    @Getter
    private final Arena arena;

    private ActiveGameTask activeGameTask;
    private CountdownTask countdownTask;
    private EndGameTask endGameTask;

    public GameManager(OITC plugin) {
        this.plugin = plugin;
        this.playerManager = new PlayerManager(this);
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
            case STARTING:
                startCountdownToStartGame();
                break;
            case ACTIVE:
                if (this.countdownTask != null) this.countdownTask.cancel();
                activeGameCountdown();
                break;
            case END:
                if (this.activeGameTask != null) this.activeGameTask.cancel();
                endGame();
                break;
            case RESTARTING:
                if (this.endGameTask != null) this.endGameTask.cancel();
                restartGame();
                break;
        }
        this.gameState = gameState;
    }

    private void startCountdownToStartGame() {
        this.countdownTask = new CountdownTask(this);
        this.countdownTask.runTaskTimer(plugin, 0, 20);
    }

    private void activeGameCountdown() {
        this.activeGameTask = new ActiveGameTask(plugin,this);
        this.activeGameTask.runTaskTimer(plugin, 0, 20);
    }

    private void endGame() {
            arena.calculateGameWinner();
            broadcast(Messages.WIN_MESSAGE.getMessage(arena.getWinner().getDisplayName()));
            this.endGameTask = new EndGameTask(this);
            this.endGameTask.runTaskTimer(plugin, 0, 20);
    }

    private void restartGame() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(plugin.getPluginConfig().getLobbyLocation());
        }
    }
    public void broadcast(String message) {
        Bukkit.broadcastMessage(message);
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerHitListener(this),plugin);
        pluginManager.registerEvents(new PlayerJoinListener(this, plugin), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(this), plugin);
    }
}
