package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Enthält die Bearbeitungsfunktionen für Produkte in der Einkaufsliste.
 * 
 * Die Klasse arbeitet direkt mit dem Listenmodell und der sichtbaren JList. Dadurch können
 * Produkte hinzugefügt, gelöscht, sortiert, abgehackt oder in ihrer Anzahl geändert werden.
 */
public class Produktarbeit {

  private DefaultListModel<Produkt> listModel;
  private JList<Produkt> liste;

  /**
   * Erstellt ein Objekt zur Bearbeitung der Einkaufsliste.
   * 
   * @param listModel Datenmodell der Einkaufsliste
   * @param liste sichtbare Liste, in der die Produkte angezeigt werden
   */
  public Produktarbeit(DefaultListModel<Produkt> listModel, JList<Produkt> liste) {
    this.listModel = listModel;
    this.liste = liste;
  }

  /**
   * Ändert den Kaufstatus eines Produkts an der angegebenen Listenposition.
   * 
   * @param index Position des Produkts in der Liste
   */
  public void toggleGekauft(int index) {
    if (index != -1) {
      Produkt p = listModel.getElementAt(index);
      p.setGekauft(!p.getGekauft());
      liste.repaint();
    }
  }

  /**
   * Löscht ein Produkt aus der Einkaufsliste.
   * 
   * @param index Position des zu löschenden Produkts
   */
  public void loeschen(int index) {
    if (index != -1) {
      listModel.remove(index);
    }
  }

  /**
   * Öffnet einen Dialog zum Ändern der Produktanzahl.
   * 
   * @param index Position des Produkts, dessen Anzahl geändert werden soll
   */
  public void anzahlAendern(int index) {
    if (index != -1) {
      Produkt p = listModel.getElementAt(index);
      String input = JOptionPane.showInputDialog("Neue Anzahl eingeben:", p.getAnzahl());
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

  /**
   * Fügt ein Produkt zur Einkaufsliste hinzu.
   * 
   * Wenn das Produkt bereits existiert, wird nur die Anzahl erhöht. Ansonsten
   * wird ein neues Produktobjekt erstellt und an die Liste angehängt.
   * 
   * @param text Name des Produkts
   * @param anzahl Anzahl, die hinzugefügt werden soll
   */
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

  /**
   * Sortiert die Einkaufsliste nach den Abteilungen der Produkte.
   */
  public void sortieren() {
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