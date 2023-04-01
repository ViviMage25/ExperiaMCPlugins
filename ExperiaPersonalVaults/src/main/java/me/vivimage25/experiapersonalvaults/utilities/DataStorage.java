package me.vivimage25.experiapersonalvaults.utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface DataStorage {

    boolean save(Player player, ItemStack[] items);
    ItemStack[] load(Player player);

}
