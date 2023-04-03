package me.vivimage25.rpgstats.data.storage;

import org.bukkit.entity.Player;

public class RPGStatsSQLDataStorage implements RPGStatsDataStorage {
    @Override
    public boolean save(Player player) {
        return false;
    }

    @Override
    public boolean load(Player player) {
        return false;
    }
}
