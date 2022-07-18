package me.tqqn.oitc.managers;

import me.tqqn.oitc.GameState;
import me.tqqn.oitc.OITC;
import me.tqqn.oitc.tasks.CountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GameManager {

    public GameState gameState = GameState.LOBBY;

    private final OITC plugin;
    private final PlayerManager playerManager;

    public GameManager(OITC plugin) {
        this.plugin = plugin;
        this.playerManager = new PlayerManager(this);
    }

    public void setGameState(GameState gameState) {
        if (this.gameState == gameState) return;
        if (this.gameState == GameState.ACTIVE && gameState == GameState.LOBBY) return;
        if (this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) return;

        switch (gameState) {
            case STARTING:
                startGame();
            case ACTIVE:
            case END:
            case RESTARTING:
                break;
        }
        this.gameState = gameState;
    }

    public void startGame() {
        CountdownTask countdownTask = new CountdownTask(playerManager);
        countdownTask.run();
    }

}
