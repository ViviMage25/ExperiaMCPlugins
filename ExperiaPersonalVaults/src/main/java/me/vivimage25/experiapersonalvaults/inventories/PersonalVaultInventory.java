package me.vivimage25.experiapersonalvaults.inventories;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PersonalVaultInventory {

    private static final Map<UUID, PersonalVaultInventory> VAULT_INVENTORY_MAP = new HashMap<>();

    public static PersonalVaultInventory createNewInventory(Player owner, int size) {
        PersonalVaultInventory result = new PersonalVaultInventory(owner, size);
        VAULT_INVENTORY_MAP.put(owner.getUniqueId(), result);
        return result;
    }

    public static PersonalVaultInventory addInventory(Player owner, ItemStack[] items) {
        PersonalVaultInventory result = new PersonalVaultInventory(owner, items);
        VAULT_INVENTORY_MAP.put(owner.getUniqueId(), result);
        return result;
    }

    private final Player owner;
    private final Inventory inventory;

    private PersonalVaultInventory(Player owner, int size) {
        String inventoryTitle = owner.displayName().toString().concat("'s Personal Vault");

        this.owner = owner;
        this.inventory = Bukkit.createInventory(this.owner, size, Component.text(inventoryTitle));
    }

    private PersonalVaultInventory(Player owner, ItemStack[] items) {
        String inventoryTitle = owner.displayName().toString().concat("'s Personal Vault");
        this.owner = owner;
        this.inventory = Bukkit.createInventory(this.owner, items.length, Component.text(inventoryTitle));
        this.inventory.setContents(items);
    }

    public Player getOwner() {
        return this.owner;
    }

    public Inventory getInventory() {
        return this.inventory;
    }


}
