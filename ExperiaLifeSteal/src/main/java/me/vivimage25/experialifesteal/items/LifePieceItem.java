package me.vivimage25.experialifesteal.items;

import me.vivimage25.experialifesteal.ExperiaLifeSteal;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class LifePieceItem {
    public static final Material ITEM_TYPE = Material.APPLE;
    public static final int ITEM_MODEL_NUMBER = 2048;
    public static final String ITEM_NAME = "Life Piece";
    public static final Component[] loreList = {Component.text("Increase your life by 1")};

    private LifePieceItem() {}

    public static ItemStack create() {
        return create(1);
    }

    public static ItemStack create(int amount) {
        ItemStack item = new ItemStack(ITEM_TYPE, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(ITEM_MODEL_NUMBER);
        meta.displayName(Component.text(ITEM_NAME));
        meta.lore(Arrays.asList(loreList));
        meta.addEnchant(Enchantment.MENDING, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    public static ShapedRecipe getRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(ExperiaLifeSteal.getPlugin(), "life_piece");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, create());
        ItemStack lifePotion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) lifePotion.getItemMeta();
        potionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        lifePotion.setItemMeta(potionMeta);

        recipe.shape("PPP", "PAP", "PPP");
        recipe.setIngredient('A', Material.APPLE);
        recipe.setIngredient('P', lifePotion);
        return recipe;
    }

}
