package com.brikelos.model.Local.queries;

import com.brikelos.model.Local.SQLiteConnection;
import com.brikelos.model.Local.models.Config;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigQueries extends SQLiteConnection {

    /**
     * Sets the money when the alert will trigger.
     * @param money
     */
    public static void setMoneyAlert(double money) {
        java.sql.Connection connection = connect();
        try {
            connection.createStatement().execute(
                    "UPDATE Config SET moneyAlert=" + money + " WHERE id=1;"
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

    /**
     * Sets the moneyAlert parameter.
     * @return
     */
    public static Config getConfig() {
        java.sql.Connection connection = connect();
        try {
            ResultSet res = connection.createStatement().executeQuery(
                    "SELECT * FROM Config WHERE (id=1);"
            );
            if(res.next()) {
                return new Config(res.getDouble("moneyAlert"));
            }
            return null;
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
