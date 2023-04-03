package me.vivimage25.rpgstats;

import me.vivimage25.rpgstats.event.listener.RPGStatsCustomListener;
import me.vivimage25.rpgstats.event.listener.RPGStatsEntityListener;
import me.vivimage25.rpgstats.event.listener.RPGStatsPlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGStatsPlugin extends JavaPlugin {

    public static RPGStatsPlugin getInstance() {
        return (RPGStatsPlugin) Bukkit.getServer().getPluginManager().getPlugin("RPGStats");
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new RPGStatsPlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new RPGStatsEntityListener(), this);
        this.getServer().getPluginManager().registerEvents(new RPGStatsCustomListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
