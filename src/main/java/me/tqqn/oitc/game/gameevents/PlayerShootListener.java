package me.tqqn.oitc.game.gameevents;

import me.tqqn.oitc.managers.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class PlayerShootListener implements Listener {


    private final GameManager gameManager;

    public PlayerShootListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (!gameManager.isGameRunning()) return;
        if (!(event.getEntity() instanceof Player player)) return;

        if (event.getProjectile().isOnGround()) return;

        gameManager.startArrowCountdown(player);
    }
}
