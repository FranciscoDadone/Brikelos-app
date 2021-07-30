package com.brikelos;

import ch.qos.logback.classic.Logger;
import com.brikelos.util.GUIHandler;
import com.brikelos.model.Remote.queries.MongoBackup;
import com.brikelos.util.Util;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

public class main {

    public static void main(String args[]) {

        try {
            Util.backupDatabase();
        } catch(Exception e) {
            System.out.println("Backup error");
            e.printStackTrace();
        }

        // Disabling all the logging in mongo connection
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);

        GUIHandler.main();
        MongoBackup.Backup();

    }
}
