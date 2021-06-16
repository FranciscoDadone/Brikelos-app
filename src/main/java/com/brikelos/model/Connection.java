package com.brikelos.model;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Connection {
    /**
     * Connects to the database.
     *
     * If the database doesn't exists, it creates it.
     *
     * Creation of tables 'Clients', 'Sells' if it doesn't exits.
     *
     * @return Connection
     */
    public static java.sql.Connection connect() {
        java.sql.Connection con = null;
        try {
            File theDir = new File("database");
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            con = DriverManager.getConnection("jdbc:sqlite:" + FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Brikelos/database/sqlite.db");
            ResultSet res = con.createStatement().executeQuery("SELECT name FROM sqlite_master WHERE type='table';");
            ArrayList<String> tables = new ArrayList<>();
            while(res.next()) {
                tables.add(res.getString(1));
            }
            /**
             * Creation of table Clients if it doesn't exists.
             */
            if(!tables.contains("Clients")) {
                Statement statement = con.createStatement();
                statement.execute("CREATE TABLE Clients (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name VARCHAR(255) NOT NULL," +
                                "dni INTEGER," +
                                "phone VARCHAR(50)," +
                                "email VARCHAR(100)," +
                                "moneySpent DOUBLE" +
                                ");"
                );
            }

            /**
             * Creation of table Sells if it doesn't exists.
             */
            if(!tables.contains("Sells")) {
                Statement statement = con.createStatement();
                statement.execute("CREATE TABLE Sells (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "title VARCHAR(255)," +
                        "date VARCHAR(255)," +
                        "description VARCHAR(255)," +
                        "price DOUBLE," +
                        "buyerID INTEGER" +
                        ");"
                );
            }

            /**
             * Creation of table Config if it doesn't exists.
             */
            if(!tables.contains("Config")) {
                Statement statement = con.createStatement();
                statement.execute("CREATE TABLE Config (" +
                        "id INTEGER, " +
                        "moneyAlert DOUBLE" +
                        ");"
                );
                /**
                 * Initializes the table with default values.
                 */
                statement.execute(
                        "INSERT INTO Config (id, moneyAlert) VALUES (" +
                                "1, " +     // id
                                "10000.0" + // default moneyAlertTrigger
                                ");"
                );

            }
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }
}
