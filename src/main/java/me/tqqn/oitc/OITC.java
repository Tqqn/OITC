package me.tqqn.oitc;

import lombok.Getter;
import me.tqqn.oitc.commands.StartCommand;
import me.tqqn.oitc.config.PluginConfig;
import me.tqqn.oitc.managers.GameManager;
import me.tqqn.oitc.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class OITC extends JavaPlugin {
    @Getter
    private GameManager gameManager;
    @Getter
    private PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        this.pluginConfig = new PluginConfig(this);
        this.gameManager = new GameManager(this);
        registerCommands();

        sendStartUpMessage();
    }

    @Override
    public void onDisable() {
        sendDisableMessage();
    }

    public void registerCommands() {
        this.getCommand("start").setExecutor(new StartCommand(this));
    }

    private void sendStartUpMessage() {
        Bukkit.getLogger().info(Color.translateColor("&6&m---------------------------------"));
        Bukkit.getLogger().info(Color.translateColor("       &fOne in the Chamber - &aEnabled"));
        Bukkit.getLogger().info(Color.translateColor("&6Author: &fTqqn"));
        Bukkit.getLogger().info(Color.translateColor("&6Version: &f" + getDescription().getVersion()));
        Bukkit.getLogger().info(Color.translateColor("&6Description: &f" + getDescription().getDescription()));
        Bukkit.getLogger().info(Color.translateColor("&6&m---------------------------------"));
        Bukkit.getLogger().info(Color.translateColor(""));
    }

    private void sendDisableMessage() {
        Bukkit.getLogger().info(Color.translateColor("&6&m---------------------------------"));
        Bukkit.getLogger().info(Color.translateColor("       &fOne in the Chamber - &cDisabled"));
        Bukkit.getLogger().info(Color.translateColor("&6Author: &fTqqn"));
        Bukkit.getLogger().info(Color.translateColor("&6Version: &f" + getDescription().getVersion()));
        Bukkit.getLogger().info(Color.translateColor("&6Description: &f" + getDescription().getDescription()));
        Bukkit.getLogger().info(Color.translateColor("&6&m---------------------------------"));
        Bukkit.getLogger().info(Color.translateColor(""));
    }
}
