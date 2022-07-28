package me.tqqn.oitc.setupwizard.events;

import me.tqqn.oitc.setupwizard.SetupManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final SetupManager setupManager;

    public PlayerQuitListener(SetupManager setupManager) {
        this.setupManager = setupManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!setupManager.isPlayerInSetupMode(player.getUniqueId())) return;

        setupManager.removeSetupItems(player);
    }
}
