package me.tqqn.oitc.powerups;

import me.tqqn.oitc.packets.PowerUpPacket;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class PowerUp {

    private final ItemStack displayItem;
    private final String displayName;
    private final Location location;
    private boolean isSpawned;
    private final Item item;

    private Map<UUID, PowerUpPacket> loadedPacketEntities = new HashMap<>();

    public PowerUp(ItemStack displayItem, String displayName, Location location) {
        this.displayItem = displayItem;
        this.displayName = displayName;
        this.location = location;
        this.item = location.getWorld().dropItem(location, displayItem);
    }

    public void addSpawnedEntityToMap(Player player, PowerUpPacket powerUpPacket) {
        loadedPacketEntities.put(player.getUniqueId(), powerUpPacket);
    }

    public void removeSpawnedEntityFromMap(Player player) {
        loadedPacketEntities.remove(player.getUniqueId());
    }

    public boolean doesExistInSpawnEntityMap(Player player) {
        return loadedPacketEntities.containsKey(player.getUniqueId());
    }

    public ItemStack getDisplayItem() {
        return this.displayItem;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setPowerUp() {
        item.setCanMobPickup(false);
        item.setCanPlayerPickup(false);
        item.setGlowing(true);
        item.setInvulnerable(true);

        this.isSpawned = true;

        Bukkit.getOnlinePlayers().forEach(player -> {
            PowerUpPacket powerUpPacket = new PowerUpPacket(player, item, location, displayName, this);
            powerUpPacket.sendPowerUpPacket();
        });
    }

    public void removePowerUp() {
        this.isSpawned = false;
        Bukkit.getOnlinePlayers().stream().filter(this::doesExistInSpawnEntityMap).forEach(player -> {
            PowerUpPacket powerUpPacket = loadedPacketEntities.get(player.getUniqueId());
            powerUpPacket.sendRemovePowerUpPacket();
        });
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

            givePowerUp(nearestPlayer);
            removePowerUp();
        }
    }

    public abstract void givePowerUp(Player player);

}
