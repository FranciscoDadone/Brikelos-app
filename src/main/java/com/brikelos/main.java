package com.brikelos;

import com.brikelos.util.GUIHandler;
import com.brikelos.util.Util;

public class main {

    public static void main(String args[]) {

        try {
            Util.backupDatabase();
        } catch(Exception e) {
            System.out.println("Backup error");
            e.printStackTrace();
        }

        GUIHandler.main();

    }
}
