package me.tqqn.oitc.utils;

import lombok.Getter;

public enum Permissions {

    COMMAND_SETUP_PERMISSION("oitc.setup.mode"),
    JOIN_SETUP_MODE_PERMISSION("oitc.setup.join"),
    JOIN_ARENA_FULL_PERMISSION("oitc.arena.full");

    @Getter
    private final String permission;

    Permissions(String permissions) {
        this.permission = permissions;
    }
}
