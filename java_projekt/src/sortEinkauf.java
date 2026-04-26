import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class sortEinkauf {
    public static void sortieren (DefaultListModel<Produkt> listModel) {
        ArrayList<Produkt> tempListe = new ArrayList<>();

        for (int i = 0; i < listModel.size(); i++) {
            tempListe.add(listModel.getElementAt(i));
        }
        tempListe.sort(Comparator.comparingInt(Produkt::getAbteilung));
        listModel.clear();

        for (Produkt p : tempListe) {
            listModel.addElement(p);
        }
    }
}