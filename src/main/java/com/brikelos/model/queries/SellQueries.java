package com.brikelos.model.queries;

import com.brikelos.model.Connection;
import com.brikelos.model.models.Client;
import com.brikelos.model.models.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SellQueries extends Connection {

    /**
     * Adds a new purchase to the database.
     * @param purchase
     * @return
     */
    public static boolean addSell(Purchase purchase) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "INSERT INTO Sells (title, date, description, price, buyerID) VALUES (" +
                    "'" + purchase.getTitle()       + "'," +
                    "'" + purchase.getDate()        + "'," +
                    "'" + purchase.getDescription() + "'," +
                          purchase.getPrice()       + ","  +
                          purchase.getBuyerID()     + ");"
            );
            ClientQueries.setMoneySpent(purchase.getBuyerID(), purchase.getPrice());
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
    public static ArrayList<Purchase> getAllClientSells(Client client) {
        java.sql.Connection connection = connect();
        ArrayList<Purchase> purchases = new ArrayList<>();
        try {
            ResultSet res = connection.createStatement().executeQuery(
                    "SELECT * FROM Sells WHERE (buyerID=" + client.getId() + ")"
            );
            while(res.next()) {
                purchases.add(new Purchase(
                        res.getInt("id"),
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

    public static void modifySellInfo(int purchaseID, Purchase modifiedPurchase) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "UPDATE Sells SET title='" + modifiedPurchase.getTitle() + "' WHERE id=" + purchaseID + ";"
            );
            connection.createStatement().execute(
                    "UPDATE Sells SET description='" + modifiedPurchase.getDescription() + "' WHERE id=" + purchaseID + ";"
            );
            connection.createStatement().execute(
                    "UPDATE Sells SET price=" + modifiedPurchase.getPrice() + " WHERE id=" + purchaseID + ";"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deletePurchase(Purchase purchase) {

        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "DELETE FROM Sells WHERE id=" + purchase.getId() + ";"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
