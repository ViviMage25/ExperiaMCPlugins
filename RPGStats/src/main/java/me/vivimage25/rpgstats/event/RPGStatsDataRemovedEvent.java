package me.vivimage25.rpgstats.event;

import me.vivimage25.rpgstats.data.RPGStatsData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RPGStatsDataRemovedEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    private final LivingEntity entity;
    private final RPGStatsData data;
    private boolean cancelled;

    public RPGStatsDataRemovedEvent(LivingEntity entity, RPGStatsData data) {
        this.entity = entity;
        this.data = data;
        this.cancelled = false;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public RPGStatsData getData() {
        return this.data;
    }
}
