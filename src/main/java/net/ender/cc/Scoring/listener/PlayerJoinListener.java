package net.ender.cc.Scoring.listener;

import net.ender.cc.Scoring.database.MongoPlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        MongoPlayerData mongo = new MongoPlayerData();

        mongo.createPlayerData(player.getUniqueId());
    }
}
