package me.tqqn.oitc.game.gameevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerChangeListener implements Listener {

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
