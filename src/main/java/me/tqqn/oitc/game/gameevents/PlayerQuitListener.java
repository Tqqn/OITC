package me.tqqn.oitc.game.gameevents;

import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.managers.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final GameManager gameManager;

    public PlayerQuitListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        //Removes default Quit message.
        event.setQuitMessage("");

        //Broadcasts custom Quit message and removes player from loaded Scoreboards.
        gameManager.broadcast(Messages.PLAYER_LEAVE.getMessage(event.getPlayer().getDisplayName()));
        gameManager.getScoreboardManager().removePlayerScoreboard(event.getPlayer());
    }

}
