package me.vivimage25.rpgstats.event;

import me.vivimage25.rpgstats.data.RPGStatsData;
import me.vivimage25.rpgstats.data.RPGStatsDataModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RPGStatsDataModifierAddedEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    private final LivingEntity entity;
    private final RPGStatsData data;
    private final RPGStatsDataModifier modifier;
    private boolean cancelled;

    public RPGStatsDataModifierAddedEvent(LivingEntity entity, RPGStatsData data, RPGStatsDataModifier modifier) {
        this.entity = entity;
        this.data = data;
        this.modifier = modifier;
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

    public RPGStatsDataModifier getModifier() {
        return this.modifier;
    }
}
