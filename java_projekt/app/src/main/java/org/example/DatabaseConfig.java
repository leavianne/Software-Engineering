package org.example;

import java.io.*;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Verwaltet die Konfiguration und den Verbindungsaufbau zur SQLite-Datenbank.
 * 
 * Die Klasse lädt die Datenbankkonfiguration aus der Datei {@code config.properties}.
 * Falls keine Konfigurationsdatei gefunden wird, wird ein Standardname für die Datenbankdatei verwendet.
 * @author Lea Eichler
 * @version 1.0
 */
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

  /**
   * Privater Standardkonstruktor, um die Instanziierung dieser Utility-Klasse zu verhindern.
   * <p>Diese Klasse enthält nur statische Methoden und sollte nicht instanziiert werden.</p>
   */
  private DatabaseConfig() {
    // Verhindert die Erzeugung von Objekten dieser Klasse
    throw new UnsupportedOperationException("Diese Utility-Klasse darf nicht instanziiert werden.");
  }

  /**
   * Erstellt eine Verbindung zur SQLite-Datenbank.
   * 
   * Die Datenbankdatei wird bei Bedarf aus dem Classpath in ein temporäres Verzeichnis
   * kopiert. Anschließend wird eine Verbindung mit aktiviertem WAL-Modul aufgebaut.
   * 
   * @return geöffnete Datenbankverbindung
   * @throws SQLException wenn die Datenbankdatei nicht kopiert werden kann oder keine
   * Verbindung ausgebaut werden kann
   */
  public static Connection getConnection() throws SQLException {
    String dbFileName = properties.getProperty("db.file", "supermarkt.db");

    // Das System Temp Verzeichnis nutzen anstatt des aktuellen Verzeichnisses
    Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "supermarkt-app");
    try {
      Files.createDirectories(tempDir);
    } catch (IOException e) {
      throw new SQLException("Fehler beim Erstellen des Temp-Verzeichnisses: " + e.getMessage(), e);
    }

    File dbFile = tempDir.resolve(dbFileName).toFile();

    // Falls die Datei noch nicht existiert, kopiere sie aus dem Classpath
    if (!dbFile.exists()) {
      try (InputStream in = DatabaseConfig.class.getResourceAsStream("/" + dbFileName)) {
        if (in != null) {
          Files.copy(in, dbFile.toPath());
          System.out.println("Datenbank aus Classpath kopiert: " + dbFile.getAbsolutePath());
        } else {
          throw new SQLException(
              "Datenbankdatei '" + dbFileName + "' nicht im Classpath gefunden!");
        }
      } catch (IOException e) {
        throw new SQLException("Fehler beim Kopieren der DB-Datei: " + e.getMessage(), e);
      }
    }

    // SQLite-Verbindung mit WAL-Modus für bessere Performance
    String url = "jdbc:sqlite:" + dbFile.getAbsolutePath() + "?journal_mode=WAL";
    return DriverManager.getConnection(url);
  }

  /**
   * Gibt den Speicherort der Konfigurationsdatei zurück.
   * 
   * @return Pfadangabe zur Konfigurationsdatei im Classpath
   */
  public static String getConfigPath() {
    return "classpath:" + CONFIG_RESOURCE;
  }
}