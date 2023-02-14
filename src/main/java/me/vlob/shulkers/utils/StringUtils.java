package me.vlob.shulkers.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    public static String color(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static boolean sendMessage(Player player, String message){
        player.sendMessage(color(message));
        return true;
    }

    public static boolean sendMessage(CommandSender commandSender, String message){
        commandSender.sendMessage(color(message));
        return true;
    }

    public static List<String> color(List<String> lore) {
        return lore.stream()
                .map(StringUtils::color)
                .collect(Collectors.toList());
    }

    public static String replace(String text, String searchString, String replacement) {
        if ((text == null) || (text.isEmpty()) || (searchString.isEmpty()) || (replacement == null)) {
            return text;
        }
        int start = 0;
        int max = -1;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replacedLength = searchString.length();
        int increase = replacement.length() - replacedLength;
        increase = increase < 0 ? 0 : increase;
        increase *= (max > 64 ? 64 : max < 0 ? 16 : max);
        StringBuilder sb = new StringBuilder(text.length() + increase);
        while (end != -1){
            sb.append(text.substring(start, end)).append(replacement);
            start = end + replacedLength;
            max--;
            if (max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        sb.append(text.substring(start));
        return sb.toString();
    }

}
