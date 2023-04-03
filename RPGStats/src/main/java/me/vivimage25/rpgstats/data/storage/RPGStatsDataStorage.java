package me.vivimage25.rpgstats.data.storage;

import org.bukkit.entity.Player;

public interface RPGStatsDataStorage {
    boolean save(Player player);
    boolean load(Player player);
}
