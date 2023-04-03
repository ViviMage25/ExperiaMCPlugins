package me.vivimage25.rpgstats.data;

import com.google.common.base.Preconditions;
import me.vivimage25.rpgstats.event.RPGStatsDataAddedEvent;
import me.vivimage25.rpgstats.event.RPGStatsDataRemovedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RPGStatsDataManager {
    private static final @NotNull ConcurrentMap<@NotNull LivingEntity, @NotNull RPGStatsData> ENTITY_STATS_MAP = new ConcurrentHashMap<>();

    public static void addData(@NotNull RPGStatsData data) {
        Preconditions.checkNotNull(data, "Method: addData - must accept non null data. (Internal values may be 0)");
        ENTITY_STATS_MAP.put(data.getEntity(), data);
        RPGStatsDataAddedEvent event = new RPGStatsDataAddedEvent(data.getEntity(), data);
        Bukkit.getPluginManager().callEvent(event);
    }
    public static void removeData(@NotNull LivingEntity entity) {
        Preconditions.checkNotNull(entity, "Method: removeData - must accept non null entity.");
        ENTITY_STATS_MAP.remove(entity);
        RPGStatsDataRemovedEvent event = new RPGStatsDataRemovedEvent(entity, getData(entity));
        Bukkit.getPluginManager().callEvent(event);
    }
    public static RPGStatsData getData(@NotNull LivingEntity entity) {
        Preconditions.checkNotNull(entity, "Method: getData - must accept non null entity.");
        return ENTITY_STATS_MAP.get(entity);
    }
    public static boolean hasData(@NotNull LivingEntity entity) {
        Preconditions.checkNotNull(entity, "Method: getData - must accept non null entity.");
        return ENTITY_STATS_MAP.containsKey(entity);
    }
    public static @NotNull Set<@NotNull LivingEntity> getKeySet() {
        return ENTITY_STATS_MAP.keySet();
    }

    private RPGStatsDataManager() {}
}
