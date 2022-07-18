package me.tqqn.oitc.managers;

import me.tqqn.oitc.Color;
import me.tqqn.oitc.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerManager {

    private final GameManager gameManager;

    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void givePlayerBowAndArrow(Player player) {
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowmeta = bow.getItemMeta();

        bowmeta.setDisplayName(Color.translateColor("&cOITC-Bow"));
        bowmeta.setUnbreakable(true);

        bow.setItemMeta(bowmeta);

        ItemStack arrow = new ItemStack(Material.ARROW,1);

        player.getInventory().setItem(0, bow);
        player.getInventory().setItem(1, arrow);

        player.sendMessage(Messages.GIVE_ITEM.getMessage());
    }

}
