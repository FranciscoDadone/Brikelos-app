package com.brikelos.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientQueries extends Connection {

    public static boolean addClient(Client client) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "INSERT INTO Clients (name, dni, phone, email, moneySpent) VALUES (" +
                            "'" + client.getName() + "', "
                            + client.getDni()            + ", "  +
                            "'" + client.getPhone()       + "', " +
                            "'" + client.getEmail()          + "', " +
                            "0" +
                            ");"
            );
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * Returns an ArrayList<Client> with all the clients.
     * @return ArrayList<Client>
     */
    public static ArrayList<Client> getAllClients() {
        ArrayList<Client> clients = new ArrayList();
        java.sql.Connection connection = connect();
        try {
            ResultSet res = connection.createStatement().executeQuery("SELECT * FROM Clients;");
            while(res.next()) {
                clients.add(new Client(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getInt("dni"),
                        res.getString("email"),
                        res.getString("phone"),
                        res.getDouble("moneySpent")
                ));
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
        return clients;
    }

    /**
     * Checks if the same name already exists in the database.
     */
    public static boolean sameName(Client client) {
        java.sql.Connection connection = connect();
        try {
            ResultSet res = connection.createStatement().executeQuery(
                    "SELECT * FROM Clients WHERE upper(name)='" + client.getName().toUpperCase() + "';"
            );
            if(res.next()) {
                return true;
            }
            return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the same DNI already exists in the database.
     */
    public static boolean sameDni(Client client) {
        java.sql.Connection connection = connect();
        try {
            ResultSet res = connection.createStatement().executeQuery(
                    "SELECT * FROM Clients WHERE (dni=" + client.getDni() + ");"
            );
            if(res.next()) {
                return true;
            }
            return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


}
