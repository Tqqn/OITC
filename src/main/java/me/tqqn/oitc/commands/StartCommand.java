package me.tqqn.oitc.commands;

import me.tqqn.oitc.game.GameState;
import me.tqqn.oitc.OITC;
import me.tqqn.oitc.managers.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {

    private final OITC plugin;
    private final GameManager gameManager;

    public StartCommand(OITC plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
    }

    //Debug start command.

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        gameManager.setGameState(GameState.STARTING);
        return true;
    }
}
