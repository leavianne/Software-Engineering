package org.example;

import java.sql.*;
import java.util.ArrayList;

/**
 * Lädt Produktdaten aus der Datenbank und stellt sie für andere Klassen bereit.
 * 
 * Die Klasse wird unter anderem für die AutoComplete-Funktion und für das Anzeigen
 * von Nährwertinformationen verwendet.
 */
public class Dateiarbeit {

  private ArrayList<String> produkte = new ArrayList<>();

  /**
   * Erstellt ein Objekt für die Datenarbeit und lädt direkt alle Produktnamen aus der Datenbank.
   */
  public Dateiarbeit() {
    ladeProdukte();
  }

  /**
   * Lädt alle Produktnamen aus der Datenbank und speichert sie in der Produktliste.
   */
  private void ladeProdukte() {
    try (Connection conn = DatabaseConfig.getConnection()) {
      String sql = "SELECT produktname FROM produkte";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        produkte.add(rs.getString("produktname"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gibt die geladenen Produktnamen zurück.
   * 
   * @return Liste aller Produktnamen aus der Datenbank
   */
  public ArrayList<String> getProdukte() {
    return produkte;
  }

  /**
   * Sucht die Nährwerte eines bestimmten Produkts in der Datenbank.
   * 
   * @param produktname Name des Produkts, dessen Nährwerte gesucht werden
   * @return Nährwertinformationen des Produkts oder {@code null}, wenn kein Eintrag gefunden wurde
   */
  public String getNaehrwerte(String produktname) {
    String sql = "SELECT naehrwerte FROM produkte WHERE produktname = ?";
    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, produktname);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        return rs.getString("naehrwerte");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}