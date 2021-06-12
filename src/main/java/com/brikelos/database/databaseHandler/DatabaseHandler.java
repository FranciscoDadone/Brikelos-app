package com.brikelos.database.databaseHandler;

import com.brikelos.templates.Client;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    /**
     * Creates the database if it already doesn't exists.
     */
    public void createNewDatabase() {
        Connection conn = null;
        try {
            conn = this.connect();
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Driver: " + meta.getDriverName());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Connect to the database.
     * @return Connection
     */
    public Connection connect() {
        Connection con = null;
        boolean newDatabase = false;
        try {
            File theDir = new File("database");
            if (!theDir.exists()){
                theDir.mkdirs();
                newDatabase = true;
            }
            con = DriverManager.getConnection("jdbc:sqlite:database/sqlite.db");
            if(newDatabase) {
                Statement statement = con.createStatement();
                statement.execute("CREATE TABLE Clients (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name VARCHAR(255) NOT NULL," +
                                "dni INTEGER," +
                                "phone VARCHAR(50)," +
                                "email VARCHAR(100)," +
                                "moneySpent DOUBLE" +
                                ");");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    /**
     * Returns an arraylist with all the clients.
     * @return
     */
    public static ArrayList<Client> getAllClients() {
        ArrayList<Client> clients = new ArrayList();
        Connection connection = new DatabaseHandler().connect();
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
        }
        return clients;
    }

}
