package me.tqqn.oitc.setupwizard.events;

import me.tqqn.oitc.items.PluginItems;
import me.tqqn.oitc.setupwizard.SetupManager;
import me.tqqn.oitc.utils.Messages;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerInteractListener implements Listener {

    private final SetupManager setupManager;

    private final List<Location> arenaLocations = new ArrayList<>();

    private final List<Location> powerUpLocations = new ArrayList<>();

    private Location lobbyLocation = null;

    public PlayerInteractListener(SetupManager setupManager) {
        this.setupManager = setupManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!setupManager.isPlayerInSetupMode(player.getUniqueId())) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (PluginItems.SET_LOBBY_LOCATION_ITEM.getItemStack().isSimilar(player.getInventory().getItemInMainHand())) {
                this.lobbyLocation = player.getLocation();
                player.sendMessage(Messages.SELECT_LOBBY_LOCATION_SETUP_MODE.getMessage());
                event.setCancelled(true);
            }
            if (PluginItems.SET_ARENA_LOCATION_ITEM.getItemStack().isSimilar(player.getInventory().getItemInMainHand())) {
                arenaLocations.add(player.getLocation());
                player.sendMessage(Messages.SELECT_ARENA_LOCATION_SETUP_MODE.getMessage());
                event.setCancelled(true);
            }
            if (PluginItems.SET_POWERUP_LOCATIONS_ITEM.getItemStack().isSimilar(player.getInventory().getItemInMainHand())) {
                powerUpLocations.add(player.getLocation().add(0,1,0));
                player.sendMessage(Messages.SELECT_POWERUP_LOCATION_SETUP_MODE.getMessage());
                event.setCancelled(true);
            }
            if (PluginItems.SAVE_ITEM.getItemStack().isSimilar(player.getInventory().getItemInMainHand())) {
                if (lobbyLocation == null) {
                    player.sendMessage(Messages.NO_LOBBY_LOCATION_SETUP_MODE.getMessage());
                    event.setCancelled(true);
                    return;
                }
                player.sendMessage(Messages.SAVE_SETUP_MODE.getMessage());
                setupManager.getPluginConfig().saveLobbyLocation(lobbyLocation);
                setupManager.getPluginConfig().saveArenaLocations(arenaLocations);
                setupManager.getPluginConfig().savePowerUpLocations(powerUpLocations);
                arenaLocations.clear();
                powerUpLocations.clear();
                this.lobbyLocation = null;
                event.setCancelled(true);
            }
        }
    }
}
