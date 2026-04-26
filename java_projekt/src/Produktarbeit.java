import javax.swing.*;

public class Produktarbeit {
    private DefaultListModel<Produkt> listModel;
    private JList<Produkt> liste;

    public Produktarbeit(DefaultListModel<Produkt> listModel, JList<Produkt> liste) {
        this.listModel = listModel;
        this.liste = liste;
    }

    // Produkt abhaken
    public void toggleGekauft(int index) {
        if (index != -1) {
            Produkt p = listModel.getElementAt(index);
            p.setGekauft(!p.getGekauft());
            liste.repaint();
        }
    }

    // Produkt löschen
    public void loeschen(int index) {
        if (index != -1) {
            listModel.remove(index);
        }
    }

    // Anzahl ändern
    public void anzahlAendern(int index) {
        if (index != -1) {
            Produkt p = listModel.getElementAt(index);
            String input = JOptionPane.showInputDialog("Neue Anzahl eingeben:",p.getAnzahl());
            if (input != null && !input.isEmpty()) {
                try {
                    int neueAnzahl = Integer.parseInt(input);
                    p.setAnzahl(neueAnzahl);
                    listModel.set(index, p);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ungültige Zahl!");
                }
            }
        }
    }

    // Produkt hinzufügen
    public void hinzufuegen(String text, int anzahl) {
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bitte Produkt eingeben!");
            return;
        }

        boolean gefunden = false;
        for (int i = 0; i < listModel.size(); i++) {
            Produkt p = listModel.getElementAt(i);

            if (p.getName().equalsIgnoreCase(text)) {
                p.setAnzahl(p.getAnzahl() + anzahl);
                listModel.set(i, p);
                gefunden = true;
                break;
            }
        }

        if (!gefunden) {
            listModel.addElement(new Produkt(text, anzahl));
        }

        int lastIndex = listModel.getSize() - 1;
        liste.ensureIndexIsVisible(lastIndex);
    }
}