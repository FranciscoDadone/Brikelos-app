package com.brikelos.model;

import java.sql.SQLException;

public class SellQueries extends Connection {

    public static boolean addSell(Sell sell) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute("INSERT INTO Sells (title, date, description, price, buyerID) VALUES (" +
                    "'" + sell.getTitle()       + "'," +
                    "'" + sell.getDate()        + "'," +
                    "'" + sell.getDescription() + "'," +
                          sell.getPrice()       + "," +
                          sell.getBuyerID()     + ");");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}
