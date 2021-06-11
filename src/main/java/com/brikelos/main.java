package com.brikelos;

import com.brikelos.database.databaseHandler.DatabaseHandler;
import com.brikelos.gui.GUIHandler;

public class main {

    public static void main(String args[]) {
        GUIHandler.main();
        new DatabaseHandler().createNewDatabase();
    }

}
