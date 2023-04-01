package me.vivimage25.experiapersonalvaults.utilities;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EncodeItemStack {

    public static String toBase64(ItemStack[] items) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BukkitObjectOutputStream boos = new BukkitObjectOutputStream(baos);
            for(int i = 0; i < 54; i++) {
                if(items[i] != null) boos.writeObject(items[i]); else boos.writeObject("null");
            }
            boos.close();
            return Base64Coder.encodeLines(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemStack[] fromBase64(String data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream bois = new BukkitObjectInputStream(bais);
            ItemStack[] items = new ItemStack[54];
            for(int i = 0; i < 54; i++) {
                Object object = bois.readObject();
                if(object instanceof ItemStack) {
                    items[i] = (ItemStack) object;
                }
            }
            bois.close();
            return items;
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
