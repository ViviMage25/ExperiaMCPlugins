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
        this.updateMaxHealth(event.getEntity());
    }

    @EventHandler
    public void onDataRemoved(RPGStatsDataRemovedEvent event) {
        this.updateMaxHealth(event.getEntity());
    }

    @EventHandler
    public void onDataModifierAdded(RPGStatsDataModifierAddedEvent event) {
        this.updateMaxHealth(event.getEntity());
    }

    @EventHandler
    public void onDataModifierRemoved(RPGStatsDataModifierRemovedEvent event) {
        this.updateMaxHealth(event.getEntity());
    }

    private void updateMaxHealth(LivingEntity entity) {
        AttributeInstance attributeInstance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        RPGStatsData entityData = RPGStatsDataManager.getData(entity);
        if(attributeInstance != null && entityData != null) {
            int vitality = entityData.getVitality();
            attributeInstance.setBaseValue(vitality * 2);
        }
    }

}
