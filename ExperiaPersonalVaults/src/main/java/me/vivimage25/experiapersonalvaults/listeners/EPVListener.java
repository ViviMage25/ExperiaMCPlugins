package me.vivimage25.experiapersonalvaults.listeners;

import me.vivimage25.experiapersonalvaults.EPVPlugin;
import me.vivimage25.experiapersonalvaults.inventories.InventoryManager;
import me.vivimage25.experiapersonalvaults.inventories.VaultInventory;
import me.vivimage25.experiapersonalvaults.utilities.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EPVListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack[] items = DataManager.load(player);
        if(items != null) {
            InventoryManager.addInventory(player, items);
            EPVPlugin.getInstance().getLogger().info(player.getName().concat("'s Personal Vault has been loaded!"));
        } else {
            InventoryManager.addNewInventory(player);
            EPVPlugin.getInstance().getLogger().info(player.getName().concat("'s Personal Vault has been created!"));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        VaultInventory vaultInventory = InventoryManager.getVaultInventory(player);
        ItemStack[] items = vaultInventory.getItems();
        if(DataManager.save(player, items)) {
            EPVPlugin.getInstance().getLogger().info(player.getName().concat("'s Personal Vault has been saved! Removing..."));
            InventoryManager.removeInventory(player);
        } else {
            EPVPlugin.getInstance().getLogger().info(player.getName().concat("'s Personal Vault has not been saved!"));
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        ItemStack[] contents = inventory.getContents();
        if(inventory.getHolder() instanceof VaultInventory) {
            VaultInventory target = (VaultInventory) inventory.getHolder();
            target.setItems(contents);
        }

    }

}
