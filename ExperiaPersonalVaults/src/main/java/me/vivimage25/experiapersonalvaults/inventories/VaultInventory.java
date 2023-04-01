package me.vivimage25.experiapersonalvaults.inventories;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class VaultInventory implements InventoryHolder {

    private final Player owner;
    private final ItemStack[] items;
    private final Component title;

    protected VaultInventory(Player owner) {
        String inventoryTitle = owner.getName().concat("'s Personal Vault");

        this.owner = owner;
        this.items = new ItemStack[54];
        this.title = Component.text(inventoryTitle);
    }

    protected VaultInventory(Player owner, ItemStack[] items) {
        String inventoryTitle = owner.getName().concat("'s Personal Vault");
        this.owner = owner;
        this.items = new ItemStack[54];
        System.arraycopy(items, 0, this.items, 0, items.length);
        this.title = Component.text(inventoryTitle);
    }

    public Player getOwner() {
        return this.owner;
    }

    public ItemStack[] getItems() {
        return this.items;
    }

    public void setItems(ItemStack[] items) {
        System.arraycopy(items, 0, this.items, 0, Math.min(items.length, this.items.length));
    }

    public Component getTitle() {
        return this.title;
    }

    public int getSize() {
        if(this.owner.hasPermission("epv.vault.rows.6")) return 6;
        if(this.owner.hasPermission("epv.vault.rows.5")) return 5;
        if(this.owner.hasPermission("epv.vault.rows.4")) return 4;
        if(this.owner.hasPermission("epv.vault.rows.3")) return 3;
        if(this.owner.hasPermission("epv.vault.rows.2")) return 2;
        if(this.owner.hasPermission("epv.vault.rows.1")) return 1;
        return 0;
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, this.getSize() * 9, this.getTitle());
        ItemStack[] contents = inventory.getContents();
        System.arraycopy(this.items, 0, contents, 0, contents.length);
        inventory.setContents(contents);
        return inventory;
    }
}
