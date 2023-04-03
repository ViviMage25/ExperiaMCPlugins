package me.vivimage25.rpgstats.data.storage;

import me.vivimage25.rpgstats.RPGStatsPlugin;
import me.vivimage25.rpgstats.config.RPGStatsConfig;
import me.vivimage25.rpgstats.data.RPGStatsData;
import me.vivimage25.rpgstats.data.RPGStatsDataManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RPGStatsYAMLDataStorage implements RPGStatsDataStorage {
    private final Path storagePath;

    RPGStatsYAMLDataStorage() {
        String dataFolder = RPGStatsPlugin.getInstance().getDataFolder().getAbsolutePath();
        String storageDirectory = RPGStatsConfig.getStorageFolder();
        this.storagePath = Paths.get(dataFolder, storageDirectory);
    }

    @Override
    public boolean save(Player player) {
        try {
            YamlConfiguration configuration = new YamlConfiguration();
            configuration.set("player_uuid", player.getUniqueId().toString());
            configuration.set("player_name", player.getName());

            RPGStatsData playerData = RPGStatsDataManager.getData(player);
            if(playerData == null) throw new RuntimeException("internal player data does NOT exist. Aborting save...");

            ConfigurationSection statsSection = configuration.createSection("stats");
            statsSection.set("strength", playerData.getBaseStrength());
            statsSection.set("dexterity", playerData.getBaseDexterity());
            statsSection.set("vitality", playerData.getBaseVitality());
            statsSection.set("intelligence", playerData.getBaseIntelligence());
            statsSection.set("spirit", playerData.getBaseSpirit());
            statsSection.set("luck", playerData.getBaseLuck());

            File playerFile = new File(this.storagePath.toFile(), player.getUniqueId().toString().concat(".yml"));
            configuration.save(playerFile);

            return true;
        } catch(IOException | RuntimeException exception) {
            RPGStatsPlugin.getInstance().getLogger().warning(exception.getMessage());
            return false;
        }

    }

    @Override
    public boolean load(Player player) {
        try {
            File playerFile = new File(this.storagePath.toFile(), player.getUniqueId().toString().concat(".yml"));
            YamlConfiguration configuration = new YamlConfiguration();
            configuration.load(playerFile);

            String uuid = configuration.getString("player_uuid");
            if(uuid == null) throw new RuntimeException("player data file does NOT contain a player_uuid. Aborting load...");
            if(!player.getUniqueId().toString().equalsIgnoreCase(uuid)) throw new RuntimeException("player data file contains an invalid player_uuid. Aborting load...");

            String name = configuration.getString("player_name", player.getName());
            if(!player.getName().equalsIgnoreCase(name)) Bukkit.broadcast(Component.text("The player formally known as ".concat(name).concat(" is now known as ".concat(player.getName()).concat("."))));

            ConfigurationSection statsSection = configuration.getConfigurationSection("stats");
            if(statsSection == null) throw new RuntimeException("player data file does NOT contain a stats section. Aborting load...");
            int strength = statsSection.getInt("strength");
            int dexterity = statsSection.getInt("dexterity");
            int vitality = statsSection.getInt("vitality");
            int intelligence = statsSection.getInt("intelligence");
            int spirit = statsSection.getInt("spirit");
            int luck = statsSection.getInt("luck");

            RPGStatsData playerData = new RPGStatsData(player, strength, dexterity, vitality, intelligence, spirit, luck);
            RPGStatsDataManager.addData(playerData);

            return true;
        } catch (IOException | InvalidConfigurationException | RuntimeException exception) {
            RPGStatsPlugin.getInstance().getLogger().warning(exception.getMessage());
            return false;
        }
    }
}
