package me.tqqn.oitc;

import lombok.Getter;

public enum Messages {

    NO_PERMISSION(Color.translateColor("&cYou have currently no permission to use this command.")),
    GAME_START_COOLDOWN(Color.translateColor("&cGame is starting in &f")),
    GAME_START(Color.translateColor("&cGame started!")),
    GIVE_ITEM(Color.translateColor("&cYou have received the One In Chamber items."));

    @Getter
    private final String message;

    Messages(String message) {
        this.message = message;
    }
}
