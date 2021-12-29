package net.ender.cc.Scoring.listener.custom;

import net.ender.cc.Scoring.events.OnGameEndEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static net.ender.cc.Scoring.listener.custom.GameStartListener.players;
import static net.ender.cc.Scoring.listener.custom.GameStartListener.playersAlive;
import static net.ender.cc.Scoring.util.Util.colorize;

public class GameEndListener implements Listener {
    @EventHandler
    public void onGamEnd(OnGameEndEvent event) {
        for (Player p : players) {
            p.kickPlayer("Game is over!");
            players.clear();
            playersAlive.clear();
            GameStartListener.stageIndex = 1;
            GameStartListener.frozenPlayers.clear();
            GameStartListener.teamsToPlayers.clear();
            GameStartListener.currentTimeSeconds = 0;
            GameStartListener.teams.clear();
        }
    }
}
