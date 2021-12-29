package net.ender.cc.Scoring.listener;

import net.ender.cc.Scoring.Games.player.PlayerData;
import net.ender.cc.Scoring.listener.custom.GameStartListener;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import static net.ender.cc.Scoring.listener.custom.GameStartListener.players;
import static net.ender.cc.Scoring.util.Util.colorize;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (players.contains(player)) {
            GameStartListener.playersAlive.remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            if (player.getKiller() != null) {
                Player killer = player.getKiller();
                int score = PlayerData.score.get(killer.getUniqueId());
                PlayerData.score.put(killer.getUniqueId(), score + 70);
                killer.sendMessage(colorize("&f[&a+70&f]"));
            }
            for (Player p : GameStartListener.playersAlive) {
                int score = PlayerData.score.get(p.getUniqueId());
                PlayerData.score.put(p.getUniqueId(), score + 10);
                p.sendMessage(colorize("&f[&a+10&f]"));
            }
        }
    }
}
