import javax.swing.*;
import java.awt.*;

public class getAufbau extends JFrame {
    private Image img;
    public getAufbau() {
        setTitle("Supermarkt Aufbau");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        img = new ImageIcon("img/Aufbau.png").getImage();
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img,0,0,getWidth(),getHeight(),this);   // Bild auf Fenstergröße skalieren
            }
        };
        add(panel);
        setVisible(true);
    }
}