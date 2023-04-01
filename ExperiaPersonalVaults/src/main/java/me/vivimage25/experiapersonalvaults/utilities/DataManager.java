package me.vivimage25.experiapersonalvaults.utilities;

import me.vivimage25.experiapersonalvaults.EPVPlugin;
import me.vivimage25.experiapersonalvaults.inventories.InventoryManager;
import me.vivimage25.experiapersonalvaults.inventories.VaultInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DataManager {
    private static DataStorage DATA;

    public static void setData(DataStorage data) {
        if(DATA == null) DATA = data;
    }

    public static DataStorage getData() {
        if(DATA == null) {
            EPVPlugin plugin = EPVPlugin.getInstance();
            String storageType = ConfigManager.getStorageType();
            if(storageType != null) {
                switch(storageType.toLowerCase()) {
                    case "yaml":
                    case "yml":
                        DataManager.setData(new YamlDataStorage());
                        break;
                    case "mariadb":
                        DataManager.setData(new MariaDBDataStorage());
                        break;
                    default:
                        plugin.getLogger().severe("Unknown value in config storage_type. Using YAML");
                        DataManager.setData(new YamlDataStorage());
                }
            } else {
                plugin.getLogger().severe("Could not load config storage_type. Using YAML");
                DataManager.setData(new YamlDataStorage());
            }
        }
        return DATA;
    }

    public static boolean save(Player player, ItemStack[] items) {
        return DataManager.getData().save(player, items);
    }

    public static ItemStack[] load(Player player) {
        return DataManager.getData().load(player);
    }

    public static void saveAll() {
        for(VaultInventory vaultInventory : InventoryManager.getVaultInventoryMap().values()) {
            ItemStack[] contents = vaultInventory.getItems();
            save(vaultInventory.getOwner(), contents);
        }
    }

    private DataManager() {}
}
