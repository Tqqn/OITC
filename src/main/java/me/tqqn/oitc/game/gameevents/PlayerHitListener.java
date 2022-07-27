package me.tqqn.oitc.game.gameevents;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.players.PlayerStats;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.managers.GameManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerHitListener implements Listener {

    private final GameManager gameManager;

    public PlayerHitListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player player))
            return;

        event.setDamage(0);
        if (!(event.getDamager() instanceof Arrow arrow))
            return;

        if (!(arrow.getShooter() instanceof Player shooter)) return;

        if (!(event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE))
            return;

        PlayerStats shooterstats = gameManager.getPlayerInArena(shooter.getUniqueId()).getPlayerStats();
        PlayerStats hitplayerstats = gameManager.getPlayerInArena(player.getUniqueId()).getPlayerStats();

        shooterstats.addKill();
        hitplayerstats.addDeath();

        player.getInventory().clear();
        player.teleport(gameManager.getRandomArenaSpawnLocation());

        gameManager.broadcast(Messages.GLOBAL_PLAYER_KILL_MESSAGE.getMessage(player.getDisplayName(), shooter.getDisplayName()));

        gameManager.getPlayerManager().givePlayerBowAndArrow(player);

        gameManager.getPlayerManager().givePlayerArrow(shooter);

        gameManager.addKillToArenaKills();

        if (!gameManager.isArenaOnMaxKills()) return;

        gameManager.setGameState(GameState.END);

            event.setCancelled(true);
    }
}
