package me.tqqn.oitc.menubuilder;

import me.tqqn.oitc.managers.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Menu matchedMenu = MenuManager.getInstance().matchMenu(event.getWhoClicked().getUniqueId());

        if (matchedMenu == null) return;
        matchedMenu.handleClick(event);
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {
        Menu matchedMenu = MenuManager.getInstance().matchMenu(event.getPlayer().getUniqueId());

        if (matchedMenu != null) {
            // Menu found.
            matchedMenu.handleClose((Player) event.getPlayer());
        }

        // Unregister menu - it has been closed.
        MenuManager.getInstance().unregisterMenu(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        Menu matchedMenu = MenuManager.getInstance().matchMenu(event.getPlayer().getUniqueId());

        if (matchedMenu != null) {
            // Menu found.
            matchedMenu.handleClose(event.getPlayer());
        }

        // Unregister menu - the player has quit.
        MenuManager.getInstance().unregisterMenu(event.getPlayer().getUniqueId());
    }
}
