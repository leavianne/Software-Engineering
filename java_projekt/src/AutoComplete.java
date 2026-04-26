import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class AutoComplete {
    private JTextField inputTextField;
    private JPopupMenu autoCompleteMenu;
    private ArrayList<String> produktListe;
    private boolean isUpdating = false;
    private int selectedIndex = -1;
    private boolean vorschlagAusgewaehlt = false;

    public AutoComplete(JTextField inputTextField, ArrayList<String> produktListe) {
        this.inputTextField = inputTextField;
        this.produktListe = produktListe;
        this.autoCompleteMenu = new JPopupMenu();

        inputTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                if (!vorschlagAusgewaehlt) updateAutoComplete();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                if (!vorschlagAusgewaehlt) updateAutoComplete();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {}
        });

        inputTextField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (!autoCompleteMenu.isVisible()) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN -> selectedIndex = (selectedIndex + 1) % autoCompleteMenu.getComponentCount();
                    case KeyEvent.VK_UP -> selectedIndex = (selectedIndex - 1 + autoCompleteMenu.getComponentCount()) % autoCompleteMenu.getComponentCount();
                    case KeyEvent.VK_ENTER -> {
                        if (vorschlagAusgewaehlt) {
                            // Zweiter Enter -> Bestätigung
                            autoCompleteMenu.setVisible(false);
                            vorschlagAusgewaehlt = false;
                            selectedIndex = -1;
                        } else {
                            // Erster Enter -> Vorschlag übernehmen
                            if (selectedIndex >= 0 && selectedIndex < autoCompleteMenu.getComponentCount()) {
                                String selected = ((JMenuItem) autoCompleteMenu.getComponent(selectedIndex)).getText();
                                isUpdating = true;
                                inputTextField.setText(selected);
                                isUpdating = false;
                                vorschlagAusgewaehlt = true;
                            }
                        }
                        e.consume();
                    }
                    case KeyEvent.VK_ESCAPE -> {
                        autoCompleteMenu.setVisible(false);
                        vorschlagAusgewaehlt = false;
                        selectedIndex = -1;
                    }
                }
                if (e.getKeyCode() != KeyEvent.VK_ENTER && selectedIndex >= 0) {
                    ((JMenuItem) autoCompleteMenu.getComponent(selectedIndex)).setArmed(true);
                }
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });
    }

    private void updateAutoComplete() {
        if (isUpdating) return;
        String input = inputTextField.getText().toLowerCase();
        autoCompleteMenu.removeAll();
        selectedIndex = -1;

        if (input.isEmpty()) {
            autoCompleteMenu.setVisible(false);
            return;
        }

        int count = 0;
        for (String produkt : produktListe) {
            if (produkt.toLowerCase().startsWith(input)) {
                JMenuItem item = new JMenuItem(produkt);

                // ⭐ Mausklick-Funktion hinzufügen
                item.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        isUpdating = true;
                        inputTextField.setText(produkt);
                        isUpdating = false;
                        vorschlagAusgewaehlt = true;
                        autoCompleteMenu.setVisible(false);
                        selectedIndex = -1;
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        item.setArmed(true);
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        item.setArmed(false);
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                });

                // ⭐ Action Listener für Enter/Klick-Simulation
                item.addActionListener(e -> {
                    isUpdating = true;
                    inputTextField.setText(produkt);
                    isUpdating = false;
                    vorschlagAusgewaehlt = true;
                    autoCompleteMenu.setVisible(false);
                    selectedIndex = -1;
                });

                autoCompleteMenu.add(item);
                if (++count >= 10) break;
            }
        }

        if (autoCompleteMenu.getComponentCount() > 0) {
            autoCompleteMenu.pack();
            autoCompleteMenu.show(inputTextField, 0, inputTextField.getHeight());
            inputTextField.requestFocus();
        } else {
            autoCompleteMenu.setVisible(false);
        }
    }

    public void close() {
        autoCompleteMenu.setVisible(false);
        vorschlagAusgewaehlt = false;
        selectedIndex = -1;
    }
}