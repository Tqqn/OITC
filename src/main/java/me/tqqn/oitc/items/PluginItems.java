package me.tqqn.oitc.items;

import me.tqqn.oitc.utils.Color;
import me.tqqn.oitc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum PluginItems {

    PLAYER_ARROW("&cOITC Arrow", Material.ARROW, null, false, 1),
    PLAYER_BOW("&cOITC Bow", Material.BOW, null, false, 1),
    SET_LOBBY_LOCATION_ITEM("&cSet Lobby Location", Material.BOOK, new String[]{"&6Sets Lobby Spawn location"}, true, 1),
    SET_POWERUP_LOCATIONS_ITEM("&cSet PowerUp Location", Material.FEATHER, new String[]{"&6Sets PowerUp locations."}, true, 1),
    SAVE_ITEM("&cSave", Material.NETHER_STAR, new String[]{"&6Saves locations."}, true, 1),
    POWERUP_SPEED_ITEM("&cSpeed-PowerUp", Material.FEATHER, null, true, 1),
    SET_ARENA_LOCATION_ITEM("&cSet Arena Locations", Material.FEATHER, new String[]{"&6Sets Arena Spawn locations"}, true, 1);

    private final String displayName;
    private final Material material;
    private final String[] lore;
    private final boolean itemGlow;
    private final int amount;

    PluginItems(String displayName, Material material, String[] lore, boolean itemGlow, int amount) {
        this.displayName = displayName;
        this.material = material;
        this.lore = lore;
        this.itemGlow = itemGlow;
        this.amount = amount;
    }
    public ItemStack getItemStack() {
        ItemBuilder itemBuilder = new ItemBuilder(this.material, amount);
        itemBuilder.setDisplayName(this.displayName);
        if (this.lore != null) {
            itemBuilder.setLore(this.lore);
        }
        if (this.itemGlow) {
            itemBuilder.setGlow();
        }
        return itemBuilder.build();
    }
}
