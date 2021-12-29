package net.ender.cc.Scoring.command;

import net.ender.cc.Scoring.Games.Skyblockle.Skyblockle;
import net.ender.cc.Scoring.Games.teams.Team;
import net.ender.cc.Scoring.Games.teams.TeamData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.ender.cc.Scoring.util.Util.getRandom;

public class GameStartCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if ("skyblockle".equalsIgnoreCase(args[0])) {
                new Skyblockle().start();
            }
        }
        return false;
    }
}
