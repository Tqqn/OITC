package me.tqqn.oitc.setupwizard;

import lombok.Getter;
import me.tqqn.oitc.OITC;
import me.tqqn.oitc.config.PluginConfig;
import me.tqqn.oitc.items.PluginItems;
import me.tqqn.oitc.setupwizard.commands.EnterSetupModeCommand;
import me.tqqn.oitc.setupwizard.events.PlayerInteractListener;
import me.tqqn.oitc.setupwizard.events.PlayerJoinListener;
import me.tqqn.oitc.setupwizard.events.PlayerQuitListener;
import me.tqqn.oitc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;

import java.util.*;

public class SetupManager {

    private Map<UUID, Inventory> playerInventories = new HashMap<>();
    private List<UUID> playersInSetupMode = new ArrayList<>();
    private final OITC plugin;
    @Getter
    private final PluginConfig pluginConfig;

    public SetupManager(OITC plugin) {
        this.plugin = plugin;
        this.pluginConfig = plugin.getPluginConfig();
        registerEvents();
        registerCommands();
    }

    public void addSetupItems(Player player) {
        this.playerInventories.put(player.getUniqueId(), player.getInventory());
        this.playersInSetupMode.add(player.getUniqueId());

        player.getInventory().clear();
        player.getInventory().setItem(0, PluginItems.SET_LOBBY_LOCATION_ITEM.getItemStack());
        player.getInventory().setItem(1, PluginItems.SET_ARENA_LOCATION_ITEM.getItemStack());
        player.getInventory().setItem(2, PluginItems.SAVE_ITEM.getItemStack());

        player.sendMessage(Messages.ENTER_SETUP_MODE.getMessage());
    }

    public void removeSetupItems(Player player) {
        Inventory inventory = playerInventories.get(player.getUniqueId());

        player.getInventory().clear();
        player.getInventory().setContents(inventory.getContents());

        player.sendMessage(Messages.EXIT_SETUP_MODE.getMessage());
        this.playerInventories.remove(player.getUniqueId());
        this.playersInSetupMode.remove(player.getUniqueId());
    }

    public boolean isPlayerInSetupMode(UUID uuid) {
        return this.playersInSetupMode.contains(uuid);
    }

    private void registerEvents() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(this),plugin);
        pluginManager.registerEvents(new PlayerJoinListener(), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(this), plugin);
    }

    private void registerCommands() {
        plugin.getCommand("setup").setExecutor(new EnterSetupModeCommand(this));
    }

    public void removeSetupItemsFromAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isPlayerInSetupMode(player.getUniqueId())) return;
            removeSetupItems(player);
            Bukkit.getLogger().info(Messages.REMOVE_SETUP_ITEMS_FROM_PLAYER.getMessage(player.getDisplayName()));
        }
    }
}
