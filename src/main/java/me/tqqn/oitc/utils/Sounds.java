package me.tqqn.oitc.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import me.tqqn.oitc.OITC;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public enum Sounds {

    COUNTDOWN_SOUND(Sound.BLOCK_DISPENSER_FAIL, 0.5, 1.5, "block.dispenser.fail"),
    PLAYER_DEATH(Sound.ENTITY_CAT_AMBIENT, 0.5, 0.5, "entity.cat.ambient"),
    PLAYER_KILL(Sound.ENTITY_CAT_AMBIENT, 0.5, 4, "entity.cat.ambient"),
    GAME_START(Sound.ENTITY_ENDER_DRAGON_GROWL, 0.3, 3, "entity.ender_dragon.growl");

    private final Sound sound;
    private final double volume;
    private final double pitch;
    private final String soundName;


    Sounds(Sound sound, double volume, double pitch, String soundName) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.soundName = soundName;
    }

    public void playSound(Player player) {
        player.playSound(player.getLocation(), this.sound, (float) this.volume,(float) this.pitch);
    }

    public void playPacketSound(Player player) {
        Location location = player.getLocation();
        ProtocolManager protocolManager = OITC.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.NAMED_SOUND_EFFECT);
        packet.getModifier().writeDefaults();
        packet.getSoundEffects().write(0, this.sound);
        packet.getIntegers().write(0, location.getBlockX() * 8).write(1, location.getBlockY() * 8).write(2, location.getBlockZ() * 8);
        packet.getFloat().write(0, (float) this.volume).write(1, (float) this.pitch);
        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
