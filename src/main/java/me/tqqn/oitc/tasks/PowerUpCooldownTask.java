package me.tqqn.oitc.tasks;

import me.tqqn.oitc.OITC;
import me.tqqn.oitc.managers.GameManager;
import me.tqqn.oitc.powerups.powerups.SpeedPowerUp;
import me.tqqn.oitc.utils.Messages;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class PowerUpCooldownTask extends BukkitRunnable {

    private final GameManager gameManager;
    private final OITC plugin;

    public PowerUpCooldownTask(GameManager gameManager, OITC plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!gameManager.isGameActive()) {
            cancel();
            return;
        }
            Location powerUpLocation = gameManager.getRandomPowerUpLocation();
            SpeedPowerUp speedPowerUp = new SpeedPowerUp(powerUpLocation);
            speedPowerUp.setPowerUp();
            gameManager.broadcast(Messages.POWERUP_SPAWN.getMessage());

            ActiveSpeedPowerUpTask activeSpeedPowerUpTask = new ActiveSpeedPowerUpTask(speedPowerUp, gameManager);
            activeSpeedPowerUpTask.runTaskTimerAsynchronously(plugin, 0, 20);
    }
}
