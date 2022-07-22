package me.tqqn.oitc.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowXPCountdown extends BukkitRunnable {

    private int countdown = 0;
    private final Player player;

    public ArrowXPCountdown(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if (countdown == 10) {
            player.setExp(0);
            player.setLevel(0);
            cancel();
        }

        player.setExp(1F / (countdown*10));
        countdown++;
    }
}
