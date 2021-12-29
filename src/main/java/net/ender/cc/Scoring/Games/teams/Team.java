package net.ender.cc.Scoring.Games.teams;

import org.bukkit.entity.Player;

import java.util.UUID;

public enum Team {
    TEAM_1("Emerald Endermen", UUID.fromString("ad80d7cf-8115-4e2a-b15d-e5cc0bf6a9a2")),
    TEAM_2("Indigo illiagers"),
    TEAM_3("Fuschia Phantoms"),
    TEAM_4("Bronze Blazes"),
    TEAM_5("Crimson Creepers"),
    TEAM_6("Charcoal Chickens"),
    TEAM_7("Prizmarine Piglins"),
    TEAM_8("Violet Vexes");

    String name;
    UUID[] players;
    Team(String name, UUID... players) {
        this.name = name;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public UUID[] getPlayers() {
        return players;
    }

    public static Team getTeamFromName(String name) {
        for (Team team : Team.values()) {
            if (team.name.equals(name)) {
                return team;
            }
        }
        return null;
    }
}
