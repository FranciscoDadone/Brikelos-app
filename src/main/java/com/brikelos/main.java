package com.brikelos;

import com.brikelos.database.databaseHandler.DatabaseHandler;
import com.brikelos.gui.Handler;

public class main {

    public static void main(String args[]) {
        Handler.main();
        new DatabaseHandler().createNewDatabase();
    }

}
