package me.vivimage25.experialifesteal;

import me.vivimage25.experialifesteal.commands.LifePieceCommand;
import me.vivimage25.experialifesteal.commands.LifeSeeCommand;
import me.vivimage25.experialifesteal.commands.LifeModCommand;
import me.vivimage25.experialifesteal.items.LifePieceItem;
import me.vivimage25.experialifesteal.listeners.PlayerListeners;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExperiaLifeSteal extends JavaPlugin {

    private static ExperiaLifeSteal plugin;

    public static ExperiaLifeSteal getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        saveDefaultConfig();
        PlayerListeners.max_limit_amount = getConfig().getDouble("max_limit");
        PlayerListeners.regain_modifier = getConfig().getDouble("regain_mod");
        PlayerListeners.ban_on_last_life = getConfig().getBoolean("ban_on_last");

        getLogger().info("Registering Events");
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

        getLogger().info("Adding Recipes");
        getServer().addRecipe(LifePieceItem.getRecipe());

        getLogger().info("Registering Commands");
        PluginCommand cmd;
        if((cmd = getCommand("lifepiece")) != null) {
            LifePieceCommand lifePieceCommand = new LifePieceCommand();
            cmd.setExecutor(lifePieceCommand);
            cmd.setTabCompleter(lifePieceCommand);
        }
        if((cmd = getCommand("lifesee")) != null) {
            LifeSeeCommand lifeSeeCommand = new LifeSeeCommand();
            cmd.setExecutor(lifeSeeCommand);
            cmd.setTabCompleter(lifeSeeCommand);
        }
        if((cmd = getCommand("lifemod")) != null) {
            LifeModCommand lifeModCommand = new LifeModCommand();
            cmd.setExecutor(lifeModCommand);
            cmd.setTabCompleter(lifeModCommand);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
