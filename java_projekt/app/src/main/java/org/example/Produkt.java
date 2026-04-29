package org.example;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Produkt {
    private String name;
    private boolean gekauft;
    private int anzahl;
    private int abteilung;

    // Statischer Cache: Abteilung
    private static Map<String, Integer> abteilungsCache = new HashMap<>();
    private static boolean cacheGeladen = false;

    public Produkt(String name, int anzahl) {
        this.name = name;
        this.anzahl = anzahl;
        this.gekauft = false;

        if (!cacheGeladen) {ladeCacheAusDatei();}

        // Aus Cache lesen
        this.abteilung = abteilungsCache.getOrDefault(name.toLowerCase(), 0);
    }

    // Cache füllen
    private static synchronized void ladeCacheAusDatei() {
        if (cacheGeladen) return;

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

    public int getAnzahl() {return anzahl;}

    public void setAnzahl(int anzahl) {this.anzahl = anzahl;}

    public String getName() {return name;}

    public boolean getGekauft() {return gekauft;}

    public void setGekauft(boolean gekauft) {this.gekauft = gekauft;}

    public int getAbteilung() {return abteilung;}

    // Anzahl verändern
    @Override
    public String toString() {return anzahl + "x " + name;}
}