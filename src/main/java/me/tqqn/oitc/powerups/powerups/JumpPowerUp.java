package me.tqqn.oitc.powerups.powerups;

import me.tqqn.oitc.items.PluginItems;
import me.tqqn.oitc.powerups.PowerUp;
import me.tqqn.oitc.utils.Messages;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpPowerUp extends PowerUp {

    public JumpPowerUp(Location location) {
        super(PluginItems.POWERUP_JUMP_ITEM.getItemStack(), "&b&lJump Power-Up", location);
    }

    @Override
    public void givePowerUp(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,120, 2));
        player.sendMessage(Messages.POWERUP_JUMP_PICKUP.getMessage());
    }
}
