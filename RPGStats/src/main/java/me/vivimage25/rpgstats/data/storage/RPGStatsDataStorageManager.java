package me.vivimage25.rpgstats.data.storage;

import com.google.common.base.Preconditions;
import me.vivimage25.rpgstats.config.RPGStatsConfig;
import me.vivimage25.rpgstats.data.RPGStatsDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RPGStatsDataStorageManager {
    private static RPGStatsDataStorage DATA_STORAGE;

    private static RPGStatsDataStorage getDataStorage() {
        if(DATA_STORAGE == null) {
            switch(RPGStatsConfig.getDataStorage().toLowerCase()) {
                case "pdc":
                    DATA_STORAGE = new RPGStatsPDCDataStorage();
                    break;
                case "mysql": case "mariadb":
                    DATA_STORAGE = new RPGStatsSQLDataStorage();
                    break;
                case "binary":
                    DATA_STORAGE = new RPGStatsBinaryDataStorage();
                    break;
                case "yml": case "yaml": default:
                    DATA_STORAGE = new RPGStatsYAMLDataStorage();
                    break;
            }
        }
        return DATA_STORAGE;
    }

    public static boolean save(@NotNull Player player) {
        Preconditions.checkNotNull(player, "Method: save - must accept non null player");
        return getDataStorage().save(player);
    }
    public static boolean load(@NotNull Player player) {
        Preconditions.checkNotNull(player, "Method: load - must accept non null player");
        return getDataStorage().load(player);
    }
    public static void saveAll() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(RPGStatsDataManager.hasData(player)) save(player);
        }
    }
    public static void loadAll() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(!RPGStatsDataManager.hasData(player)) load(player);
        }
    }

    private RPGStatsDataStorageManager() {}
}
