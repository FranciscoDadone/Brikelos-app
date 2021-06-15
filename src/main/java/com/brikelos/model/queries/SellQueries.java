package com.brikelos.model.queries;

import com.brikelos.model.Connection;
import com.brikelos.model.models.Client;
import com.brikelos.model.models.Sell;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SellQueries extends Connection {

    /**
     * Adds a new purchase to the database.
     * @param sell
     * @return
     */
    public static boolean addSell(Sell sell) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "INSERT INTO Sells (title, date, description, price, buyerID) VALUES (" +
                    "'" + sell.getTitle()       + "'," +
                    "'" + sell.getDate()        + "'," +
                    "'" + sell.getDescription() + "'," +
                          sell.getPrice()       + ","  +
                          sell.getBuyerID()     + ");"
            );
            ClientQueries.setMoneySpent(sell.getBuyerID(), sell.getPrice());
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

    /**
     * Returns an ArrayList of Sells of a specific client.
     * @param client
     * @return
     */
    public static ArrayList<Sell> getAllClientSells(Client client) {
        java.sql.Connection connection = connect();
        ArrayList<Sell> purchases = new ArrayList<>();
        try {
            ResultSet res = connection.createStatement().executeQuery(
                    "SELECT * FROM Sells WHERE (buyerID=" + client.getId() + ")"
            );
            while(res.next()) {
                purchases.add(new Sell(
                        client.getId(),
                        res.getString("date"),
                        res.getString("title"),
                        res.getString("description"),
                        res.getDouble("price")
                ));
            }
            return purchases;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
