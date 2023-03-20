package me.vivimage25.experialifesteal.commands;

import me.vivimage25.experialifesteal.listeners.PlayerListeners;
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

public class LifeModCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getPermission() != null) {
            if(sender.hasPermission(command.getPermission())) {
                if(args.length == 3) {
                    String subCommand = args[0];
                    String playerName = args[1];
                    double lifeAmount = Double.parseDouble(args[2]) * 2.0;

                    switch (subCommand.toLowerCase()) {
                        case "set" -> {
                            if (sender.hasPermission(command.getPermission().concat(".set"))) {
                                Player player = Bukkit.getPlayer(playerName);
                                if (player != null) {
                                    AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                    if (maxHealthAttribute != null) {
                                        if (lifeAmount >= 2.0 && lifeAmount <= PlayerListeners.max_limit_amount) {
                                            maxHealthAttribute.setBaseValue(lifeAmount);
                                        } else {
                                            sender.sendMessage(ChatColor.RED.toString().concat("Lives MUST be greater than 1 and less than ").concat(String.valueOf(PlayerListeners.max_limit_amount / 2)));
                                        }
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED.toString().concat("Player MUST be online!"));
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED.toString().concat("You DO NOT have permission to do that!"));
                            }
                        }
                        case "add" -> {
                            if (sender.hasPermission(command.getPermission().concat(".add"))) {
                                Player player = Bukkit.getPlayer(playerName);
                                if (player != null) {
                                    AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                    if (maxHealthAttribute != null) {
                                        double current = maxHealthAttribute.getBaseValue();
                                        double next = current + lifeAmount;
                                        if(next <= PlayerListeners.max_limit_amount) {
                                            maxHealthAttribute.setBaseValue(next);
                                        } else {
                                            sender.sendMessage(ChatColor.RED.toString().concat("Amount cannot result in over ").concat(String.valueOf(PlayerListeners.max_limit_amount / 2)).concat(" lives"));
                                        }
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED.toString().concat("Player MUST be online!"));
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED.toString().concat("You DO NOT have permission to do that!"));
                            }
                        }
                        case "remove" -> {
                            if (sender.hasPermission(command.getPermission().concat(".remove"))) {
                                Player player = Bukkit.getPlayer(playerName);
                                if (player != null) {
                                    AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                    if (maxHealthAttribute != null) {
                                        double current = maxHealthAttribute.getBaseValue();
                                        double next = current - lifeAmount;
                                        if (next >= 2.0) {
                                            maxHealthAttribute.setBaseValue(next);
                                        } else {
                                            sender.sendMessage(ChatColor.RED.toString().concat("Lives MUST be greater than 1!"));
                                        }
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED.toString().concat("Player MUST be online!"));
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED.toString().concat("You DO NOT have permission to do that!"));
                            }
                        }
                        default ->
                                sender.sendMessage(ChatColor.RED.toString().concat("SOMETHING WENT WRONG!!! PLEASE REPORT TO DEVELOPER!!!"));
                    }
                } else {
                    sender.sendMessage(ChatColor.RED.toString().concat("This command requires 3 arguments"));
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> result = new ArrayList<>();
        if(args.length == 1) {
            List<String> subcmdList = new ArrayList<>();
            subcmdList.add("set");
            subcmdList.add("add");
            subcmdList.add("remove");
            StringUtil.copyPartialMatches(args[0], subcmdList, result);
        }
        if(args.length == 2) {
            List<String> playerList = new ArrayList<>();
            for(Player player : Bukkit.getOnlinePlayers()) {
                playerList.add(player.getName());
            }
            StringUtil.copyPartialMatches(args[1], playerList, result);
        }
        if(args.length == 3) {
            String subcmd = args[0];
            String playerName = args[1];
            Player player = Bukkit.getPlayer(playerName);
            if(player != null) {
                AttributeInstance attrib = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                List<String> numberList = new ArrayList<>();
                switch (subcmd.toLowerCase()) {
                    case "set" -> {
                        for (int i = 1; i <= PlayerListeners.max_limit_amount / 2; i++) {
                            numberList.add(String.valueOf(i));
                        }
                        StringUtil.copyPartialMatches(args[2], numberList, result);
                    }
                    case "add" -> {
                        if (attrib != null) {
                            for (int i = (int) (PlayerListeners.max_limit_amount - attrib.getBaseValue()) / 2; i >= 1; i--) {
                                numberList.add(String.valueOf(i));
                            }
                            StringUtil.copyPartialMatches(args[2], numberList, result);
                        }
                    }
                    case "remove" -> {
                        if (attrib != null) {
                            for (int i = 0; i <= attrib.getBaseValue() / 2; i++) {
                                numberList.add(String.valueOf(i));
                            }
                            StringUtil.copyPartialMatches(args[2], numberList, result);
                        }
                    }
                }
            }
        }
        return result;
    }
}
