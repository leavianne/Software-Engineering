package org.example;

import java.sql.*;
import java.util.ArrayList;

public class Dateiarbeit {

    private ArrayList<String> produkte = new ArrayList<>();

    public Dateiarbeit() {
        ladeProdukte();
    }

    // Produktname für AutoComplete aus txt filtern und speichern
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

    public ArrayList<String> getProdukte() {
        return produkte;
    }
}