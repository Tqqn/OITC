package me.tqqn.oitc.utils;

import lombok.Getter;
import me.tqqn.oitc.players.PlayerStats;
import me.tqqn.oitc.players.PluginPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;
import java.util.UUID;

public class PluginScoreboard {

    private final PlayerStats playerStats;
    @Getter
    private final Player player;

    public PluginScoreboard(PlayerStats playerStats, UUID uuid) {
        this.playerStats = playerStats;
        this.player = Bukkit.getPlayer(uuid);
    }

    public void update() {
        Team playerKills = player.getScoreboard().getTeam("kills");
        Team newPlayerKills = playerKills != null ? playerKills : player.getScoreboard().registerNewTeam("player_kills");
        newPlayerKills.setSuffix(ChatColor.WHITE + Integer.toString(playerStats.getKills()));

        Team entityKills = player.getScoreboard().getTeam("deaths");
        Team newEntityKills = entityKills != null ? entityKills : player.getScoreboard().registerNewTeam("entity_kills");
        newEntityKills.setSuffix(ChatColor.WHITE + Integer.toString(playerStats.getDeaths()));
    }

    public void setPlayerScoreboard() {

        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        Objective objective = scoreboard.getObjective("oitc") != null ? scoreboard.getObjective("oitc") : scoreboard.registerNewObjective("aaa", "dummy", (Color.translateColor("  &e&lOne in the Chamber"  )));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score blank = objective.getScore(" ");
        Score blank1 = objective.getScore("  ");
        Score blank2 = objective.getScore("   ");

        Score spelerInfo = objective.getScore(Color.translateColor("&c- &f&lPlayer info:"));

        Score stats = objective.getScore(Color.translateColor("&c- &f&lStats:"));

        Score playerName = objective.getScore(Color.translateColor("&7Name: &f" + player.getDisplayName()));


        Team playerKills = scoreboard.registerNewTeam("kills");
        playerKills.addEntry(ChatColor.BLACK.toString());
        playerKills.setPrefix(Color.translateColor("&7Kills: "));
        playerKills.setSuffix(Color.translateColor("&f" + playerStats.getKills()));


        Team entityKills = scoreboard.registerNewTeam("deaths");
        entityKills.addEntry(ChatColor.BLUE.toString());
        entityKills.setPrefix(Color.translateColor("&7Deaths: "));
        entityKills.setSuffix(Color.translateColor("&f" + playerStats.getDeaths()));

        blank.setScore(1);
        objective.getScore(ChatColor.AQUA.toString()).setScore(2); //deaths
        objective.getScore(ChatColor.BLUE.toString()).setScore(3); //entity_kills
        objective.getScore(ChatColor.BLACK.toString()).setScore(4); //player_kills
        stats.setScore(5);
        blank2.setScore(6);
        playerName.setScore(7);
        spelerInfo.setScore(8);
        blank1.setScore(9);

        player.setScoreboard(Objects.requireNonNull(objective.getScoreboard()));
    }

    public void removeScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
