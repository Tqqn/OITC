package me.tqqn.oitc.events;

import me.tqqn.oitc.Messages;
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
        gameManager.broadcast(Messages.PLAYER_LEAVE.getMessage(event.getPlayer().getDisplayName()));
    }
}
