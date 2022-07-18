package me.tqqn.oitc;

import org.bukkit.ChatColor;

public class Color {

    public static String translateColor(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
