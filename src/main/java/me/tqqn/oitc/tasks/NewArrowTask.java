package me.tqqn.oitc.tasks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class NewArrowTask extends BukkitRunnable {

    private final Player player;

    public NewArrowTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        player.getInventory().addItem(new ItemStack(Material.ARROW));
    }
}
