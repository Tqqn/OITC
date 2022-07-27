package me.tqqn.oitc.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public class Color {

    public static String translateColor(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String[] translateColor(String[] strings) {
        ArrayList<String> msgArray = new ArrayList<>();
        for (String msg : strings) {
            msgArray.add(Color.translateColor(msg));
        }
        return msgArray.toArray(new String[0]);
    }
}
