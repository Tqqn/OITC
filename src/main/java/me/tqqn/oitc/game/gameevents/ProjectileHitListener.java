package me.tqqn.oitc.game.gameevents;

import me.tqqn.oitc.managers.GameManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {

    private final GameManager gameManager;

    public ProjectileHitListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!gameManager.isGameActive()) return;
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player player)) return;

        arrow.remove();

        if (event.getHitEntity() instanceof Player) return;

        gameManager.startArrowCountdown(player);
    }
}
