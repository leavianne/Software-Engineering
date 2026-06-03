package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hauptklasse für die Funktion: "Schobbow - Einkaufsliste"
 * Sie initialisiert das Design und prüft die Datenbankverbindung
 * und startet das Fenster (Startseite)
 *
 * @author Lea Eichler
 * @version 1.0
 */

public class Main {

  /**
   * Einstiegspunkt der Anwendung
   * <ul>
   *     <li>Setzt Nimbus Look & Feel für eine modernere Oberfläche</li>
   *     <li>Prüft Verbindung zur Datenbank (SQLite) </li>
   *     <li>Öffnet Startfenster {@link Startseite}</li>
   * </ul>
   * @param args (wird nicht verwendet)
   */

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception e) {
      e.printStackTrace();
    }
    testDatenbankverbindung();
    new Startseite();
  }

  /**
   * Stellt eine Verbindung zur Datenbank her und prüft deren Verfügbarkeit.
   * <p>
   *      Bei erfolgreicher Verbindung wird eine Bestätigung auf der Konsole ausgegeben. Tritt ein {@link SQLException} auf, wird eine Fehlermeldung auf der Konsole sowie in einem Dialogfenster angezeigt. Die Anwendung wird anschließend mit dem Exit-Code 0 beendet.
   * </p>
   *
   * @see DatabaseConfig#getConnection()
   * @see DatabaseConfig#getConfigPath()
   */

  private static void testDatenbankverbindung() {
    try (Connection conn = DatabaseConfig.getConnection()) {
      System.out.println("Datenbankverbindung erfolgreich!");
    } catch (SQLException e) {
      System.err.println("Fehler bei der Datenbankverbindung!");
      System.err.println("Bitte bearbeite: " + DatabaseConfig.getConfigPath());
      e.printStackTrace();

      JOptionPane.showMessageDialog(null,
          "Fehler: Kann keine Verbindung zur Datenbank herstellen!\n\n" +
              "Bitte bearbeite die Datei:\n" + DatabaseConfig.getConfigPath(),
          "Verbindungsfehler",
          JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }
  }
}