package me.tqqn.oitc.powerups;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public abstract class PowerUp {

    private final ItemStack displayItem;
    private final String displayName;
    private final Location location;
    private boolean isSpawned;

    private Item item;

    private Player nearestPlayer;

    public PowerUp(ItemStack displayItem, String displayName, Location location) {
        this.displayItem = displayItem;
        this.displayName = displayName;
        this.location = location;
    }

    public ItemStack getDisplayItem() {
        return this.displayItem;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setPowerUp() {
        this.item = location.getWorld().dropItem(location, displayItem);
        item.setGravity(false);
        item.setCanMobPickup(false);
        item.setCanPlayerPickup(false);
        item.setGlowing(true);
        item.setInvulnerable(true);
        this.isSpawned = true;
    }

    private void remove() {
        item.remove();
        this.isSpawned = false;
    }

    public boolean isPowerUpSpawned() {
        return isSpawned;
    }

    public void pickUp() {
        if (!isSpawned) return;

        Collection<LivingEntity> entities = location.getNearbyLivingEntities(0.5);

        Player nearestPlayer = null;

        if (entities.isEmpty()) return;

        for (LivingEntity livingEntity : entities) {
            if (!(livingEntity instanceof Player player)) return;

            if (nearestPlayer == null) {
                nearestPlayer = player;
            }

            if (location.distance(player.getLocation()) > location.distance(nearestPlayer.getLocation())) {
                nearestPlayer = player;
            }

            this.nearestPlayer = nearestPlayer;

            givePowerUp(nearestPlayer);
            remove();
        }
    }

    public abstract void givePowerUp(Player player);

}
