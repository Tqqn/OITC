package me.tqqn.oitc.events;

import me.tqqn.oitc.managers.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class PlayerShootEvent implements Listener {


    private final GameManager gameManager;

    public PlayerShootEvent(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (!gameManager.isGameRunning()) return;
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        gameManager.startArrowCountdown(player);
    }
}
