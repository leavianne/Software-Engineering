import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Zentrale Datenbankverbindung
// Alle Nutzer müssen die Einstellungen in config.properties ändern

public class DatabaseConfig {

    private static final String CONFIG_FILE = "src/config.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        loadConfig();
    }

    private static void loadConfig() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("⚠️ config.properties nicht gefunden! Verwende Standard-Einstellungen.");
            // Standard-Einstellungen als Fallback
            setDefaults();
        }
    }

    private static void setDefaults() {
        properties.setProperty("db.server", "localhost");
        properties.setProperty("db.port", "1433");
        properties.setProperty("db.name", "supermarkt_db");
        properties.setProperty("db.user", "sa");
        properties.setProperty("db.password", "");
    }

    public static Connection getConnection() throws SQLException {
        String server = properties.getProperty("db.server", "localhost");
        String port = properties.getProperty("db.port", "1433");
        String dbName = properties.getProperty("db.name", "supermarkt_db");
        String user = properties.getProperty("db.user", "sa");
        String password = properties.getProperty("db.password", "");

        String connectionString = String.format(
                "jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true",
                server, port, dbName
        );

        return DriverManager.getConnection(connectionString, user, password);
    }

    public static String getConfigPath() {
        return CONFIG_FILE;
    }
}