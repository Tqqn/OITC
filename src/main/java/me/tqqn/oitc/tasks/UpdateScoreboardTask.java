package me.tqqn.oitc.tasks;

import me.tqqn.oitc.managers.GameManager;
import me.tqqn.oitc.utils.PluginScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateScoreboardTask extends BukkitRunnable {

    private final GameManager gameManager;

    public UpdateScoreboardTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PluginScoreboard pluginScoreboard = gameManager.getScoreboardManager().getPluginScoreboard(player.getUniqueId());
            pluginScoreboard.update();
        }
    }
}
