import javax.swing.*;
import java.awt.Image;

public class Startseite extends JFrame {
    private JPanel mainPanel;
    private JPanel Titel;
    private JPanel Buttons;
    private JLabel Logo;
    private JLabel Ueberschrift;
    private JLabel Untertitel;
    private JButton einkaufsliste;
    private JButton neueListe;

    public Startseite() {
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setTitle("Schobbow - Einkaufsliste");
        setSize(720, 512);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("img/Logo_Schobbow.png").getImage());

        setContentPane(mainPanel);

        // Schriftarten
        einkaufsliste.setFont(UIStyles.BUTTON_FONT);
        neueListe.setFont(UIStyles.BUTTON_FONT);

        // Logo
        ImageIcon icon = new ImageIcon("img/Logo_Schobbow.png");
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        Logo.setIcon(new ImageIcon(scaledImg));

        // Button: Einkaufsliste
        einkaufsliste.addActionListener(e -> {
            new Liste_Fenster().setVisible(true);
            dispose();
        });

        // Button: Neue Liste erstellen
        neueListe.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Neue Liste wird erstellt...");
        });

        setVisible(true);
    }
}