package me.tqqn.oitc.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import me.tqqn.oitc.OITC;
import me.tqqn.oitc.powerups.PowerUp;
import me.tqqn.oitc.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PowerUpPacket {

    private int itemID;
    private int itemArmorStandID;
    private int nameArmorStandID;

    private final Item item;
    private final Location location;
    private final String displayName;

    private final Player player;

    private final ArmorStand itemArmorStand;
    private final ArmorStand nameArmorStand;

    private final ProtocolManager protocolManager = OITC.getProtocolManager();
    private final PowerUp powerUp;

    public PowerUpPacket(Player player, Item item, Location location, String displayName, PowerUp powerUp) {
        this.player = player;
        this.powerUp = powerUp;
        this.item = item;
        this.location = location;
        this.displayName = displayName;
        this.itemArmorStand =  (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        this.nameArmorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
    }

    public void sendPowerUpPacket() {

        powerUp.addSpawnedEntityToMap(player, this);
            try {
                protocolManager.sendServerPacket(player, spawnItemArmorStandPacket());
                protocolManager.sendServerPacket(player, spawnItemArmorStandMetaPacket());
                protocolManager.sendServerPacket(player, teleportEntityUp());
                protocolManager.sendServerPacket(player, spawnItemPacket());
                protocolManager.sendServerPacket(player, spawnItemMetaPacket());
                protocolManager.sendServerPacket(player, addPassengerPacket());
                protocolManager.sendServerPacket(player, spawnNameArmorStandPacket());
                protocolManager.sendServerPacket(player, spawnNameArmorStandMetaPacket());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
    }

    public void sendRemovePowerUpPacket() {
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
        int[] ints = new int[]{itemID, itemArmorStandID, nameArmorStandID};
        packetContainer.getIntegerArrays().write(0, ints);

        powerUp.removeSpawnedEntityFromMap(player);
            try {
                protocolManager.sendServerPacket(player, packetContainer);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
    }

    private PacketContainer spawnNameArmorStandPacket() {
        PacketContainer armorStandSpawnPacket = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        nameArmorStandID = ThreadLocalRandom.current().nextInt();
        nameArmorStand.remove();

        armorStandSpawnPacket.getUUIDs().write(0, UUID.randomUUID());
        armorStandSpawnPacket.getIntegers().write(0, nameArmorStandID);
        armorStandSpawnPacket.getEntityTypeModifier().write(0, nameArmorStand.getType());
        armorStandSpawnPacket.getDoubles().write(0, location.getX())
                .write(1, (location.getY() + 1))
                .write(2, location.getZ());

        return armorStandSpawnPacket;
    }

    private PacketContainer spawnNameArmorStandMetaPacket() {
        PacketContainer armorStandMetaPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

        nameArmorStand.setVisible(false);
        nameArmorStand.setInvulnerable(true);
        nameArmorStand.setMarker(true);
        nameArmorStand.setSmall(true);
        nameArmorStand.setCustomName(Color.translateColor(displayName));
        nameArmorStand.setCustomNameVisible(true);

        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(nameArmorStand);
        armorStandMetaPacket.getIntegers().write(0, nameArmorStandID);
        armorStandMetaPacket.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return armorStandMetaPacket;
    }


    private PacketContainer spawnItemArmorStandPacket() {
        PacketContainer armorStandSpawnPacket = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        armorStandSpawnPacket.getModifier().writeDefaults();

        itemArmorStandID = ThreadLocalRandom.current().nextInt();
        itemArmorStand.remove();

        armorStandSpawnPacket.getUUIDs().write(0, UUID.randomUUID());
        armorStandSpawnPacket.getIntegers().write(0, itemArmorStandID);
        armorStandSpawnPacket.getEntityTypeModifier().write(0, itemArmorStand.getType());
        armorStandSpawnPacket.getDoubles().write(0, location.getX())
                .write(1, (location.getY() - 50))
                .write(2, location.getZ());

        return armorStandSpawnPacket;
    }

    private PacketContainer spawnItemArmorStandMetaPacket() {
        PacketContainer armorStandMetaPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

        itemArmorStand.setVisible(false);
        itemArmorStand.setInvulnerable(true);
        itemArmorStand.setMarker(true);
        itemArmorStand.setSmall(true);

        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(itemArmorStand);
        armorStandMetaPacket.getIntegers().write(0, itemArmorStandID);
        armorStandMetaPacket.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return armorStandMetaPacket;
    }

    private PacketContainer spawnItemPacket() {
        PacketContainer itemSpawnPacket = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        itemSpawnPacket.getModifier().writeDefaults();

        itemID = ThreadLocalRandom.current().nextInt();
        item.remove();

        itemSpawnPacket.getUUIDs().write(0, UUID.randomUUID());
        itemSpawnPacket.getIntegers().write(0,itemID);
        itemSpawnPacket.getEntityTypeModifier().write(0, item.getType());
        itemSpawnPacket.getDoubles().write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());

        return itemSpawnPacket;
    }

    private PacketContainer spawnItemMetaPacket() {
        PacketContainer itemMetaDataPacket = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);

        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(item);
        itemMetaDataPacket.getIntegers().write(0,itemID);
        itemMetaDataPacket.getWatchableCollectionModifier().write(0,dataWatcher.getWatchableObjects());
        return itemMetaDataPacket;
    }

    private PacketContainer addPassengerPacket() {
        PacketContainer addPassengerPacket = new PacketContainer(PacketType.Play.Server.MOUNT);
        addPassengerPacket.getModifier().writeDefaults();
        addPassengerPacket.getIntegers().write(0, itemArmorStandID);

        int[] ints = new int[]{itemID};
        addPassengerPacket.getIntegerArrays().write(0, ints);

        return addPassengerPacket;
    }

    private PacketContainer teleportEntityUp() {
        PacketContainer teleportEntityPacket = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
        teleportEntityPacket.getModifier().writeDefaults();
        teleportEntityPacket.getIntegers().write(0, itemArmorStandID);

        teleportEntityPacket.getDoubles().write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());
        return teleportEntityPacket;
    }
}
