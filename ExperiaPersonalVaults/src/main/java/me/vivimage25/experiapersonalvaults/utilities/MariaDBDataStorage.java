package me.vivimage25.experiapersonalvaults.utilities;

import me.vivimage25.experiapersonalvaults.EPVPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.Arrays;

public class MariaDBDataStorage implements DataStorage {

    private final String dbURL;
    private final String dbUsername;
    private final String dbPassword;
    private final String dbTable;

    public MariaDBDataStorage() {
        String dbAddress = ConfigManager.getDBAddress();
        int dbPort = ConfigManager.getDBPort();
        String dbDatabase = ConfigManager.getDBDatabase();
        this.dbURL = "jdbc:mariadb://".concat(dbAddress).concat(":").concat(String.valueOf(dbPort)).concat("/").concat(dbDatabase);
        this.dbUsername = ConfigManager.getDBUsername();
        this.dbPassword = ConfigManager.getDBPassword();
        this.dbTable = ConfigManager.getDBTable();
        this.createStorageTable();
    }

    private void createStorageTable() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(this.dbURL, this.dbUsername, this.dbPassword);
            PreparedStatement dbPreparedStatement = dbConnection.prepareStatement("CREATE TABLE IF NOT EXISTS `".concat(this.dbTable).concat("` (uuid char(36) NOT NULL UNIQUE, name char(16) NOT NULL, items LONGTEXT NOT NULL, update BOOLEAN NOT NULL, PRIMARY KEY(uuid))"));
            dbPreparedStatement.execute();
            dbPreparedStatement.close();
            dbConnection.close();
        } catch (SQLException e) {
            EPVPlugin.getInstance().getLogger().warning(ChatColor.RED.toString().concat("[EPV] ERROR: Could not create table"));
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasData(Player player) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(this.dbURL, this.dbUsername, this.dbPassword);
            PreparedStatement dbPreparedStatement = dbConnection.prepareStatement("SELECT `name` FROM `".concat(this.dbTable).concat("` WHERE `uuid` = ? LIMIT 1"));
            dbPreparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet result = dbPreparedStatement.executeQuery();
            if(result.next()) {
                System.out.println(result.getString("name"));
                result.close();
                dbPreparedStatement.close();
                dbConnection.close();
                return true;
            }
            result.close();
            dbPreparedStatement.close();
            dbConnection.close();
            return false;
        } catch (SQLException e) {
            EPVPlugin.getInstance().getLogger().warning(ChatColor.RED.toString().concat("[EPV] ERROR: Could not save data"));
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void createData(Player player) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(this.dbURL, this.dbUsername, this.dbPassword);
            PreparedStatement dbPreparedStatement = dbConnection.prepareStatement("INSERT INTO `".concat(this.dbTable).concat("`(`uuid`, `name`, `items`) VALUES(?, ?, ?)"));
            dbPreparedStatement.setString(1, player.getUniqueId().toString());
            dbPreparedStatement.setString(2, player.getName());
            dbPreparedStatement.setString(3, EncodeItemStack.toBase64(new ItemStack[54]));
            dbPreparedStatement.executeUpdate();
            dbPreparedStatement.close();
            dbConnection.close();
        } catch (SQLException e) {
            EPVPlugin.getInstance().getLogger().warning(ChatColor.RED.toString().concat("[EPV] ERROR: Could not save data"));
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Player player, ItemStack[] items) {
        if(!this.hasData(player)) this.createData(player);
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(this.dbURL, this.dbUsername, this.dbPassword);
            PreparedStatement dbPreparedStatement = dbConnection.prepareStatement("UPDATE `".concat(this.dbTable).concat("` SET `items` = ? WHERE `uuid` = ?"));
            dbPreparedStatement.setString(1, EncodeItemStack.toBase64(items));
            dbPreparedStatement.setString(2, player.getUniqueId().toString());
            dbPreparedStatement.executeUpdate();
            dbPreparedStatement.close();
            dbConnection.close();
        } catch (SQLException e) {
            EPVPlugin.getInstance().getLogger().warning(ChatColor.RED.toString().concat("[EPV] ERROR: Could not save data"));
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public ItemStack[] load(Player player) {
        if(!this.hasData(player)) this.createData(player);
        ItemStack[] items = new ItemStack[54];
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(this.dbURL, this.dbUsername, this.dbPassword);
            PreparedStatement dbPreparedStatement = dbConnection.prepareStatement("SELECT `items` FROM `".concat(this.dbTable).concat("` WHERE `uuid` = ? LIMIT 1"));
            dbPreparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet result = dbPreparedStatement.executeQuery();
            while(result.next()) {
                String encodedItems = result.getString("items");
                System.out.println(encodedItems);
                if(encodedItems != null) items = EncodeItemStack.fromBase64(encodedItems);
                System.out.println(Arrays.toString(items));
            }
            result.close();
            dbPreparedStatement.close();
            dbConnection.close();
        } catch (SQLException e) {
            EPVPlugin.getInstance().getLogger().warning(ChatColor.RED.toString().concat("[EPV] ERROR: Could not load data"));
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

}
