package me.vivimage25.experiapersonalvaults.utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface DataStorage {

    void save(Player player, ItemStack[] items);
    ItemStack[] load(Player player);

}
