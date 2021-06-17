package com.brikelos.util;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Util {
    
    private static final String BACKUP_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + "Brikelos" + File.separator + "backups";
    private static final String BACKUP_DETECTION_FILE = BACKUP_PATH + File.separator + "backup.txt";
    private static final String DATABASE_FILE_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + "Brikelos" + File.separator + "database";


    /**
     * Check if a String has only numbers or not.
     * @param strNum
     * @return boolean
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) return false;
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Driver to manage the backup of the database.
     */
    public static void backupDatabase() {

        File backupDir = new File(BACKUP_PATH);
        if (!backupDir.exists()){
            backupDir.mkdirs();
        }

        try {
            LocalDate backupDate = getBackupDate();
            if(!backupDate.toString().equals(LocalDate.now().format(DateTimeFormatter.ISO_DATE))) {
                makeBackup();
                newBackupDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
            }
        } catch (IOException | ParseException e) {
            try {
                newBackupDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
                makeBackup();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Puts a new backup date to the file.
     * @param date
     * @throws IOException
     */
    private static void newBackupDate(String date) throws IOException {
        FileWriter fileWriter = new FileWriter(BACKUP_DETECTION_FILE);

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(date);
        System.out.println("Backup date updated.");
        bufferedWriter.close();
    }

    /**
     * Gets the backup date from the file.
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private static LocalDate getBackupDate() throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(BACKUP_DETECTION_FILE));
        String date = br.readLine();
        br.close();

        return LocalDate.parse(date);
    }

    /**
     * First it creates the backup folder if it doesn't exists.
     *
     * Checks if in the directory are more than 20 backups.
     * If yes: Searches for the oldest backup and removes it.
     * If no: continue.
     *
     * Copies the database to the path of the current date.
     */
    private static void makeBackup() {
        /**
         * Creates a folder for the daily backup
         */
        File newBackupDir = new File(BACKUP_PATH + File.separator + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        if (!newBackupDir.exists()){
            newBackupDir.mkdirs();
        }

        File file = new File(BACKUP_PATH);
        if(file.listFiles(f -> f.isDirectory()).length > 20) {
            File[] files = file.listFiles();
            ArrayList<LocalDate> fileDates = new ArrayList<>();
            for(File f: Arrays.asList(files)) {
                if(f.isDirectory()) {
                    fileDates.add(LocalDate.parse(f.getName()));
                }
            }
            File oldestFile = new File(BACKUP_PATH + File.separator + Collections.min(fileDates));
            oldestFile.delete();
        }

        /**
         * Copies the database.
         */
        try {
            Files.copy(Paths.get(DATABASE_FILE_PATH + File.separator + "sqlite.db"),
                    Paths.get(BACKUP_PATH + File.separator + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + File.separator + "sqlite.db")
            );
        } catch (IOException e) {
            System.out.println("No database found to backup.");
        }
    }
}
