package me.vivimage25.experiapersonalvaults.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private static final Map<Player, VaultInventory> VAULT_INVENTORY_MAP = new HashMap<>();

    public static void addNewInventory(Player owner) {
        VaultInventory result = new VaultInventory(owner);
        VAULT_INVENTORY_MAP.put(owner, result);
    }

    public static void addInventory(Player owner, ItemStack[] items) {
        VaultInventory result = new VaultInventory(owner, items);
        VAULT_INVENTORY_MAP.put(owner, result);
    }

    public static void removeInventory(Player owner) {
        VAULT_INVENTORY_MAP.remove(owner);
    }

    public static VaultInventory getVaultInventory(Player owner) {
        return VAULT_INVENTORY_MAP.get(owner);
    }

    public static Map<Player, VaultInventory> getVaultInventoryMap() {
        return VAULT_INVENTORY_MAP;
    }

    private InventoryManager() {}
}
