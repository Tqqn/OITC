package me.tqqn.oitc.players;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PlayerStats {

    private final UUID uuid;
    private int kills = 0;
    private int deaths = 0;

    public PlayerStats(UUID uuid) {
        this.uuid = uuid;
    }

    public void addKill() {
        this.kills = (this.kills+1);
    }

    public void addDeath() {
        this.deaths = (this.deaths+1);
    }
}
