package me.tqqn.oitc.game.gameevents;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.players.PlayerStats;
import me.tqqn.oitc.utils.Messages;
import me.tqqn.oitc.managers.GameManager;
import me.tqqn.oitc.utils.Sounds;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerHitListener implements Listener {

    private final GameManager gameManager;

    public PlayerHitListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event) {

        //Cancel event when game is not active and return.
        if (!gameManager.isGameActive()) {
            event.setCancelled(true);
            return;
        }

        //Returns when Entity is not a Player or the player is in creative.
        if (!(event.getEntity() instanceof Player player) || player.getGameMode() == GameMode.CREATIVE)
            return;

        //Sets the general damage to 0.
        event.setDamage(0);

        //Return if the damager is not an arrow.
        if (!(event.getDamager() instanceof Arrow arrow))
            return;

        //Return if the shooter of the arrow is not a player.
        if (!(arrow.getShooter() instanceof Player shooter)) return;

        //Return if the damage cause is not projecttile.
        if (!(event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE))
            return;

        //If the player shoots himself, cancel and return.
        if (shooter == player) {
            event.setCancelled(true);
            return;
        }

        PlayerStats shooterStats = gameManager.getPlayerInArena(shooter.getUniqueId()).getPlayerStats();
        PlayerStats hitPlayerStats = gameManager.getPlayerInArena(player.getUniqueId()).getPlayerStats();

        //Adds the stats to the shooter and hit player.
        shooterStats.addKill();
        hitPlayerStats.addDeath();

        //Send kill/death titles to shooter and hit player.
        gameManager.getPlayerManager().sendDeathTitleToPlayer(player);
        gameManager.getPlayerManager().sendKillTitleToPlayer(shooter);

        //If hit player "dies" clear inventory and teleport hit player to random location. If hit player has potionEffects, remove. (PotionEffects from PowerUps).
        player.getInventory().clear();
        Location location = gameManager.getRandomPowerUpLocation();
        player.teleport(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
        player.removePotionEffect(PotionEffectType.SPEED);

        //Broadcast the killMessage to all players. Give the hit player new Bow and arrow, and give the killer a new arrow.
        gameManager.broadcast(Messages.GLOBAL_PLAYER_KILL_MESSAGE.getMessage(player.getDisplayName(), shooter.getDisplayName()));
        gameManager.getPlayerManager().givePlayerBowAndArrow(player);
        gameManager.getPlayerManager().givePlayerArrow(shooter);

        //Play sounds to the killer and hit player.
        Sounds.PLAYER_KILL.playPacketSound(shooter);
        Sounds.PLAYER_DEATH.playPacketSound(player);

        //cancel event, so that hit-player does not take damage when he got shot.
        event.setCancelled(true);

        //Check if the shooter has enough kills to end the game, if so end the game.
        if (!gameManager.isArenaOnMaxKills(shooterStats)) return;

        gameManager.setGameState(GameState.END);
    }
}
