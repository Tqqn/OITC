package me.tqqn.oitc.managers;

import me.tqqn.oitc.utils.Color;
import me.tqqn.oitc.utils.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerManager {

    private final ItemStack arrow = new ItemStack(Material.ARROW,1);

    public void givePlayerBowAndArrow(Player player) {
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowmeta = bow.getItemMeta();

        bowmeta.setDisplayName(Color.translateColor("&cOITC-Bow"));
        bowmeta.setUnbreakable(true);

        bow.setItemMeta(bowmeta);


        player.getInventory().setItem(0, bow);
        player.getInventory().setItem(1, this.arrow);
    }
    public void givePlayerArrow(Player player) {
        player.getInventory().setItem(1, this.arrow);
    }

    public void sendDeathTitleToPlayer(Player player) {
        player.resetTitle();
        player.sendTitle(Messages.TITLE_DEATH_PLAYER.getMessage(),"", 20, 70, 20);
    }

    public void sendKillTitleToPlayer(Player player) {
        player.resetTitle();
        player.sendTitle(Messages.TITLE_KILLER_PLAYER.getMessage(),"", 20, 70, 20);
    }
}
