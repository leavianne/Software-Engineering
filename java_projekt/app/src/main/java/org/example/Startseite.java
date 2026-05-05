package org.example;

import javax.swing.*;
import java.awt.*;

public class Startseite extends JFrame {

  private JPanel mainPanel;
  private JLabel Logo;
  private JLabel Ueberschrift;
  private JLabel Untertitel;
  private JButton einkaufsliste;
  private JButton neueListe;

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

  private void setupFonts() {
    einkaufsliste.setFont(UIStyles.BUTTON_FONT);
    neueListe.setFont(UIStyles.BUTTON_FONT);
  }

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