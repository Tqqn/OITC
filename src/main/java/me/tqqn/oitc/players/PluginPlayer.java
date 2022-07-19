package me.tqqn.oitc.players;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PluginPlayer {

    private final UUID uuid;
    private final String displayName;
    private final PlayerStats playerStats;

    public PluginPlayer(UUID uuid, String displayName, PlayerStats playerStats) {
        this.uuid = uuid;
        this.displayName = displayName;
        this.playerStats = playerStats;
    }
}
