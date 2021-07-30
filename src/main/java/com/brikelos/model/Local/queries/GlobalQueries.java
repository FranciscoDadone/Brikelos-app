package com.brikelos.model.Local.queries;

import com.brikelos.model.Local.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GlobalQueries {

    public static void checkTableUpdateClients() {

        java.sql.Connection con = SQLiteConnection.connect();

        Statement statement = null;
        try {
            statement = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet a = null;
        try {
            a = statement.executeQuery("SELECT COUNT(*) AS CNTREC FROM pragma_table_info('Clients') WHERE name='deleted'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if(a.getInt(1) == 0) {
                statement.execute("ALTER TABLE Clients ADD deleted INTEGER;");
                Statement finalStatement = statement;
                ClientQueries.getAllClients().forEach((client -> {
                    try {
                        finalStatement.execute("UPDATE Clients SET deleted=false WHERE id=" + client.getId() + ";");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void checkTableUpdateSells() {

        java.sql.Connection con = SQLiteConnection.connect();

        Statement statement = null;
        try {
            statement = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ResultSet a = null;
        try {
            a = statement.executeQuery("SELECT COUNT(*) AS CNTREC FROM pragma_table_info('Sells') WHERE name='deleted'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if(a.getInt(1) == 0) {
                statement.execute("ALTER TABLE Sells ADD deleted INTEGER;");
                Statement finalStatement = statement;
                ClientQueries.getAllClients().forEach((client) -> {
                    SellQueries.getAllClientSells(client).forEach((sell -> {
                        try {
                            finalStatement.execute("UPDATE Sells SET deleted=false WHERE id=" + sell.getId() + ";");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }));
                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
