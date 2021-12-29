package net.ender.cc.Scoring.util.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import net.ender.cc.Scoring.Games.Timers;
import net.ender.cc.Scoring.Games.player.PlayerData;
import net.ender.cc.Scoring.Games.teams.Team;
import net.ender.cc.Scoring.Games.teams.TeamData;
import net.ender.cc.Scoring.Scoring;
import net.ender.cc.Scoring.listener.custom.GameStartListener;
import net.ender.cc.Scoring.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static net.ender.cc.Scoring.listener.custom.GameStartListener.getTeam;
import static net.ender.cc.Scoring.util.Util.colorize;

public class GameBoard {
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    public void start(ArrayList<Player> players) {
        for (Player player : players) {
            FastBoard board = new FastBoard(player);

            board.updateTitle(colorize("&e&lEnder Tournaments #1"));

            this.boards.put(player.getUniqueId(), board);
        }
        startRunnable();
    }

    private void startRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (FastBoard board : boards.values()) {
                    if (!board.getPlayer().isOnline()) {
                        boards.remove(board.getPlayer().getUniqueId());
                        return;
                    }
                    gameBoardUpdate(board);
                }
            }
        }.runTaskTimer(Scoring.getInstance(), 0, 20);
    }

    public static void gameBoardUpdate(FastBoard board) {
        Player player = board.getPlayer();
        LinkedHashMap<Team, Integer> sorted = Util.getSortedMap(TeamData.score);
        board.updateLines(
                Util.colorize(
                        "",
                        "&b&lYou Team: &f" + getTeam(player).getName() + "      ",
                        "&b&lGame 1/1: &fSkyblockle",
                        "&c&l" + Timers.getTimerFromIndex(GameStartListener.stageIndex).getDisplayName() + ": &f" + formatTime(Math.toIntExact(Timers.getTimerFromIndex(GameStartListener.stageIndex).getSeconds()),GameStartListener.currentTimeSeconds),
                        "",
                        "&e&lYour Score: &f" + PlayerData.score.get(player.getUniqueId()),
                        "&e&lTeam Score: &f" + TeamData.score.get(getTeam(player)),
                        "",
                        getPosition(player, 1, sorted),
                        getPosition(player, 2, sorted),
                        getPosition(player, 0, sorted)
        ));
    }

    private static String getPosition(Player player, int spot, LinkedHashMap<Team, Integer> sorted) {
        ArrayList<Map.Entry<Team, Integer>> sortedTeams  = new ArrayList<>(sorted.entrySet());
        if (spot == 0) {
            int count = 0;
            for (Map.Entry<Team, Integer> entry : sortedTeams) {
                count++;
                if (entry.getKey() == getTeam(player)) {
                    if (count > 3) {
                        return "&f" + (count) + ". &c&l" + sortedTeams.get(count - 1).getKey().getName() + "    " + sorted.get(sortedTeams.get(count - 1).getKey());
                    } else {
                        return "";
                    }
                }
            }
        }
        if (getTeam(player) == sortedTeams.get(spot - 1).getKey()) {
            return "&f" + spot + ". &c&l" + sortedTeams.get(spot - 1).getKey().getName() + "    " + sorted.get(sortedTeams.get(spot - 1).getKey());
        } else {
            return "&f" + spot + ". &c" + sortedTeams.get(spot - 1).getKey().getName() + "    " + sorted.get(sortedTeams.get(spot - 1).getKey());
        }
    }

    private static String formatTime(int minus, int secs) {
        secs = minus - secs;
        return String.format("%02d:%02d", (secs % 3600) / 60, secs % 60);
    }


}
