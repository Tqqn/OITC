package me.tqqn.oitc.powerups.powerups;

import me.tqqn.oitc.items.PluginItems;
import me.tqqn.oitc.powerups.PowerUp;
import me.tqqn.oitc.utils.Messages;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TrackerPowerUp extends PowerUp {

    public TrackerPowerUp(Location location) {
        super(PluginItems.POWERUP_TRACKER_ITEM.getItemStack(), "&b&lTracker Power-Up", location);
    }

    @Override
    public void givePowerUp(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.,120, 2));
        player.sendMessage(Messages.POWERUP_JUMP_PICKUP.getMessage());
    }
}
