package me.tqqn.oitc.game.gameevents;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final GameManager gameManager;

    public PlayerJoinListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        //Replaces the default joinMessage to empty.
        event.setJoinMessage("");

        Player player = event.getPlayer();

        //Check if player can join. If not kick player.
        if (gameManager.canJoin(player)) {

            //Clear joined players inventory, add player to the arena.
            player.getInventory().clear();
            gameManager.addNewPlayerToArena(player);

            //Teleport joined player to the lobby.
            player.teleport(gameManager.getLobbyLocation());

            int onlinePlayers = Bukkit.getOnlinePlayers().size();

            //Broadcast the new joinMessage.
            gameManager.broadcast(Messages.PLAYER_JOIN.getMessage(String.valueOf(onlinePlayers), String.valueOf(gameManager.getArenaMaxPlayers()), player.getDisplayName()));

            //Check if the game can start.
            if (!gameManager.canGameStart()) return;
            gameManager.setGameState(GameState.STARTING);

        } else {
            player.kickPlayer(Messages.KICK_PLAYER_WHEN_ARENA_FULL.getMessage());
        }
    }
}
