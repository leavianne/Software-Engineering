import java.io.*;
import java.util.ArrayList;

public class Dateiarbeit {

    private ArrayList<String> produkte = new ArrayList<>();

    public Dateiarbeit() {
        ladeProdukte();
    }

    // Produktname für AutoComplete aus txt filtern und speichern
    private void ladeProdukte() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/AufbauSupermarkt.txt"))) {
            String zeile;
            while ((zeile = br.readLine()) != null) {
                String[] teile = zeile.split(";");
                produkte.add(teile[0].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getProdukte() {
        return produkte;
    }
}