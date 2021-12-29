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
            switch (args[0].toLowerCase()) {
                case "skyblockle": {
                    new Skyblockle().start();
                    break;
                }
                case "test": {
                    for (Team team : TeamData.score.keySet()) {
                        TeamData.score.put(team, getRandom(0, 100));
                    }
                    break;
                }
            }
        }
        return false;
    }
}
