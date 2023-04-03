package me.vivimage25.rpgstats.event;

import me.vivimage25.rpgstats.data.RPGStatsData;
import me.vivimage25.rpgstats.data.RPGStatsDataModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RPGStatsDataModifierAddedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    private final LivingEntity entity;
    private final RPGStatsData data;
    private final RPGStatsDataModifier modifier;

    public RPGStatsDataModifierAddedEvent(LivingEntity entity, RPGStatsData data, RPGStatsDataModifier modifier) {
        this.entity = entity;
        this.data = data;
        this.modifier = modifier;
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

    public RPGStatsDataModifier getModifier() {
        return this.modifier;
    }
}
