package com.brikelos.database.databaseHandler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

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
                System.out.println("The driver name is " + meta.getDriverName());
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
    private Connection connect() {
        Connection con = null;
        try  {
            con = DriverManager.getConnection("jdbc:sqlite:src/main/java/com/brikelos/database/db/sqlite.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }


}
