package net.ender.cc.Scoring.Games.Skyblockle;

import net.ender.cc.Scoring.Games.Games;
import net.ender.cc.Scoring.events.OnGameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class Skyblockle extends Games {
    @Override
    public void start() {
        Bukkit.getPluginManager().callEvent(new OnGameStartEvent("Skyblockle"));
    }

    public enum SpawnLocations {
        TEAM_1("skyblockle", 0.5F, 78.0F, -148.5F);

        String world;
        float x;
        float y;
        float z;

        SpawnLocations(String world, float x, float y, float z) {
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Location getLocation() {
            return new Location(Bukkit.getWorld(world), x, y, z);
        }

        public Location getLocation(float pitch, float yaw) {
            return new Location(Bukkit.getWorld(world), x, y, z, pitch, yaw);
        }
    }
}
