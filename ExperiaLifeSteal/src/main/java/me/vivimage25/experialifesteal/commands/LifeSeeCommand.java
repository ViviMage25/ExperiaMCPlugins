package me.vivimage25.experialifesteal.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LifeSeeCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player target = null;
        if(command.getPermission() != null) {
            if(sender.hasPermission(command.getPermission())) {
                if(args.length == 1) {
                    sender.hasPermission(command.getPermission().concat(".others"));
                    String targetName = args[0];
                    target = Bukkit.getPlayer(targetName);
                    if(target == null) {
                        sender.sendMessage(ChatColor.RED.toString().concat("Player MUST be online!"));
                        return true;
                    }
                }
                if(target == null && sender instanceof Player) {
                    target = (Player) sender;
                }
                if(target == null){
                    sender.sendMessage(ChatColor.RED.toString().concat("Only Players can see their Health!"));
                    return true;
                }
                AttributeInstance maxHealthAttribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(maxHealthAttribute != null) {
                    double lives = maxHealthAttribute.getBaseValue() / 2.0;
                    sender.sendMessage(ChatColor.GREEN.toString().concat(target.getName()).concat(" has ").concat(String.valueOf(lives)).concat(" lives"));
                } else {
                    sender.sendMessage(ChatColor.RED.toString().concat("Cannot access MAX_HEALTH"));
                }
            } else {
                sender.sendMessage(ChatColor.RED.toString().concat("You DO NOT have permission to do that!"));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length == 1) {
            List<String> playerList = new ArrayList<>();
            for(Player player : Bukkit.getOnlinePlayers()) {
                playerList.add(player.getName());
            }
            StringUtil.copyPartialMatches(args[0], playerList, result);
        }
        return result;
    }
}
