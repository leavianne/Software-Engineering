package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Einkaufsliste Sortierungstests")
public class TestListe {

  private DefaultListModel<Produkt> listModel;
  private Produktarbeit produktarbeit;

  @BeforeEach
  public void setUp() {
    //ListModel und Produktarbeit für jeden Test initialisieren
    listModel = new DefaultListModel<>();
    //JList für Produktarbeit (als Parameter)
    JList<Produkt> liste = new JList<>(listModel);
    produktarbeit = new Produktarbeit(listModel, liste);
  }

  /**
   * Test 1: Wenn man den Sortierbutton bei einer leeren Liste klickt, sollte nichts passieren
   */
  @Test
  @DisplayName("Test 1: Sortier Button auf leerer Liste - Sollte keine Fehler machen und leer bleiben")
  public void testLeereListeSortieren() {
    // Arrange: Liste ist leer (In setUp initialisiert)
    assertTrue(listModel.isEmpty(), "Liste sollte zuerst leer sein");

    // Act: Versuchen, die leere Liste zu sortieren
    produktarbeit.sortieren();

    // Assert: Sollte keine Fehler machen und leer bleiben
    assertTrue(listModel.isEmpty(), "Liste sollte leer bleiben");
    assertEquals(0, listModel.size(), "Listengröße sollte 0 sein");
  }

  /**
   * Test 2: Wenn man ein Produkt in das Feld schreibt und den Hinzufügen-Button klickt, sollte es
   * als Eintrag in der Liste erscheinen
   */
  @Test
  @DisplayName("Test 2: Ein hinzugefügter Eintrag sollte in der Liste erscheinen")
  public void testEintragZuListeHinzufuegen() {
    // Arrange
    String productName = "Äpfel";  // "Äpfel" aus der Datenbank
    int quantity = 2;

    // Act: Produkt zur Liste hinzufügen
    produktarbeit.hinzufuegen(productName, quantity);

    // Assert: Produkt sollte in der Liste erscheinen
    assertEquals(1, listModel.size(), "Liste sollte 1 Eintrag haben");
    Produkt addedProduct = listModel.getElementAt(0);
    assertEquals(productName, addedProduct.getName(), "Produktname sollte übereinstimmen");
    assertEquals(quantity, addedProduct.getAnzahl(), "Anzahl sollte 2 sein");
  }

  /**
   * Test 3: Mehrere Produkte werden zur Liste hinzugefügt und sortiert, die Produkte sollten dann
   * nach ihrer Abteilung sortiert sein.
   */
  @Test
  @DisplayName("Test 3: Mehrere Produkte zur Liste hinzufügen, sollten nach Abteilung sortiert werden")
  public void testMehrereProdukteSortieren() {
    // Arrange: Produkte von verschiedenen Abteilungen hinzufügen
    // Äpfel = Abteilung 1, Karotten = Abteilung 2, Mehl = Abteilung 3
    produktarbeit.hinzufuegen("Äpfel", 1);      // Abteilung 1
    produktarbeit.hinzufuegen("Karotten", 2);   // Abteilung 2
    produktarbeit.hinzufuegen("Mehl", 1);       // Abteilung 3
    produktarbeit.hinzufuegen("Bananen", 1);    // Abteilung 1

    // Sichergehen, dass die Einträge hinzugefügt wurden
    assertEquals(4, listModel.size(), "Sollte 4 Einträge vor dem sortieren haben");

    // Act: Liste sortieren
    produktarbeit.sortieren();

    // Assert: Einträge sollten sortiert sein (1, 1, 2, 3)
    assertEquals(4, listModel.size(), "Sollte 4 Einträge nach dem sortieren haben");

    // Abteilung 1
    assertEquals(1, listModel.getElementAt(0).getAbteilung(),
        "Erster Eintrag sollte von Abteilung 1 sein");
    assertEquals(1, listModel.getElementAt(1).getAbteilung(),
        "Zweiter Eintrag sollte von Abteilung 1 sein");

    // Abteilung 2
    assertEquals(2, listModel.getElementAt(2).getAbteilung(),
        "Dritter Eintrag sollte von Abteilung 2 sein");

    // Abteilung 3
    assertEquals(3, listModel.getElementAt(3).getAbteilung(),
        "Vierter Eintrag sollte von Abteilung 3 sein");
  }

