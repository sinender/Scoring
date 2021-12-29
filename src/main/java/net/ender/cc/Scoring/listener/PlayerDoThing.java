package net.ender.cc.Scoring.listener;

import net.ender.cc.Scoring.listener.custom.GameStartListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerDoThing implements Listener {
    @EventHandler
    public void onPlayerBreak(BlockBreakEvent event) {
        if (GameStartListener.frozenPlayers.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent event) {
        if (GameStartListener.frozenPlayers.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (GameStartListener.frozenPlayers.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
