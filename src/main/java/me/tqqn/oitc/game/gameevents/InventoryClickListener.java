package me.tqqn.oitc.game.gameevents;

import me.tqqn.oitc.items.PluginItems;
import me.tqqn.oitc.managers.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final GameManager gameManager;

    public InventoryClickListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    //Default listener to block Inventory clicking, but only when the game is active.
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!gameManager.isGameActive()) return;
        event.setCancelled(true);
    }
}
