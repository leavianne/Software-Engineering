import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Font;

public class Liste_Fenster extends JFrame {

    //   UI Komponenten  
    private JPanel contentPane;
    private JButton addButton;
    private JList<Produkt> liste;
    private JButton sortierenButton;
    private JComboBox auswahlFiliale;
    private JComboBox<Integer> auswahlAnzahl;
    private JButton aufbauButton;
    private JTextField inputTextField;

    //   Listen  
    private DefaultListModel<Produkt> listModel;
    private ArrayList<String> produktListe;
    private AutoComplete autoComplete;
    private Produktarbeit produktarbeit;

    public Liste_Fenster() {

        //   Fenster Setup  
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getRootPane().setDefaultButton(addButton);

        setContentPane(contentPane);
        setTitle("Einkaufsliste");
        setSize(720, 512);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Logo_Schobbow.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //   Übersichtsplan in Fenster öffnen  
        aufbauButton.addActionListener(e -> {
            new getAufbau();
        });

        //   Einkaufsliste initialisieren  
        listModel = new DefaultListModel<>();
        liste.setModel(listModel);

        //   produktarbeit initialisieren  
        produktarbeit = new Produktarbeit(listModel, liste);

        //   Rechtsklick: Anzahl Ändern und Löschen  
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem anzahlAendern = new JMenuItem("Anzahl ändern");
        JMenuItem loeschen = new JMenuItem("Löschen");
        JMenuItem infos = new JMenuItem("Produktinformationen");
        popupMenu.add(anzahlAendern);
        popupMenu.add(loeschen);
        popupMenu.add(infos);
        //   Produkte im Supermarkt: txt in Array Speichern  
        Dateiarbeit daten = new Dateiarbeit();
        produktListe = daten.getProdukte();
        autoComplete = new AutoComplete(inputTextField, produktListe);

        // Schriftarten
        addButton.setFont(UIStyles.BUTTON_FONT);
        sortierenButton.setFont(UIStyles.BUTTON_FONT);
        aufbauButton.setFont(UIStyles.BUTTON_FONT);

        auswahlFiliale.setFont(UIStyles.COMBO_FONT);
        auswahlAnzahl.setFont(UIStyles.COMBO_FONT);

        inputTextField.setFont(UIStyles.TEXT_FIELD_FONT);

        //   Button: Hinzufügen  
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

        //   Darstellung der Liste  
        liste.setCellRenderer((list, produkt, index, isSelected, cellHasFocus) -> {
            Produkt p = (Produkt) produkt;
            JCheckBox checkBox = new JCheckBox(p.getAnzahl() + "x " + p.getName());
            checkBox.setSelected(produkt.getGekauft());
            checkBox.setFont(new Font("Consolas", Font.PLAIN, 14));
            return checkBox;
        });

        //   Produkt abhaken  
        liste.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int index = liste.locationToIndex(e.getPoint());
                produktarbeit.toggleGekauft(index);
            }
        });

        //   Sortieren Button  
        sortierenButton.addActionListener(e -> {
            produktarbeit.sortieren();
        });

        //   Rechtsklick: Untermenü öffnen  
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

        //   Produkt löschen  
        loeschen.addActionListener(e -> {
            int index = liste.getSelectedIndex();
            produktarbeit.loeschen(index);
        });

        //   Anzahl ändern  
        anzahlAendern.addActionListener(e -> {
            int index = liste.getSelectedIndex();
            produktarbeit.anzahlAendern(index);
        });
    }
}