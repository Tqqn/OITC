package me.tqqn.oitc.events;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.players.PlayerStats;
import me.tqqn.oitc.players.PluginPlayer;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.managers.GameManager;
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
        if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player)) return;
        if (!(event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE)) return;

        Player shooter = (Player) event.getDamager();
        Player hitPlayer = (Player) event.getEntity();

        PlayerStats shooterstats = gameManager.getPlayerInArena(shooter.getUniqueId()).getPlayerStats();
        PlayerStats hitplayerstats = gameManager.getPlayerInArena(hitPlayer.getUniqueId()).getPlayerStats();

        shooterstats.addKill();
        hitplayerstats.addDeath();

        hitPlayer.getInventory().clear();
        hitPlayer.teleport(gameManager.getRandomArenaSpawnLocation());

        gameManager.broadcast(Messages.GLOBAL_PLAYER_KILL_MESSAGE.getMessage(hitPlayer.getDisplayName(), shooter.getDisplayName()));

        gameManager.getPlayerManager().givePlayerBowAndArrow(hitPlayer);

        gameManager.getPlayerManager().givePlayerArrow(shooter);

        gameManager.addKillToArenaKills();

        if (!gameManager.isArenaOnMaxKills()) return;
            gameManager.setGameState(GameState.END);
    }
}
