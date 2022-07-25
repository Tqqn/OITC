package me.tqqn.oitc.players;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class PluginPlayer {

    private final UUID uuid;
    private final String displayName;
    private final PlayerStats playerStats;
    @Setter
    private boolean isArrowCountdown = false;

    public PluginPlayer(UUID uuid, String displayName, PlayerStats playerStats) {
        this.uuid = uuid;
        this.displayName = displayName;
        this.playerStats = playerStats;
    }
}
