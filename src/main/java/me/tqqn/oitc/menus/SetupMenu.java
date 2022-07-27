package me.tqqn.oitc.menus;

import me.tqqn.oitc.menubuilder.Menu;
import me.tqqn.oitc.menubuilder.MenuButton;
import me.tqqn.oitc.utils.Messages;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SetupMenu extends Menu {

    public SetupMenu() {
        super(Messages.SETUP_MENU_NAME.getMessage(),1);

        setInventoryOpened(opened -> opened.sendMessage(Messages.OPEN_SETUP_MENU.getMessage()));
        setInventoryClosed(closed -> closed.sendMessage(Messages.CLOSE_SETUP_MENU.getMessage()));

        registerButton(new MenuButton(new ItemStack(Material.DIAMOND_PICKAXE)).setWhenClicked(clicked -> clicked.sendMessage("")), 0);
    }

}
