package me.tqqn.oitc.setupwizard.events;

import me.tqqn.oitc.items.PluginItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().isSimilar(PluginItems.SET_ARENA_LOCATION_ITEM.getItemStack())
                || event.getItemDrop().getItemStack().isSimilar(PluginItems.SET_LOBBY_LOCATION_ITEM.getItemStack())
                || event.getItemDrop().getItemStack().isSimilar(PluginItems.SET_POWERUP_LOCATIONS_ITEM.getItemStack())
                || event.getItemDrop().getItemStack().isSimilar(PluginItems.SAVE_ITEM.getItemStack())) {
            event.setCancelled(true);
        }
    }
}
