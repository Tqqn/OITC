package me.tqqn.oitc.setupwizard.events;

import me.tqqn.oitc.items.PluginItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem().isSimilar(PluginItems.SET_ARENA_LOCATION_ITEM.getItemStack())
                || event.getCurrentItem().isSimilar(PluginItems.SET_LOBBY_LOCATION_ITEM.getItemStack())
                || event.getCurrentItem().isSimilar(PluginItems.SET_POWERUP_LOCATIONS_ITEM.getItemStack())
                || event.getCurrentItem().isSimilar(PluginItems.SAVE_ITEM.getItemStack())) {
            event.setCancelled(true);
        }
    }
}
