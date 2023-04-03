package me.vivimage25.rpgstats.config;

import me.vivimage25.rpgstats.RPGStatsPlugin;
import org.bukkit.configuration.ConfigurationSection;

public class RPGStatsConfig {
    private static String dataStorage;
    private static String storageFolder;

    private static String dbURL;
    private static String dbTablePrefix;
    private static String dbUsername;
    private static String dbPassword;

    public static String getDataStorage() {
        if(dataStorage == null) {
            ConfigurationSection dataSection = RPGStatsPlugin.getInstance().getConfig().getConfigurationSection("data");
            if (dataSection != null) dataStorage = dataSection.getString("storage");
        }
        return dataStorage != null ? dataStorage : "yaml";
    }

    public static String getStorageFolder() {
        if(storageFolder == null) {
            ConfigurationSection dataSection = RPGStatsPlugin.getInstance().getConfig().getConfigurationSection("data");
            if (dataSection != null) storageFolder = dataSection.getString("directory");
        }
        return storageFolder != null ? storageFolder : "storage";
    }

    public static String getDbURL() {
        if(dbURL == null) {
            ConfigurationSection dataSection = RPGStatsPlugin.getInstance().getConfig().getConfigurationSection("data");
            if(dataSection != null) {
                ConfigurationSection dbSection = dataSection.getConfigurationSection("database");
                if (dbSection != null) dbURL = dbSection.getString("url");
            }
        }
        return dbURL != null ? dbURL : "jdbc:mariadb://localhost:3306/minecraft";
    }

    public static String getDbTablePrefix() {
        if(dbTablePrefix == null) {
            ConfigurationSection dataSection = RPGStatsPlugin.getInstance().getConfig().getConfigurationSection("data");
            if(dataSection != null) {
                ConfigurationSection dbSection = dataSection.getConfigurationSection("database");
                if (dbSection != null) dbTablePrefix = dbSection.getString("table_prefix");
            }
        }
        return dbTablePrefix != null ? dbTablePrefix : "rpgstats_";
    }

    public static String getDbUsername() {
        if(dbUsername == null) {
            ConfigurationSection dataSection = RPGStatsPlugin.getInstance().getConfig().getConfigurationSection("data");
            if(dataSection != null) {
                ConfigurationSection dbSection = dataSection.getConfigurationSection("database");
                if (dbSection != null) dbUsername = dbSection.getString("username");
            }
        }
        return dbUsername != null ? dbUsername : "minecraftuser";
    }

    public static String getDbPassword() {
        if(dbPassword == null) {
            ConfigurationSection dataSection = RPGStatsPlugin.getInstance().getConfig().getConfigurationSection("data");
            if(dataSection != null) {
                ConfigurationSection dbSection = dataSection.getConfigurationSection("database");
                if (dbSection != null) dbPassword = dbSection.getString("password");
            }
        }
        return dbPassword != null ? dbPassword : "";
    }

}
