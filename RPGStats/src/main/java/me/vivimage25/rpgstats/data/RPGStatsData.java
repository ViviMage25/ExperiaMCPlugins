package me.vivimage25.rpgstats.data;

import com.google.common.base.Preconditions;
import me.vivimage25.rpgstats.event.RPGStatsDataModifierAddedEvent;
import me.vivimage25.rpgstats.event.RPGStatsDataModifierRemovedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RPGStatsData {
    private static final int MAXVALUE = 100, MINVALUE = 10;

    private final LivingEntity entity;
    private final int strength, dexterity, vitality, intelligence, spirit, luck;
    private final @NotNull ConcurrentMap<@NotNull String, @NotNull RPGStatsDataModifier> modifierMap;

    public RPGStatsData(LivingEntity entity, int strength, int dexterity, int vitality, int intelligence, int spirit, int luck) {
        Preconditions.checkNotNull(entity, "Constructor: RPGStatsData - must accept non null entity.");
        this.entity = entity;
        this.strength = Math.max(MINVALUE, Math.min(MAXVALUE, strength));
        this.dexterity = Math.max(MINVALUE, Math.min(MAXVALUE, dexterity));
        this.vitality = Math.max(MINVALUE, Math.min(MAXVALUE, vitality));
        this.intelligence = Math.max(MINVALUE, Math.min(MAXVALUE, intelligence));
        this.spirit = Math.max(MINVALUE, Math.min(MAXVALUE, spirit));
        this.luck = Math.max(MINVALUE, Math.min(MAXVALUE, luck));
        this.modifierMap = new ConcurrentHashMap<>();
    }

    public RPGStatsData cloneData() {
        RPGStatsData result = new RPGStatsData(this.entity, this.strength, this.dexterity, this.vitality, this.intelligence, this.spirit, this.luck);
        for(RPGStatsDataModifier modifier : this.modifierMap.values()) result.putModifier(modifier);
        return result;
    }

    public RPGStatsData cloneDataWithModifier(@NotNull RPGStatsDataModifier modifier) {
        Preconditions.checkNotNull(modifier, "Method: cloneDataWithModifier - must accept non-null modifier. (Internal values may be 0)");
        RPGStatsData result = new RPGStatsData(
                this.getEntity(),
                this.getBaseStrength() + modifier.getStrength(),
                this.getBaseDexterity() + modifier.getDexterity(),
                this.getBaseVitality() + modifier.getVitality(),
                this.getBaseIntelligence() + modifier.getIntelligence(),
                this.getBaseSpirit() + modifier.getSpirit(),
                this.getBaseLuck() + modifier.getLuck()
        );
        for(RPGStatsDataModifier modifier1 : this.modifierMap.values()) result.putModifier(modifier1);
        return result;
    }

    public void putModifier(@NotNull RPGStatsDataModifier modifier) {
        Preconditions.checkNotNull(modifier, "Method: putModifier - must accept non-null modifier. (Internal values may be 0)");
        RPGStatsDataModifierAddedEvent event = new RPGStatsDataModifierAddedEvent(this.entity, this, modifier);
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) this.modifierMap.put(modifier.getName(), modifier);
    }
    public void removeModifier(@NotNull String name) {
        Preconditions.checkNotNull(name, "Method: removeModifier - must accept non-null name. (Empty strings are possible!)");
        RPGStatsDataModifierRemovedEvent event = new RPGStatsDataModifierRemovedEvent(this.entity, this, this.getModifier(name));
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) this.modifierMap.remove(name);
    }
    public RPGStatsDataModifier getModifier(@NotNull String name) {
        Preconditions.checkNotNull(name, "Method: removeModifier - must accept non-null name. (Empty strings are possible!)");
        return this.modifierMap.get(name);
    }

    public LivingEntity getEntity() { return this.entity; }
    public int getBaseStrength() { return this.strength; }
    public int getBaseDexterity() { return this.dexterity; }
    public int getBaseVitality() { return this.vitality; }
    public int getBaseIntelligence() { return this.intelligence; }
    public int getBaseSpirit() { return this.spirit; }
    public int getBaseLuck() { return this.luck; }

    public int getStrength() {
        int result = this.getBaseStrength();
        for(RPGStatsDataModifier modifier : this.modifierMap.values()) {
            result += modifier.getStrength();
        }
        return Math.min(result, MAXVALUE);
    }
    public int getDexterity() {
        int result = this.getBaseDexterity();
        for(RPGStatsDataModifier modifier : this.modifierMap.values()) {
            result += modifier.getDexterity();
        }
        return Math.min(result, MAXVALUE);
    }
    public int getVitality() {
        int result = this.getBaseVitality();
        for(RPGStatsDataModifier modifier : this.modifierMap.values()) {
            result += modifier.getVitality();
        }
        return Math.min(result, MAXVALUE);
    }
    public int getIntelligence() {
        int result = this.getBaseIntelligence();
        for(RPGStatsDataModifier modifier : this.modifierMap.values()) {
            result += modifier.getIntelligence();
        }
        return Math.min(result, MAXVALUE);
    }
    public int getSpirit() {
        int result = this.getBaseSpirit();
        for(RPGStatsDataModifier modifier : this.modifierMap.values()) {
            result += modifier.getSpirit();
        }
        return Math.min(result, MAXVALUE);
    }
    public int getLuck() {
        int result = this.getBaseLuck();
        for(RPGStatsDataModifier modifier : this.modifierMap.values()) {
            result += modifier.getLuck();
        }
        return Math.min(result, MAXVALUE);
    }
}
