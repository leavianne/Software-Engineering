package org.example;

import java.io.*;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static final String CONFIG_RESOURCE = "/config.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = DatabaseConfig.class.getResourceAsStream(CONFIG_RESOURCE)) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("config.properties nicht gefunden, verwende Standardwerte.");
                properties.setProperty("db.file", "supermarkt.db");
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der config.properties.");
            properties.setProperty("db.file", "supermarkt.db");
        }
    }

    public static Connection getConnection() throws SQLException {
        String dbFileName = properties.getProperty("db.file", "supermarkt.db");
        File dbFile = new File(dbFileName);

        // Falls die Datei noch nicht existiert (z.B. nach frischem Klonen),
        // kopiere sie aus dem Classpath in das Arbeitsverzeichnis.
        if (!dbFile.exists()) {
            try (InputStream in = DatabaseConfig.class.getResourceAsStream("/" + dbFileName)) {
                if (in != null) {
                    Files.copy(in, dbFile.toPath());
                    System.out.println("Datenbank aus Classpath kopiert: " + dbFile.getAbsolutePath());
                } else {
                    throw new SQLException("Datenbankdatei '" + dbFileName + "' nicht im Classpath gefunden!");
                }
            } catch (IOException e) {
                throw new SQLException("Fehler beim Kopieren der DB-Datei: " + e.getMessage(), e);
            }
        }

        // SQLite-Verbindung aufbauen
        String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();
        return DriverManager.getConnection(url);
    }

    public static String getConfigPath() {
        return "classpath:" + CONFIG_RESOURCE;
    }
}