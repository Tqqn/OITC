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
        //Check if the game is active.
        if (!gameManager.isGameActive()) return;
        //Check if the entity is an arrow.
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        //Check if the shooter is a player.
        if (!(arrow.getShooter() instanceof Player player)) return;

        //Remove the arrow when it hit.
        arrow.remove();

        //Checks if the hit entity is a player.
        if (event.getHitEntity() instanceof Player) return;

        //Starts the arrow coolDown runnable for the shooter.
        gameManager.startArrowCountdown(player);
    }
}
