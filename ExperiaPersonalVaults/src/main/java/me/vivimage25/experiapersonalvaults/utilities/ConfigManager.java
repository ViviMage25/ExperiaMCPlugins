package me.vivimage25.experiapersonalvaults.utilities;

import me.vivimage25.experiapersonalvaults.EPVPlugin;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigManager {

    public static String getStorageType() {
        EPVPlugin plugin = EPVPlugin.getInstance();
        return plugin.getConfig().getString("storage_type");
    }

    public static String getDBAddress() {
        EPVPlugin plugin = EPVPlugin.getInstance();
        ConfigurationSection mariadbConfig = plugin.getConfig().getConfigurationSection("mariadb");
        if(mariadbConfig != null) {
            return mariadbConfig.getString("address");
        }
        return "localhost";
    }

    public static int getDBPort() {
        EPVPlugin plugin = EPVPlugin.getInstance();
        ConfigurationSection mariadbConfig = plugin.getConfig().getConfigurationSection("mariadb");
        if(mariadbConfig != null) {
            return mariadbConfig.getInt("port");
        }
        return 3306;
    }

    public static String getDBDatabase() {
        EPVPlugin plugin = EPVPlugin.getInstance();
        ConfigurationSection mariadbConfig = plugin.getConfig().getConfigurationSection("mariadb");
        if(mariadbConfig != null) {
            return mariadbConfig.getString("database");
        }
        return "minecraft";
    }

    public static String getDBTable() {
        EPVPlugin plugin = EPVPlugin.getInstance();
        ConfigurationSection mariadbConfig = plugin.getConfig().getConfigurationSection("mariadb");
        if(mariadbConfig != null) {
            return mariadbConfig.getString("table");
        }
        return "epv_storage";
    }

    public static String getDBUsername() {
        EPVPlugin plugin = EPVPlugin.getInstance();
        ConfigurationSection mariadbConfig = plugin.getConfig().getConfigurationSection("mariadb");
        if(mariadbConfig != null) {
            return mariadbConfig.getString("username");
        }
        return "username";
    }

    public static String getDBPassword() {
        EPVPlugin plugin = EPVPlugin.getInstance();
        ConfigurationSection mariadbConfig = plugin.getConfig().getConfigurationSection("mariadb");
        if(mariadbConfig != null) {
            return mariadbConfig.getString("password");
        }
        return "password";
    }

    private ConfigManager() {}
}
