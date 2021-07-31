package com.brikelos.model.Local.queries;

import com.brikelos.model.Local.SQLiteConnection;
import com.brikelos.model.Local.models.Client;
import com.brikelos.model.Local.models.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SellQueries extends SQLiteConnection {

    /**
     * Adds a new purchase to the database.
     * @param purchase
     * @return
     */
    public static int addSell(Purchase purchase) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "INSERT INTO Sells (title, date, description, price, buyerID, deleted) VALUES (" +
                    "'" + purchase.getTitle()       + "'," +
                    "'" + purchase.getDate()        + "'," +
                    "'" + purchase.getDescription() + "'," +
                          purchase.getPrice()       + ","  +
                          purchase.getBuyerID()     + ", 0);"
            );
//            ClientQueries.setMoneySpent(purchase.getBuyerID(), purchase.getPrice());

            ResultSet res = connection.createStatement().executeQuery("SELECT * FROM Sells WHERE (title='" + purchase.getTitle() + "' AND " +
                    "date='" + purchase.getDate() + "' AND " +
                    "description='" + purchase.getDescription() + "' AND " +
                    "price=" + purchase.getPrice() + ");");
            return res.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
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
                        res.getDouble("price"),
                        res.getBoolean("deleted")
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
                    "UPDATE Sells SET deleted=true WHERE id=" + purchase.getId() + ";"
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

    public static ArrayList<Purchase> getAllSells() {

        java.sql.Connection connection = connect();
        ArrayList<Purchase> purchases = new ArrayList<>();
        try {
            ResultSet res = connection.createStatement().executeQuery(
                    "SELECT * FROM Sells;"
            );
            while(res.next()) {
                purchases.add(new Purchase(
                        res.getInt("id"),
                        res.getInt("buyerID"),
                        res.getString("date"),
                        res.getString("title"),
                        res.getString("description"),
                        res.getDouble("price"),
                        res.getBoolean("deleted")
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

    public static long getActiveSells() {
        java.sql.Connection connection = connect();
        long count = 0;
        try {
            ResultSet res = connection.createStatement().executeQuery("SELECT * FROM Sells WHERE deleted=false;");
            while(res.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

}
