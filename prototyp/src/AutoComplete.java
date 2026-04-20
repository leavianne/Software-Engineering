import javax.swing.*;
import java.util.ArrayList;

public class AutoComplete {

    private JTextField inputTextField;      // Eingabefeld
    private JPopupMenu autoCompleteMenu;    // Vorschläge
    private ArrayList<String> produktListe; // Liste Produkte im Supermarkt

    private boolean isUpdating = false;

    public AutoComplete(JTextField inputTextField, ArrayList<String> produktListe) {
        this.inputTextField = inputTextField;
        this.produktListe = produktListe;
        this.autoCompleteMenu = new JPopupMenu();
        inputTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() { // Listener Texteingabe

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                if (!isUpdating) {
                    SwingUtilities.invokeLater(AutoComplete.this::updateAutoComplete);  // Update verzögern -> Swing Stabil
                }
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                if (!isUpdating) {
                    SwingUtilities.invokeLater(AutoComplete.this::updateAutoComplete);
                }
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {}
        });
    }
    // Autocomplete Logik
    private void updateAutoComplete() {
        String input = inputTextField.getText().toLowerCase();
        autoCompleteMenu.removeAll();   // alte Vorschläge entfernen

        if (input.isEmpty()) {
            autoCompleteMenu.setVisible(false);
            return;
        }

        int count = 0;

        // Produkte durchsuchen
        for (String produkt : produktListe) {
            // Filter
            if (produkt.toLowerCase().startsWith(input)) {
                JMenuItem item = new JMenuItem(produkt);

                // Vorschlag ausgewählt -> in die Eingabe übernehmen
                item.addActionListener(e -> {
                    isUpdating = true;
                    inputTextField.setText(produkt);
                    isUpdating = false;
                    autoCompleteMenu.setVisible(false);
                });
                autoCompleteMenu.add(item);
                count++;
                if (count >= 10) break;
            }
        }

        // Vorschläge als Popup anzeigen
        if (autoCompleteMenu.getComponentCount() > 0) {
            if (autoCompleteMenu.isVisible()) {
                autoCompleteMenu.setVisible(false);
                autoCompleteMenu.pack();
                autoCompleteMenu.show(inputTextField, 0, inputTextField.getHeight());
            } else {
                autoCompleteMenu.pack();
                autoCompleteMenu.show(inputTextField, 0, inputTextField.getHeight());
            }

            // Fokus zurück ins Textfeld
            SwingUtilities.invokeLater(() -> inputTextField.requestFocus());

        } else {
            autoCompleteMenu.setVisible(false);
        }
    }

    // Vorschläge manuell schließen
    public void close() {
        autoCompleteMenu.setVisible(false);
    }
}