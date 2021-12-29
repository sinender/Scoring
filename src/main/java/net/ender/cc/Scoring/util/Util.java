package net.ender.cc.Scoring.util;

import me.clip.placeholderapi.util.jsonmessage.JSONMessage;
import net.ender.cc.Scoring.Games.teams.Team;
import net.ender.cc.Scoring.Scoring;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

public class Util
{
    public static String colorize(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> colorize(final String... s) {
        List<String> list = new ArrayList<>();
        for (String st : s) {
            list.add(colorize(st));
        }
        return list;
    }

    public static LinkedHashMap<Team, Integer> getSortedMap(final Map<Team, Integer> input) {
        return input.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public static Integer getRandom(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
