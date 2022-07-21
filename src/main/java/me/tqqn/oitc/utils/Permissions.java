package me.tqqn.oitc.utils;

import lombok.Getter;

@Getter
public enum Permissions {

    JOIN_ARENA_FULL_PERMISSION("oitc.arena.full");

    private final String permission;

    Permissions(String permissions) {
        this.permission = permissions;
    }
}
