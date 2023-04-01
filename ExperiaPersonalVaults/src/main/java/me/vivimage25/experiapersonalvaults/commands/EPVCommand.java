package me.vivimage25.experiapersonalvaults.commands;

import me.vivimage25.experiapersonalvaults.inventories.InventoryManager;
import me.vivimage25.experiapersonalvaults.inventories.VaultInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EPVCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                if (player.hasPermission("epv.command.epv")) {
                    VaultInventory vaultInventory = InventoryManager.getVaultInventory(player);
                    player.openInventory(vaultInventory.getInventory());
                }
            } else if(args.length == 1) {
                if (player.hasPermission("epv.command.epv.other")) {
                    String playerName = args[0];
                    Player target = Bukkit.getPlayer(playerName);
                    if(target != null) {
                        VaultInventory vaultInventory = InventoryManager.getVaultInventory(target);
                        player.openInventory(vaultInventory.getInventory());
                    } else {
                        player.sendMessage(ChatColor.RED.toString().concat("Player is not online!"));
                    }
                }
            }
        }
        return true;
    }

}
