package me.tqqn.oitc.setupwizard.events;

import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.utils.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission(Permissions.JOIN_SETUP_MODE_PERMISSION.getPermission())) return;

        player.kickPlayer(Messages.KICK_PLAYER_WHEN_SETUP_MODE.getMessage());
    }
}
