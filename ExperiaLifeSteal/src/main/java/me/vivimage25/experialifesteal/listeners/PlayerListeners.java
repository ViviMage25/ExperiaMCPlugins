package me.vivimage25.experialifesteal.listeners;

import me.vivimage25.experialifesteal.items.LifePieceItem;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListeners implements Listener {
    public static double regain_modifier = 0.50;
    public static double max_limit_amount = 60.0;

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // If damager or entity is not an instance of Player then return
        if(!(event.getDamager() instanceof Player damager)) return;
        AttributeInstance damagerMaxHealth = damager.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (damagerMaxHealth != null) {
            // Regen some health from attack
            if(damager.getHealth() + (event.getDamage() * regain_modifier) <= damagerMaxHealth.getBaseValue()) damager.setHealth(damager.getHealth() + (event.getDamage() * regain_modifier));
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        ItemStack lifePieceItem = LifePieceItem.create();
        Player player = event.getPlayer();
        Location location = player.getLocation();
        World world = player.getWorld();
        AttributeInstance attrib = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attrib != null) {
            double current = attrib.getBaseValue();
            double next = current - 2;
            if(next >= 0.0) {
                attrib.setBaseValue(next);
                world.dropItemNaturally(location, lifePieceItem);
                if(next == 0.0) {
                    player.setGameMode(GameMode.SPECTATOR);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if(item != null) {
            if(item.getType() == LifePieceItem.ITEM_TYPE) {
                ItemMeta meta = item.getItemMeta();
                if(meta != null && meta.hasCustomModelData()) {
                    if(meta.getCustomModelData() == LifePieceItem.ITEM_MODEL_NUMBER) {
                        if(player.getFoodLevel() == 20) {
                            player.setFoodLevel(19);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if (item.getType() == LifePieceItem.ITEM_TYPE) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasCustomModelData()) {
                if (meta.getCustomModelData() == LifePieceItem.ITEM_MODEL_NUMBER) {
                    AttributeInstance attrib = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    if (attrib != null) {
                        double current = attrib.getBaseValue();
                        double next = current + 2.0;
                        if (next <= max_limit_amount) {
                            attrib.setBaseValue(next);
                            player.sendMessage(ChatColor.GREEN.toString().concat("You have gained a life"));
                        } else {
                            player.sendMessage(ChatColor.RED.toString().concat("You cannot have more than ").concat(String.valueOf(max_limit_amount / 2)).concat(" lives"));
                        }
                    }
                }
            }
        }
    }

}
