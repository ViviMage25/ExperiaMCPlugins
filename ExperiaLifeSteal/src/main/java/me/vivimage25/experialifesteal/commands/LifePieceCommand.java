package me.vivimage25.experialifesteal.commands;

import me.vivimage25.experialifesteal.items.LifePieceItem;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LifePieceCommand implements TabExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(command.getPermission() != null) {
                if (player.hasPermission(command.getPermission())) {
                    double amount = 2.0;
                    if(args.length == 1) {
                        amount = Double.parseDouble(args[0]) * 2.0;
                    }
                    AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    if(maxHealthAttribute != null) {
                        double current = maxHealthAttribute.getBaseValue();
                        double next = current - amount;
                        if(next >= 2.0) {
                            maxHealthAttribute.setBaseValue(next);
                            Map<Integer, ItemStack> dropItems = player.getInventory().addItem(LifePieceItem.create((int) (amount / 2)));
                            if(!dropItems.isEmpty()) {
                                for(ItemStack item: dropItems.values()) {
                                    player.getWorld().dropItemNaturally(player.getLocation(), item);
                                }
                            }
                            player.sendMessage(ChatColor.GREEN.toString().concat("Life removed!"));
                        } else {
                            player.sendMessage(ChatColor.RED.toString().concat("Cannot remove your last life"));
                        }
                    } else {
                        player.sendMessage(ChatColor.RED.toString().concat("Cannot access MAX_HEALTH"));
                    }
                } else {
                    player.sendMessage(ChatColor.RED.toString().concat("You DO NOT have permission to do that!"));
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED.toString().concat("This command is only usable by a Player"));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> result = new ArrayList<>();
        if(sender instanceof Player player) {
            if (args.length == 1) {
                AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (maxHealthAttribute != null) {
                    List<String> numberList = new ArrayList<>();
                    for (int i = 0; i < maxHealthAttribute.getBaseValue() / 2; i++) {
                        numberList.add(String.valueOf(i));
                    }
                    StringUtil.copyPartialMatches(args[0], numberList, result);
                }
            }
        }
        return result;
    }

}
