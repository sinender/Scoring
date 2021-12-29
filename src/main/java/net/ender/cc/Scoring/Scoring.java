
package net.ender.cc.Scoring;

import net.ender.cc.Scoring.Games.Games;
import net.ender.cc.Scoring.command.DebugCMD;
import net.ender.cc.Scoring.command.GameStartCMD;
import net.ender.cc.Scoring.database.MongoPlayerData;
import net.ender.cc.Scoring.listener.PlayerDeathListener;
import net.ender.cc.Scoring.listener.PlayerDismountListener;
import net.ender.cc.Scoring.listener.PlayerDoThing;
import net.ender.cc.Scoring.listener.PlayerJoinListener;
import net.ender.cc.Scoring.listener.custom.GameEndListener;
import net.ender.cc.Scoring.listener.custom.GameStartListener;
import net.ender.cc.Scoring.util.placeholders.PlaceHolderAPI;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spicord.Spicord;
import org.spicord.bot.DiscordBot;

public class Scoring extends JavaPlugin
{
    public static Scoring plugin;
    public Set<DiscordBot> bots;
    
    public void onEnable() {
        (Scoring.plugin = this).saveDefaultConfig();
        new MongoPlayerData().connect();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceHolderAPI().register();
        }
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new GameStartListener(), this);
        pm.registerEvents(new PlayerDismountListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new GameEndListener(), this);
        pm.registerEvents(new PlayerDoThing(), this);

        getCommand("startgame").setExecutor(new GameStartCMD());
        getCommand("debug").setExecutor(new DebugCMD());
    }

    public void onDisable() {
    }
    
    public static Scoring getInstance() {
        return Scoring.plugin;
    }

    public void addBot(DiscordBot bot) {
        bots.add(bot);
    }

}
