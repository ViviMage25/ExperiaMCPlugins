package me.vivimage25.experiapersonalvaults.utilities;

import me.vivimage25.experiapersonalvaults.ExperiaPersonalVaults;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class YamlDataStorage implements DataStorage {

    private File getDataFile(Player player) {
        File dataFolder = new File(ExperiaPersonalVaults.getInstance().getDataFolder(), "data_storage");
        return new File(dataFolder, player.getUniqueId().toString()).getAbsoluteFile();
    }

    @Override
    public void save(Player player, ItemStack[] items) {
        YamlConfiguration configuration = new YamlConfiguration();
        configuration.set("inventory_size", items.length);

        for(int i = 0; i < items.length; i++) {
            ConfigurationSection slotSection = configuration.createSection("slot_".concat(String.valueOf(i)));
            ItemStack item = items[i];
            if(item != null) {
                slotSection.set("material", item.getType().name());
                slotSection.set("amount", item.getAmount());
                if(item.hasItemMeta()) {
                    ConfigurationSection itemMetaSection = slotSection.createSection("meta");
                    ItemMeta itemMeta = item.getItemMeta();

                    if(itemMeta.hasDisplayName()) {
                        Component displayName = itemMeta.displayName();
                        if(displayName != null) itemMetaSection.set("display_name", PlainTextComponentSerializer.plainText().serialize(displayName));
                    }

                    if(itemMeta.hasCustomModelData()) itemMetaSection.set("custom_model_data", itemMeta.getCustomModelData());

                    itemMetaSection.set("unbreakable", itemMeta.isUnbreakable());

                    if(itemMeta.hasLore()) {
                        List<Component> loreComponentList = itemMeta.lore();
                        if(loreComponentList != null) {
                            List<String> loreList = new ArrayList<>();
                            for (Component loreComponent : loreComponentList) {
                                String loreLine = PlainTextComponentSerializer.plainText().serialize(loreComponent);
                                loreList.add(loreLine);
                            }
                            itemMetaSection.set("lore", loreList);
                        }
                    }

                    if(itemMeta.hasEnchants()) {
                        ConfigurationSection itemEnchantmentSection = itemMetaSection.createSection("enchants");
                        Map<Enchantment, Integer> enchantmentMap = itemMeta.getEnchants();
                        for(Enchantment enchantment : enchantmentMap.keySet()) {
                            String enchantmentName = enchantment.getKey().value();
                            int enchantmentLevel = enchantmentMap.get(enchantment);
                            itemEnchantmentSection.set(enchantmentName, enchantmentLevel);
                        }
                    }

                    Set<ItemFlag> itemFlags = itemMeta.getItemFlags();
                    if(!itemFlags.isEmpty()) {
                        List<String> itemFlagNames = new ArrayList<>();
                        for(ItemFlag flag : itemFlags) {
                            itemFlagNames.add(flag.name());
                        }
                        itemMetaSection.set("flags", itemFlagNames);
                    }
                }
            }
        }

        try {
            configuration.save(this.getDataFile(player));
        } catch (IOException e) {
            player.sendMessage(ChatColor.RED.toString().concat("[EPV] ERROR: Could not save data file"));
        }
    }

    @Override
    public ItemStack[] load(Player player) {
        ItemStack[] result = null;
        YamlConfiguration configuration = new YamlConfiguration();

        try {
            // TODO: Add catch clause for FileNotFoundException and save new file.
            configuration.load(this.getDataFile(player));
        } catch (FileNotFoundException e) {
            try {
                configuration.save(this.getDataFile(player));
            } catch (IOException ex) {
                player.sendMessage(ChatColor.RED.toString().concat("[EPV] ERROR: Could not save data file"));
                return null;
            }
        } catch (IOException | InvalidConfigurationException e) {
            player.sendMessage(ChatColor.RED.toString().concat("[EPV] ERROR: Could not load data file"));
            return null;
        }

        int inventorySize = configuration.getInt("inventory_size");
        result = new ItemStack[inventorySize];

        for(int i = 0; i < inventorySize; i++) {

            ConfigurationSection slotSection = configuration.getConfigurationSection("slot_".concat(String.valueOf(i)));
            if(slotSection != null) {
                String itemMaterialName = slotSection.getString("material");
                int itemAmount = slotSection.getInt("amount");

                if(itemMaterialName != null) {
                    Material itemMaterial = Material.getMaterial(itemMaterialName);

                    if(itemMaterial != null) {
                        ItemStack item = new ItemStack(itemMaterial, itemAmount);

                        ConfigurationSection itemMetaSection = slotSection.getConfigurationSection("meta");
                        if(itemMetaSection != null) {
                            ItemMeta itemMeta = item.getItemMeta();

                            String itemDisplayName = itemMetaSection.getString("display_name");
                            if(itemDisplayName != null) itemMeta.displayName(Component.text(itemDisplayName));

                            int itemCustomModelData = itemMetaSection.getInt("custom_model_data");
                            if(itemCustomModelData != 0) itemMeta.setCustomModelData(itemCustomModelData);

                            boolean itemUnbreakable = itemMetaSection.getBoolean("unbreakable");
                            itemMeta.setUnbreakable(itemUnbreakable);

                            List<String> loreList = itemMetaSection.getStringList("lore");
                            if(!loreList.isEmpty()) {
                                List<Component> loreComponentList = new ArrayList<>();
                                for(String loreLine : loreList) {
                                    loreComponentList.add(Component.text(loreLine));
                                }
                                itemMeta.lore(loreComponentList);
                            }

                            ConfigurationSection itemEnchantmentSection = itemMetaSection.getConfigurationSection("enchants");
                            if(itemEnchantmentSection != null) {
                                for(String enchantName : itemMetaSection.getKeys(false)) {
                                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));
                                    int enchantmentLevel = itemEnchantmentSection.getInt(enchantName);
                                    if(enchantment != null) itemMeta.addEnchant(enchantment, enchantmentLevel, true);
                                }
                            }

                            List<String> flagList = itemMetaSection.getStringList("flags");
                            if(!flagList.isEmpty()) {
                                List<ItemFlag> itemFlagList = new ArrayList<ItemFlag>();
                                for(String flagName : flagList) {
                                    itemFlagList.add(ItemFlag.valueOf(flagName));
                                }
                                itemMeta.addItemFlags(itemFlagList.toArray(new ItemFlag[0]));
                            }

                            //ConfigurationSection itemAttributeSection = itemMetaSection.getConfigurationSection("attributes");
                            //if(itemAttributeSection != null) {
                            //}

                            item.setItemMeta(itemMeta);
                        }
                        result[i] = item;

                    }

                }
            }

        }
        return result;
    }
}
