package me.vivimage25.experiapersonalvaults.utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;

public class MariaDBDataStorage implements DataStorage {

    private final String dbURL;
    private final String dbUsername;
    private final String dbPassword;

    public MariaDBDataStorage(String dbAddress, int dbPort, String dbDatabase, String dbUsername, String dbPassword) {
        this.dbURL = "jdbc:mariadb://".concat(dbAddress).concat(":").concat(String.valueOf(dbPort)).concat("/").concat(dbDatabase);
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    @Override
    public void save(Player player, ItemStack[] items) {
        try {
            Connection dbConnection = DriverManager.getConnection(this.dbURL, this.dbUsername, this.dbPassword);
            Statement dbStatement = dbConnection.createStatement();

            // TODO: Save data to MariaDB using ResultSet's

            dbStatement.close();
            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemStack[] load(Player player) {
        ItemStack[] result = null;
        try {
            Connection dbConnection = DriverManager.getConnection(this.dbURL, this.dbUsername, this.dbPassword);
            Statement dbStatement = dbConnection.createStatement();

            // TODO: Load data from MariaDB using ResultSet's

            dbStatement.close();
            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
