package me.vivimage25.rpgstats.event;

import me.vivimage25.rpgstats.data.RPGStatsData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RPGStatsDataRemovedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    private final LivingEntity entity;
    private final RPGStatsData data;

    public RPGStatsDataRemovedEvent(LivingEntity entity, RPGStatsData data) {
        this.entity = entity;
        this.data = data;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public RPGStatsData getData() {
        return this.data;
    }
}
