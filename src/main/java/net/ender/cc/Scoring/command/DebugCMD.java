package net.ender.cc.Scoring.command;

import net.ender.cc.Scoring.listener.custom.GameStartListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.ender.cc.Scoring.listener.custom.GameStartListener.currentTimeSeconds;

public class DebugCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        switch (args[0].toLowerCase()) {
            case "addtime": {
                currentTimeSeconds += Integer.parseInt(args[1]);
                break;
            }
            case "gamestop": {
                GameStartListener.stageIndex = 6;
                currentTimeSeconds = 1798;
                break;
            }
        }
        return true;
    }
}
