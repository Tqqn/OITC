package me.tqqn.oitc.tasks;

import me.tqqn.oitc.managers.GameManager;
import me.tqqn.oitc.powerups.PowerUp;
import me.tqqn.oitc.powerups.powerups.SpeedPowerUp;
import org.bukkit.scheduler.BukkitRunnable;

public class ActiveSpeedPowerUpTask extends BukkitRunnable {

    private final SpeedPowerUp speedPowerUp;
    private final GameManager gameManager;

    public ActiveSpeedPowerUpTask(SpeedPowerUp speedPowerUp, GameManager gameManager) {
        this.speedPowerUp = speedPowerUp;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (!speedPowerUp.isPowerUpSpawned()) {
            gameManager.setArenaPowerUpSpawned(false);
            cancel();
        }
        speedPowerUp.pickUp();
    }
}
