import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        testDatenbankverbindung();
        new Startseite();
    }

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