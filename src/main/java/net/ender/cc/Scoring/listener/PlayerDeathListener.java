package net.ender.cc.Scoring.listener;

import net.ender.cc.Scoring.Games.player.PlayerData;
import net.ender.cc.Scoring.Games.teams.TeamData;
import net.ender.cc.Scoring.listener.custom.GameStartListener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import java.util.HashMap;
import java.util.logging.Logger;

import static net.ender.cc.Scoring.listener.custom.GameStartListener.getTeam;
import static net.ender.cc.Scoring.listener.custom.GameStartListener.players;
import static net.ender.cc.Scoring.util.Util.colorize;

public class PlayerDeathListener implements Listener {
    HashMap<Player, Player> damage = new HashMap<>();
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (players.contains(player)) {
            GameStartListener.playersAlive.remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            for (Player p : GameStartListener.playersAlive) {
                int score = PlayerData.score.get(p.getUniqueId());
                int teamScore = TeamData.score.get(getTeam(p));
                TeamData.score.put(getTeam(p), teamScore + 10);
                PlayerData.score.put(p.getUniqueId(), score + 10);
                p.sendMessage(colorize("&f[&a+10&f]"));
            }
            if (damage.containsKey(player)) {
                Player killer = damage.get(player);
                int score = PlayerData.score.get(killer.getUniqueId());
                int teamScore = TeamData.score.get(getTeam(killer));
                TeamData.score.put(getTeam(killer), teamScore + 70);
                PlayerData.score.put(killer.getUniqueId(), score + 70);
                killer.sendMessage(colorize("&f[&a+70&f]"));
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            damage.put((Player) event.getEntity(), (Player) event.getDamager());
        }
    }
}
