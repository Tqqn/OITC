package me.tqqn.oitc.powerups.powerups;

import me.tqqn.oitc.items.PluginItems;
import me.tqqn.oitc.powerups.PowerUp;
import me.tqqn.oitc.utils.Messages;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedPowerUp extends PowerUp {

    public SpeedPowerUp(Location location) {
        super(PluginItems.POWERUP_SPEED_ITEM.getItemStack(), "&cSpeed Power-Up", location);
    }

    @Override
    public void givePowerUp(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6, 2));
        player.sendMessage(Messages.POWERUP_SPEED_PICKUP.getMessage());
    }
}
