package net.ender.cc.Scoring.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

import static net.ender.cc.Scoring.listener.custom.GameStartListener.frozenPlayers;

public class PlayerDismountListener implements Listener {
    @EventHandler
    public void onPlayerDismount(VehicleExitEvent event) {
        if (event.getExited() instanceof Player) {
            if (frozenPlayers.containsKey(event.getExited().getUniqueId())) {
                event.setCancelled(true);
                event.setCancelled(true);
            }
        }
    }
}
