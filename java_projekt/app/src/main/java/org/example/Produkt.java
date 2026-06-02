package org.example;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Repräsentiert ein Produkt in der Einkaufsliste.
 * 
 * Ein Produkt besitzt einen Namen, eine Anzahl, einen Kaufstatus und eine Abteilung.
 * Die Abteilung wird mithilfe eines statischen Caches aus der Datenbank geladen.
 */
public class Produkt {

  private String name;
  private boolean gekauft;
  private int anzahl;
  private int abteilung;

  // Statischer Cache: Abteilung
  private static Map<String, Integer> abteilungsCache = new HashMap<>();
  private static boolean cacheGeladen = false;

  /**
   * Erstellt eib neues Produkt für die Einkaufsliste.
   * 
   * Beim Erstellen wird die passende Abteilung aus dem Cache gelesen. Falls der Cache noch
   * nicht geladen wurde, wird er vorher aus der Datenbank befüllt.
   * 
   * @param name Name des Produkts
   * @param anzahl gewünschte Anzahl des Produkts
   */
  public Produkt(String name, int anzahl) {
    this.name = name;
    this.anzahl = anzahl;
    this.gekauft = false;

    if (!cacheGeladen) {
      ladeCacheAusDatei();
    }

    // Aus Cache lesen
    this.abteilung = abteilungsCache.getOrDefault(name.toLowerCase(), 0);
  }

  /**
   * Lädt die Produktabteilungen aus der Datenbank in einen statischen Cache.
   * 
   * Die Methode ist synchronisiert, damit der Cache nicht gleichzeitig mehrfach geladen wird.
   */
  private static synchronized void ladeCacheAusDatei() {
    if (cacheGeladen) {
      return;
    }

    try (Connection conn = DatabaseConfig.getConnection()) {
      String sql = "SELECT produktname, abteilung FROM produkte";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        String produktName = rs.getString("produktname").toLowerCase();
        int abteilung = rs.getInt("abteilung");
        abteilungsCache.put(produktName, abteilung);
      }
      cacheGeladen = true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gibt die Anzahl des Produkts zurück.
   * 
   * @return aktuelle Produktanzahl
   */
  public int getAnzahl() {
    return anzahl;
  }

  /**
   * Setzt die Anzahl des Produkts.
   * 
   * @param anzahl neue Produktanzahl
   */
  public void setAnzahl(int anzahl) {
    this.anzahl = anzahl;
  }

  /**
   * Gibt den Namen des Produkts zurück.
   * 
   * @return Produktname
   */
  public String getName() {
    return name;
  }

  /**
   * Gibt zurück, ob das Produkt bereits gekauft wurde.
   * 
   * @return {@code true}, wenn das Produkt als gekauft markiert ist, sonst {@code false}
   */
  public boolean getGekauft() {
    return gekauft;
  }

  /**
   * Setzt den Kaufstatus des Produkts.
   * 
   * @param gekauft neuer Kaufstatus des Produkts
   */
  public void setGekauft(boolean gekauft) {
    this.gekauft = gekauft;
  }

  /**
   * Gibt die Abteilung des Produkts zurück.
   * 
   * @return Nummer der Abteilung; {@code 0}, wenn keine passende Abteilung gefunden wurde
   */
  public int getAbteilung() {
    return abteilung;
  }

  /**
   * Gibt das Produkt als lesbaren Text für die Anzeige in der Liste zurück.
   * 
   * @return Textdarstellung mit Anzahl und Produktname
   */
  @Override
  public String toString() {
    return anzahl + "x " + name;
  }
}