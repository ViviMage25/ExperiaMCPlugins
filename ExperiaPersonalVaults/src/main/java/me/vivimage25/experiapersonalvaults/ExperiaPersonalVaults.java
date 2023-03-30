package me.vivimage25.experiapersonalvaults;

import org.bukkit.plugin.java.JavaPlugin;

public final class ExperiaPersonalVaults extends JavaPlugin {

    private static ExperiaPersonalVaults INSTANCE;

    public static ExperiaPersonalVaults getInstance() {
        return INSTANCE;
    }

    public ExperiaPersonalVaults() {
        if(INSTANCE == null) INSTANCE = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
