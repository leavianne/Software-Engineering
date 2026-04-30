package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Liste_Fenster extends JFrame {

    // UI Components
    private JPanel contentPane;
    private JButton addButton;
    private JList<Produkt> liste;
    private JButton sortierenButton;
    private JComboBox<String> auswahlFiliale;
    private JComboBox<Integer> auswahlAnzahl;
    private JButton aufbauButton;
    private JTextField inputTextField;

    // Lists
    private DefaultListModel<Produkt> listModel;
    private ArrayList<String> produktListe;
    private AutoComplete autoComplete;
    private Produktarbeit produktarbeit;

    public Liste_Fenster() {
        // Initialize frame
        setTitle("Einkaufsliste");
        setSize(720, 512);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Logo_Schobbow.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main content pane
        contentPane = new JPanel();
        contentPane.setBackground(new Color(-6906749));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new GridBagLayout());

        // Create components
        createComponents();
        setupListeners();
        setupFonts();

        setContentPane(contentPane);
        setVisible(true);
    }

    private void createComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Filiale ComboBox (row 0, col 0)
        auswahlFiliale = new JComboBox<>(new String[]{"Filiale Dresden"});
        auswahlFiliale.setBackground(new Color(-10919870));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        contentPane.add(auswahlFiliale, gbc);

        // Aufbau Button (row 0, col 2)
        aufbauButton = new JButton("Aufbau");
        aufbauButton.setBackground(new Color(-10919870));
        aufbauButton.setFocusPainted(false);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        contentPane.add(aufbauButton, gbc);

        // Sortieren Button (row 0, col 1)
        sortierenButton = new JButton("sortieren");
        sortierenButton.setBackground(new Color(-10919870));
        sortierenButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        contentPane.add(sortierenButton, gbc);

        // List (row 1, cols 0-2)
        listModel = new DefaultListModel<>();
        liste = new JList<>(listModel);
        liste.setBackground(new Color(-6906749));
        JScrollPane scrollPane = new JScrollPane(liste);
        scrollPane.setPreferredSize(new Dimension(500, 347));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(scrollPane, gbc);

        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Anzahl ComboBox (row 2, col 0)
        auswahlAnzahl = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        auswahlAnzahl.setBackground(new Color(-10919870));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        contentPane.add(auswahlAnzahl, gbc);

        // Input TextField (row 2, col 1)
        inputTextField = new JTextField();
        inputTextField.setBackground(new Color(-10919870));
        inputTextField.setForeground(new Color(-1));
        inputTextField.setMargin(new Insets(2, 9, 2, 6));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.4;
        contentPane.add(inputTextField, gbc);

        // Add Button (row 2, col 2)
        addButton = new JButton("hinzufügen");
        addButton.setBackground(new Color(-10919870));
        addButton.setFocusPainted(false);
        getRootPane().setDefaultButton(addButton);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        contentPane.add(addButton, gbc);

        // Setup list rendering
        setupListRenderer();

        // Initialize model and services
        produktarbeit = new Produktarbeit(listModel, liste);
        Dateiarbeit daten = new Dateiarbeit();
        produktListe = daten.getProdukte();
        autoComplete = new AutoComplete(inputTextField, produktListe);
    }

    private void setupListRenderer() {
        liste.setCellRenderer((list, produkt, index, isSelected, cellHasFocus) -> {
            Produkt p = (Produkt) produkt;
            JCheckBox checkBox = new JCheckBox(p.getAnzahl() + "x " + p.getName());
            checkBox.setSelected(produkt.getGekauft());
            checkBox.setFont(new Font("Consolas", Font.PLAIN, 14));
            return checkBox;
        });
    }

    private void setupFonts() {
        addButton.setFont(UIStyles.BUTTON_FONT);
        sortierenButton.setFont(UIStyles.BUTTON_FONT);
        aufbauButton.setFont(UIStyles.BUTTON_FONT);

        auswahlFiliale.setFont(UIStyles.COMBO_FONT);
        auswahlAnzahl.setFont(UIStyles.COMBO_FONT);

        inputTextField.setFont(UIStyles.TEXT_FIELD_FONT);
    }

    private void setupListeners() {
        // Aufbau Button
        aufbauButton.addActionListener(e -> new getAufbau());

        // Add Button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputTextField.getText().trim();
                try {
                    int anzahl = Integer.parseInt(auswahlAnzahl.getSelectedItem().toString());
                    produktarbeit.hinzufuegen(text, anzahl);
                    autoComplete.close();
                    inputTextField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ungültige Anzahl!");
                }
            }
        });

        // List click listener (toggle purchased)
        liste.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int index = liste.locationToIndex(e.getPoint());
                produktarbeit.toggleGekauft(index);
            }
        });

        // Sortieren Button
        sortierenButton.addActionListener(e -> produktarbeit.sortieren());

        // Right-click context menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem anzahlAendern = new JMenuItem("Anzahl ändern");
        JMenuItem loeschen = new JMenuItem("Löschen");
        JMenuItem infos = new JMenuItem("Produktinformationen");
        popupMenu.add(anzahlAendern);
        popupMenu.add(loeschen);
        popupMenu.add(infos);

        liste.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = liste.locationToIndex(e.getPoint());
                    liste.setSelectedIndex(index);
                    popupMenu.show(liste, e.getX(), e.getY());
                }
            }
        });

        // Delete
        loeschen.addActionListener(e -> {
            int index = liste.getSelectedIndex();
            produktarbeit.loeschen(index);
        });

        // Change quantity
        anzahlAendern.addActionListener(e -> {
            int index = liste.getSelectedIndex();
            produktarbeit.anzahlAendern(index);
        });
    }
}