package me.vivimage25.rpgstats.event.listener;

import me.vivimage25.rpgstats.data.RPGStatsData;
import me.vivimage25.rpgstats.data.RPGStatsDataManager;
import me.vivimage25.rpgstats.event.RPGStatsDataAddedEvent;
import me.vivimage25.rpgstats.event.RPGStatsDataModifierAddedEvent;
import me.vivimage25.rpgstats.event.RPGStatsDataModifierRemovedEvent;
import me.vivimage25.rpgstats.event.RPGStatsDataRemovedEvent;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RPGStatsCustomListener implements Listener {

    @EventHandler
    public void onDataAdded(RPGStatsDataAddedEvent event) {
        this.updateStats(event.getEntity());
    }

    @EventHandler
    public void onDataRemoved(RPGStatsDataRemovedEvent event) {
        this.updateStats(event.getEntity());
    }

    @EventHandler
    public void onDataModifierAdded(RPGStatsDataModifierAddedEvent event) {
        this.updateStats(event.getEntity());
    }

    @EventHandler
    public void onDataModifierRemoved(RPGStatsDataModifierRemovedEvent event) {
        this.updateStats(event.getEntity());
    }

    private void updateStats(LivingEntity entity) {
        this.updateMaxHealth(entity);
        this.updateAttackDamage(entity);
    }

    private void updateMaxHealth(LivingEntity entity) {
        AttributeInstance attributeInstance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        RPGStatsData entityData = RPGStatsDataManager.getData(entity);
        if(attributeInstance != null && entityData != null) {
            double vitality = entityData.getVitality();
            double spirit = entityData.getSpirit();
            attributeInstance.setBaseValue(6.0 + ((vitality * 0.5) + (spirit * 0.25)) * 2.0);
        }
    }

    private void updateAttackDamage(LivingEntity entity) {
        AttributeInstance attributeInstance = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        RPGStatsData entityData = RPGStatsDataManager.getData(entity);
        if(attributeInstance != null && entityData != null) {
            double strength = entityData.getStrength();
            double spirit = entityData.getSpirit();
            attributeInstance.setBaseValue((((strength * 0.075) + (spirit * 0.025)) * 2));
        }
    }

}
