package me.vivimage25.experiapersonalvaults.utilities;

import me.vivimage25.experiapersonalvaults.EPVPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class YamlDataStorage implements DataStorage {

    private File getDataFile(Player player) {
        File dataFolder = new File(EPVPlugin.getInstance().getDataFolder(), "data_storage");
        if(!dataFolder.exists()) if(!dataFolder.mkdir()) throw new RuntimeException("Could not create data_storage folder");
        return new File(dataFolder, player.getUniqueId().toString()).getAbsoluteFile();
    }

    @Override
    public boolean save(Player player, ItemStack[] items) {
        YamlConfiguration configuration = new YamlConfiguration();

        for(int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            if(item != null) {
                configuration.set("slot_".concat(String.valueOf(i)), item);
            }
        }

        try {
            configuration.save(this.getDataFile(player));
        } catch (IOException e) {
            EPVPlugin.getInstance().getLogger().warning(ChatColor.RED.toString().concat("[EPV] ERROR: Could not save data file"));
            return false;
        }
        return true;
    }

    @Override
    public ItemStack[] load(Player player) {
        ItemStack[] result;
        YamlConfiguration configuration = new YamlConfiguration();

        try {
            configuration.load(this.getDataFile(player));
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException | InvalidConfigurationException e) {
            EPVPlugin.getInstance().getLogger().warning(ChatColor.RED.toString().concat("[EPV] ERROR: Could not load data file"));
            return null;
        }

        result = new ItemStack[54];

        for(int i = 0; i < 54; i++) {
            Object object = configuration.get("slot_".concat(String.valueOf(i)));
            if(object instanceof ItemStack) {
                result[i] = (ItemStack) object;
            }
        }
        return result;
    }
}
