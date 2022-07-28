package me.tqqn.oitc.tasks;

import me.tqqn.oitc.players.PluginPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowCountdownTask extends BukkitRunnable {

    private int globalXPCountdown = 0;
    private final PluginPlayer pluginPlayer;
    private final Player player;

    public ArrowCountdownTask(PluginPlayer pluginPlayer) {
        this.pluginPlayer = pluginPlayer;
        this.player = Bukkit.getPlayer(pluginPlayer.getUuid());
    }

    @Override
    public void run() {

        if (player == null) {
            cancel();
            return;
        }
        pluginPlayer.setArrowCountdown(true);
        player.sendMessage("Arrow cooldown");

        if (globalXPCountdown == 10) {
            cancel();
            player.setExp(0);
            player.setLevel(0);
            player.getInventory().addItem(new ItemStack(Material.ARROW));
            pluginPlayer.setArrowCountdown(false);
            return;
        }

        player.setExp((float) globalXPCountdown / 10);
        globalXPCountdown++;
    }
}
