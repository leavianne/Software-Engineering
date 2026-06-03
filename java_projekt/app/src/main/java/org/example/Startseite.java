package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * Das Startfenster der Anwendung "Schobbow - Einkaufsliste".
 * <p>
 * Es zeigt ein Logo, einen Begrüßungstext sowie zwei Aktionsbuttons:
 * <ul>
 *     <li>„Einkaufsliste öffnen“ – öffnet das Hauptfenster {@link Liste_Fenster} und schließt die Startseite.</li>
 *     <li>„Neue Liste erstellen“ – zeigt eine Informationsmeldung (aktuell noch Platzhalter).</li>
 * </ul>
 * </p>
 *
 * @author Lea Eichler
 * @version 1.0
 */

public class Startseite extends JFrame {

  private JPanel mainPanel;
  private JLabel Logo;
  private JLabel Ueberschrift;
  private JLabel Untertitel;
  private JButton einkaufsliste;
  private JButton neueListe;

  /**
   * Erzeugt das Startfenster.
   * <p>
   * Die Fenstergröße wird auf 720x512 Pixel gesetzt, es wird zentriert und
   * das Fenster-Schließen beendet die gesamte Anwendung. Das Logo wird aus der
   * Ressource geladen, das Benutzerinterface wird aufgebaut, Listener und
   * Schriftarten werden konfiguriert. Abschließend wird das Panel sichtbar gemacht.
   * </p>
   */

  public Startseite() {
    setTitle("Schobbow - Einkaufsliste");
    setSize(720, 512);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setIconImage(new ImageIcon(getClass().getResource("/Logo_Schobbow.png")).getImage());

    createUI();
    setupListeners();
    setupFonts();

    setContentPane(mainPanel);
    setVisible(true);
  }

  /**
   * Baut das gesamte grafische Benutzerinterface auf.
   * <p>
   * Es werden zwei Bereiche erzeugt:
   * <ul>
   *     <li>Ein oberer Bereich (Nord) mit Logo, Überschrift und Untertitel.</li>
   *     <li>Ein zentraler Bereich (Center) mit zwei Buttons.</li>
   * </ul>
   * Die Hintergrundfarbe wird auf einen dunklen Grünwert gesetzt, und die
   * Buttons erhalten eine einheitliche Größe und einheitliches Aussehen.
   * </p>
   */

  private void createUI() {
    mainPanel = new JPanel();
    mainPanel.setBackground(new Color(-2827078));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    mainPanel.setLayout(new BorderLayout());

    // Title panel
    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(new Color(-2827078));
    titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
    titlePanel.setAlignmentY(Component.TOP_ALIGNMENT);

    Logo = new JLabel();
    ImageIcon icon = new ImageIcon(getClass().getResource("/Logo_Schobbow.png"));
    Image img = icon.getImage();
    Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    Logo.setIcon(new ImageIcon(scaledImg));
    Logo.setAlignmentX(Component.CENTER_ALIGNMENT);

    Ueberschrift = new JLabel("Willkommen bei Schobbow!");
    Ueberschrift.setFont(new Font("Consolas", Font.BOLD, 26));
    Ueberschrift.setForeground(new Color(-16777216));
    Ueberschrift.setAlignmentX(Component.CENTER_ALIGNMENT);

    Untertitel = new JLabel("Was gibt's heute?");
    Untertitel.setFont(new Font("Consolas", Font.PLAIN, 20));
    Untertitel.setForeground(new Color(-16777216));
    Untertitel.setAlignmentX(Component.CENTER_ALIGNMENT);

    titlePanel.add(Box.createVerticalStrut(20));
    titlePanel.add(Logo);
    titlePanel.add(Box.createVerticalStrut(10));
    titlePanel.add(Ueberschrift);
    titlePanel.add(Box.createVerticalStrut(5));
    titlePanel.add(Untertitel);

    // Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(-2827078));
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    einkaufsliste = new JButton("Einkaufsliste öffnen");
    einkaufsliste.setBackground(new Color(-8747686));
    einkaufsliste.setForeground(Color.WHITE);
    einkaufsliste.setMaximumSize(new Dimension(300, 50));
    einkaufsliste.setAlignmentX(Component.CENTER_ALIGNMENT);
    einkaufsliste.setFocusPainted(false);

    neueListe = new JButton("Neue Liste erstellen");
    neueListe.setBackground(new Color(-8747686));
    neueListe.setForeground(Color.WHITE);
    neueListe.setMaximumSize(new Dimension(300, 50));
    neueListe.setAlignmentX(Component.CENTER_ALIGNMENT);
    neueListe.setFocusPainted(false);

    buttonPanel.add(Box.createVerticalStrut(20));
    buttonPanel.add(einkaufsliste);
    buttonPanel.add(Box.createVerticalStrut(10));
    buttonPanel.add(neueListe);
    buttonPanel.add(Box.createVerticalGlue());

    mainPanel.add(titlePanel, BorderLayout.NORTH);
    mainPanel.add(buttonPanel, BorderLayout.CENTER);
  }

  /**
   * Weist den Buttons die einheitlichen Schriftarten aus {@link UIStyles} zu.
   */

  private void setupFonts() {
    einkaufsliste.setFont(UIStyles.BUTTON_FONT);
    neueListe.setFont(UIStyles.BUTTON_FONT);
  }

  /**
   * Registriert die Aktionslistener für die beiden Buttons.
   * <ul>
   *     <li>Der „Einkaufsliste öffnen“-Button öffnet das {@link Liste_Fenster} und schließt die Startseite.</li>
   *     <li>Der „Neue Liste erstellen“-Button zeigt lediglich eine Dialogmeldung (Platzhalter für zukünftige Funktionalität).</li>
   * </ul>
   */

  private void setupListeners() {
    einkaufsliste.addActionListener(e -> {
      new Liste_Fenster().setVisible(true);
      dispose();
    });

    neueListe.addActionListener(e -> {
      JOptionPane.showMessageDialog(null, "Neue Liste wird erstellt...");
    });
  }
}