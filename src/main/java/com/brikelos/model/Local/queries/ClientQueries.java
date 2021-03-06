package com.brikelos.model.Local.queries;

import com.brikelos.model.Local.SQLiteConnection;
import com.brikelos.model.Local.models.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientQueries extends SQLiteConnection {

    public static boolean addClient(Client client) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "INSERT INTO Clients (name, phone, moneySpent, deleted) VALUES (" +
                            "'" + client.getName()  + "', " +
                            "'" + client.getPhone() + "', " +
                            client.getMoneySpent() +
                            ", 0);"
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
                        res.getString("phone"),
                        res.getDouble("moneySpent"),
                        res.getBoolean("deleted")
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
     * Returns the client id by the name.
     * If it doesn't exits it returns -1.
     * @param name
     * @return
     */
    public static int getIdByName(String name) {
        java.sql.Connection connection = connect();
        try {
            ResultSet res = connection.createStatement().executeQuery(
                    "SELECT id FROM Clients WHERE (name='" + name + "');"
            );
            if(res.next()) {
                return res.getInt("id");
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
        return -1;
    }

    public static Client getClientByName(String name) {
        java.sql.Connection connection = connect();
        try {
            ResultSet res = connection.createStatement().executeQuery(
                    "SELECT * FROM Clients WHERE (name='" + name + "');"
            );
            if(res.next()) {
                return new Client(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("phone"),
                        res.getDouble("moneySpent"),
                        res.getBoolean("deleted")
                );
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
        return null;
    }

    public static double getTotalSpent(Client client) {
        java.sql.Connection connection = connect();
        try {
            ResultSet res = connection.createStatement().executeQuery(
                    "SELECT moneySpent FROM Clients WHERE (name='" + client.getName() + "');"
            );
            if(res.next()) {
                return res.getDouble("moneySpent");
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
        return -1;
    }

    public static void setMoneySpent(int clientID, double price) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "UPDATE Clients SET moneySpent=" + price + " WHERE id=" + clientID + ";"
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


    public static Client getClientById(int id) {
        java.sql.Connection connection = connect();
        try {
            ResultSet res = connection.createStatement().executeQuery(
                    "SELECT * FROM Clients WHERE (id='" + id + "');"
            );
            if(res.next()) {
                return new Client(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("phone"),
                        res.getDouble("moneySpent"),
                        res.getBoolean("deleted")
                );
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
        return null;
    }

    public static void modifyClientInfo(int clientID, Client modifiedClient) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "UPDATE Clients SET name='" + modifiedClient.getName() + "' WHERE id=" + clientID + ";"
            );
            connection.createStatement().execute(
                    "UPDATE Clients SET phone='" + modifiedClient.getPhone() + "' WHERE id=" + clientID + ";"
            );
            connection.createStatement().execute(
                    "UPDATE Clients SET moneySpent=" + modifiedClient.getMoneySpent() + " WHERE id=" + clientID + ";"
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

    public static void deleteClient(Client client) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "UPDATE Clients SET deleted=true WHERE id=" + client.getId() + ";"
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

    public static long getActiveClients() {
        java.sql.Connection connection = connect();
        long count = 0;
        try {
            ResultSet res = connection.createStatement().executeQuery("SELECT * FROM Clients WHERE deleted=false;");
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
