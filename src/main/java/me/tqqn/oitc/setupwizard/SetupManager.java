package me.tqqn.oitc.setupwizard;

import me.tqqn.oitc.OITC;
import me.tqqn.oitc.items.PluginItems;
import me.tqqn.oitc.setupwizard.commands.EnterSetupModeCommand;
import me.tqqn.oitc.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;

import java.util.*;

public class SetupManager {

    private final Map<UUID, Inventory> playerInventories = new HashMap<>();
    private final List<UUID> playersInSetupMode = new ArrayList<>();
    private final OITC plugin;

    public SetupManager(OITC plugin) {
        this.plugin = plugin;
        registerEvents();
        registerCommands();
    }

    public void addSetupItems(Player player) {
        this.playerInventories.put(player.getUniqueId(), player.getInventory());

        player.getInventory().clear();
        player.getInventory().setItem(0, PluginItems.SET_LOBBY_LOCATION_ITEM.getItemStack());
        player.getInventory().setItem(1, PluginItems.SET_ARENA_LOCATION_ITEM.getItemStack());

        player.sendMessage(Messages.EXIT_SETUP_MODE.getMessage());
    }

    public void removeSetupItems(Player player) {
        Inventory inventory = playerInventories.get(player.getUniqueId());

        player.getInventory().clear();
        player.getInventory().setContents(inventory.getContents());

        player.sendMessage(Messages.ENTER_SETUP_MODE.getMessage());
    }

    public boolean isPlayerInSetupMode(UUID uuid) {
        return this.playersInSetupMode.contains(uuid);
    }

    private void registerEvents() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
    }

    private void registerCommands() {
        plugin.getCommand("setup").setExecutor(new EnterSetupModeCommand(this));
    }

}
