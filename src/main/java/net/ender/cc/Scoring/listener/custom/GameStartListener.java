package net.ender.cc.Scoring.listener.custom;

import fr.mrmicky.fastboard.FastBoard;
import net.ender.cc.Scoring.Games.Skyblockle.Skyblockle;
import net.ender.cc.Scoring.Games.Timers;
import net.ender.cc.Scoring.Games.player.PlayerData;
import net.ender.cc.Scoring.Games.teams.Team;
import net.ender.cc.Scoring.Games.teams.TeamData;
import net.ender.cc.Scoring.Scoring;
import net.ender.cc.Scoring.events.OnGameEndEvent;
import net.ender.cc.Scoring.events.OnGameStartEvent;
import net.ender.cc.Scoring.util.scoreboard.GameBoard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameStartListener implements Listener {
    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<Player> playersAlive = new ArrayList<>();
    public static HashMap<Team, ArrayList<Player>> teamsToPlayers = new HashMap<>();
    public static ArrayList<Team> teams = new ArrayList<>();
    public static WorldBorder boarder = Bukkit.getWorld("skyblockle").getWorldBorder();

    @EventHandler
    public void onStart(OnGameStartEvent event) {
        for (Team team : Team.values()) {
            List<UUID> uuids = Arrays.asList(team.getPlayers());
            ArrayList<Player> tempPlayers = new ArrayList<>();
            if (!uuids.isEmpty()) {
                for (UUID uuid : uuids) {
                    if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
                        players.add(Bukkit.getPlayer(uuid));
                        playersAlive.add(Bukkit.getPlayer(uuid));
                        tempPlayers.add(Bukkit.getPlayer(uuid));
                        PlayerData.score.put(uuid, 0);
                    }
                }
            } else {
                continue;
            }
            teams.add(team);
            TeamData.score.put(team, 0);
            teamsToPlayers.put(team, tempPlayers);
        }
        new GameBoard().start(players);
        runTimer();
        boarder.setCenter(0,0);
        boarder.setSize(360);
        boarder.setWarningDistance(3);
        boarder.setDamageAmount(1);
    }

    public static Team getTeam(Player player) {
        for (Map.Entry<Team, ArrayList<Player>> entry : teamsToPlayers.entrySet()) {
            if (entry.getValue().contains(player)) {
                return entry.getKey();
            }
        }
        return null;
    }
    public static HashMap<UUID, Entity> frozenPlayers = new HashMap<>();
    public void freeze(Player player) {
        if (!frozenPlayers.containsKey(player.getUniqueId())) {
            ArmorStand stand = (ArmorStand) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
            stand.setVisible(false);
            stand.setInvulnerable(true);
            stand.setPassenger(player);
            frozenPlayers.put(player.getUniqueId(), stand);
        }
    }

    public void unFreeze(Player player) {
        if (frozenPlayers.containsKey(player.getUniqueId())) {
            frozenPlayers.get(player.getUniqueId()).remove();
            frozenPlayers.remove(player.getUniqueId());
        }
    }

    public static int currentTimeSeconds;
    public static int stageIndex = 1;
    public void runTimer() {
        BukkitRunnable freeze = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : players) {
                    if (frozenPlayers.get(player.getUniqueId()).getPassengers().isEmpty()) {
                        frozenPlayers.get(player.getUniqueId()).setPassenger(player);
                    }
                }
            }
        };
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (currentTimeSeconds == Timers.getTimerFromIndex(stageIndex).getSeconds()) {
                    stageIndex++;
                    switch (Timers.getTimerFromIndex(stageIndex - 1)) {
                        case START:
                            for (Team team : teams) {
                                for (Player player : teamsToPlayers.get(team)) {
                                    player.teleport(Skyblockle.SpawnLocations.valueOf(team.name()).getLocation());
                                    freeze(player);
                                }
                            }
                            freeze.runTaskTimer(Scoring.getInstance(), 0, 1);
                            break;
                        case RELEASE:
                            freeze.cancel();
                            for (Player player : players) {
                                unFreeze(player);
                            }
                            break;
                        case FIRST_SHRINK:
                            boarder.setSize(260, 60);
                            break;
                        case SECOND_SHRINK:
                            boarder.setSize(200, 60);
                            break;
                        case THIRD_SHINK:
                            boarder.setSize(60, 120);
                            break;
                        case END:
                            boarder.reset();
                            cancel();
                            Bukkit.getPluginManager().callEvent(new OnGameEndEvent("Skyblockle"));
                            break;
                    }
                }
                currentTimeSeconds++;
            }
        };
        runnable.runTaskTimer(Scoring.getInstance(), 0, 20);
    }


}
