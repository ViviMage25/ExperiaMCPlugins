package me.vivimage25.experiapersonalvaults;

import me.vivimage25.experiapersonalvaults.commands.EPVCommand;
import me.vivimage25.experiapersonalvaults.listeners.EPVListener;
import me.vivimage25.experiapersonalvaults.utilities.DataManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class EPVPlugin extends JavaPlugin {

    private static EPVPlugin INSTANCE;

    public static EPVPlugin getInstance() {
        return INSTANCE;
    }

    public EPVPlugin() {
        if(INSTANCE == null) INSTANCE = this;
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Loading/Parsing Config");
        this.saveDefaultConfig();
        DataManager.getData();

        this.getLogger().info("Registering Events");
        this.getServer().getPluginManager().registerEvents(new EPVListener(), this);

        this.getLogger().info("Registering Commands");
        PluginCommand pluginCommand = this.getCommand("epv");
        if(pluginCommand != null) {
            pluginCommand.setExecutor(new EPVCommand());
        }

    }

    @Override
    public void onDisable() {
        DataManager.saveAll();
    }
}
