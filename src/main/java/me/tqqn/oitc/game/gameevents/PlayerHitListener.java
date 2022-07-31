package me.tqqn.oitc.game.gameevents;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.players.PlayerStats;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.managers.GameManager;
import me.tqqn.oitc.utils.Sounds;
import org.bukkit.GameMode;
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

        if (!gameManager.isGameActive()) {
            event.setCancelled(true);
            return;
        }

        if (!(event.getEntity() instanceof Player player) || player.getGameMode() == GameMode.CREATIVE)
            return;

        event.setDamage(0);
        if (!(event.getDamager() instanceof Arrow arrow))
            return;

        if (!(arrow.getShooter() instanceof Player shooter)) return;

        if (!(event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE))
            return;

        if (shooter == player) {
            event.setCancelled(true);
            return;
        }

        PlayerStats shooterStats = gameManager.getPlayerInArena(shooter.getUniqueId()).getPlayerStats();
        PlayerStats hitPlayerStats = gameManager.getPlayerInArena(player.getUniqueId()).getPlayerStats();

        shooterStats.addKill();
        hitPlayerStats.addDeath();

        gameManager.getPlayerManager().sendDeathTitleToPlayer(player);
        gameManager.getPlayerManager().sendKillTitleToPlayer(shooter);

        player.getInventory().clear();
        player.teleport(gameManager.getRandomArenaSpawnLocation());

        gameManager.broadcast(Messages.GLOBAL_PLAYER_KILL_MESSAGE.getMessage(player.getDisplayName(), shooter.getDisplayName()));

        gameManager.getPlayerManager().givePlayerBowAndArrow(player);

        gameManager.getPlayerManager().givePlayerArrow(shooter);

        gameManager.addKillToArenaKills();

        Sounds.PLAYER_KILL.playPacketSound(shooter);
        Sounds.PLAYER_DEATH.playPacketSound(player);

        event.setCancelled(true);

        if (!gameManager.isArenaOnMaxKills(shooterStats)) return;

        gameManager.setGameState(GameState.END);
    }
}
