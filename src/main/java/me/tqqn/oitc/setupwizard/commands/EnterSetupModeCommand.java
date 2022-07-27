package me.tqqn.oitc.setupwizard.commands;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.setupwizard.SetupManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnterSetupModeCommand implements CommandExecutor {

    private final SetupManager setupManager;

    public EnterSetupModeCommand(SetupManager setupManager) {
        this.setupManager = setupManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (setupManager.isPlayerInSetupMode(player.getUniqueId())) {
            setupManager.removeSetupItems(player);
        } else {
            setupManager.addSetupItems(player);
        }
        return true;
    }
}
