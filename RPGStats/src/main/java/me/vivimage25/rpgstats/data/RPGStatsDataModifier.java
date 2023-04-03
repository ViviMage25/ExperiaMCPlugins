package me.vivimage25.rpgstats.data;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public class RPGStatsDataModifier {
    private static final int MAXVALUE = 10, MINVALUE = -10;

    private final @NotNull String name;
    private final int strength, dexterity, vitality, intelligence, spirit, luck;

    public RPGStatsDataModifier(@NotNull String name, int strength, int dexterity, int vitality, int intelligence, int spirit, int luck) {
        Preconditions.checkNotNull(name, "Constructor: RPGStatsModifier - must accept non-null name. (Empty strings are possible!)");
        this.name = name;
        this.strength = Math.max(MINVALUE, Math.min(MAXVALUE, strength));
        this.dexterity = Math.max(MINVALUE, Math.min(MAXVALUE, dexterity));
        this.vitality = Math.max(MINVALUE, Math.min(MAXVALUE, vitality));
        this.intelligence = Math.max(MINVALUE, Math.min(MAXVALUE, intelligence));
        this.spirit = Math.max(MINVALUE, Math.min(MAXVALUE, spirit));
        this.luck = Math.max(MINVALUE, Math.min(MAXVALUE, luck));
    }

    public @NotNull String getName() { return this.name; }
    public int getStrength() { return this.strength; }
    public int getDexterity() { return this.dexterity; }
    public int getVitality() { return this.vitality; }
    public int getIntelligence() { return this.intelligence; }
    public int getSpirit() { return this.spirit; }
    public int getLuck() { return this.luck; }
}
