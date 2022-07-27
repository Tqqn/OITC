package me.tqqn.oitc.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuButton {

    private ItemStack itemStack;

    private Consumer<Player> whenClicked;

    public MenuButton(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public Consumer<Player> getWhenClicked() {
        return whenClicked;
    }

    public MenuButton setWhenClicked(Consumer<Player> whenClicked) {
        this.whenClicked = whenClicked;
        return this;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
