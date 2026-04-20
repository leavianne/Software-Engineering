import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        try (BufferedReader br = new BufferedReader(new FileReader("src/AufbauSupermarkt.txt"))) {
            String zeile;
            while ((zeile = br.readLine()) != null) {
                String[] teile = zeile.split(";");
                if (teile.length >= 2) {
                    String produktName = teile[0].trim().toLowerCase();
                    int abteilung = Integer.parseInt(teile[1].trim());
                    abteilungsCache.put(produktName, abteilung);
                }
            }
            cacheGeladen = true;
        } catch (IOException e) {
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