  /**
   * Test 4: Eine schon sortierte Liste wird sortiert, Anordnung sollte so bleiben und sich nicht
   * zufällig verändern
   */
  @Test
  @DisplayName("Test 4: Schon sortierte Liste sortieren - Sollte unverändert bleiben")
  public void testSortiereSortierteListe() {
    // Arrange: Einträge hinzufügen, die eigentlich schon sortiert sind
    produktarbeit.hinzufuegen("Äpfel", 1);      // Abteilung 1
    produktarbeit.hinzufuegen("Karotten", 1);   // Abteilung 2
    produktarbeit.hinzufuegen("Mehl", 1);       // Abteilung 3

    // Zustand davor speichern
    String[] stateBeforeSort = new String[listModel.size()];
    for (int i = 0; i < listModel.size(); i++) {
      stateBeforeSort[i] = listModel.getElementAt(i).getName();
    }

    // Act: Sortieren
    produktarbeit.sortieren();

    // Assert: Liste sollte gleich bleiben
    assertEquals(stateBeforeSort.length, listModel.size(),
        "Listengröße sollte sich nicht ändern");

    for (int i = 0; i < listModel.size(); i++) {
      assertEquals(stateBeforeSort[i], listModel.getElementAt(i).getName(),
          "Eintrag an der Position " + i + " sollte sich nicht verändern");
    }
  }

  /**
   * Test 5: Mehrmals sortieren, sollte gleich bleiben
   */
  @Test
  @DisplayName("Test 5: Mehrmaliges Sortieren - Sollte immer dasselbe Ergebnis haben")
  public void testMehrmalsDieselbeListeSortieren() {
    // Arrange: Einträge in beliebiger Reihenfolge
    produktarbeit.hinzufuegen("Mehl", 1);       // Abteilung 3
    produktarbeit.hinzufuegen("Äpfel", 1);      // Abteilung 1
    produktarbeit.hinzufuegen("Karotten", 1);   // Abteilung 2

    // Act & Assert: Mehrmals sortieren und sichergehen, dass es immer dasselbe Ergebnis hat
    produktarbeit.sortieren();
    String[] firstSort = aktuellerListenZustand();

    produktarbeit.sortieren();
    String[] secondSort = aktuellerListenZustand();

    produktarbeit.sortieren();
    String[] thirdSort = aktuellerListenZustand();

    // Alle Sorts sollten gleich sein
    assertArrayEquals(firstSort, secondSort,
        "Erstes und zweites Sortieren sollten dasselbe Ergebnis haben");
    assertArrayEquals(secondSort, thirdSort,
        "Zweites und drittes Sortieren sollten dasselbe Ergebnis haben");
  }

  /**
   * Test 6: Beim Hinzufügen von Duplikaten sollte sich die Anzahl von dem schon existierenden
   * Eintrag erhöhen
   */
  @Test
  @DisplayName("Test 6: Duplikate hinzufügen - Sollte Anzahl erhöhen und nur bei 1 Eintrag bleiben")
  public void testDuplikatHinzufuegen() {
    // Arrange & Act: Add the same item twice
    produktarbeit.hinzufuegen("Äpfel", 2);
    produktarbeit.hinzufuegen("Äpfel", 3);

    // Assert: Sollte einen Eintrag namens "Äpfel" mit der Anzahl 5 haben
    assertEquals(1, listModel.size(), "Sollte nur 1 Eintrag haben");
    Produkt product = listModel.getElementAt(0);
    assertEquals("Äpfel", product.getName(), "Produktname sollte Äpfel sein");
    assertEquals(5, product.getAnzahl(), "Anzahl sollte 5 sein (2+3)");
  }

  /**
   * Test 7: Nach Duplikaten hinzufügen sortieren
   */
  @Test
  @DisplayName("Test 7: Sortieren, nachedem Duplikate hinzugefügt wurden - Sollte korrekt funktionieren")
  public void testSortierenNachDuplikatEinfuegen() {
    // Arrange: Unsortierte Einträge mit Duplikaten einfügen
    produktarbeit.hinzufuegen("Mehl", 1);        // Abteilung 3
    produktarbeit.hinzufuegen("Äpfel", 2);       // Abteilung 1
    produktarbeit.hinzufuegen("Äpfel", 1);       // Abteilung 1 (Duplikat)
    produktarbeit.hinzufuegen("Karotten", 1);    // Abteilung 2

    // Act: Sortieren
    produktarbeit.sortieren();

    // Assert: Sicherstellen, dass die Reihenfolge richtig ist
    assertEquals(3, listModel.size(), "Liste sollte 3 Einträge haben");
    assertEquals(1, listModel.getElementAt(0).getAbteilung(),
        "Erster Eintrag sollte von Abteilung 1 sein");
    assertEquals(3, listModel.getElementAt(0).getAnzahl(),
        "Erster Eintrag (Äpfel) sollte Anzahl 3 haben");
  }

  // Hilfsmethode, um den aktuellen Zustand der Liste zu speichern
  private String[] aktuellerListenZustand() {
    String[] zustand = new String[listModel.size()];
    for (int i = 0; i < listModel.size(); i++) {
      zustand[i] = listModel.getElementAt(i).getName() +
          ":" + listModel.getElementAt(i).getAbteilung();
    }
    return zustand;
  }
}