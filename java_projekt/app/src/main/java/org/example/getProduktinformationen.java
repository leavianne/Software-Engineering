package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * Zeigt ein separates Fenster mit den Produktinformationen eines ausgewählten Produkts an.
 * 
 * Die übergebenen Nährwerte werden formatiert und in einem nicht bearbeitbaren Textfeld dargestellt.
 * @author Lea Eichler
 * @version 1.1
 */
public class getProduktinformationen extends JFrame {

    /**
     * Erstellt und öffnet ein Informationsfenster für ein Produkt.
     * 
     * @param produktName Name des Produkts, der als Fenstertitel verwendet wird
     * @param naehrwerte Nährwertinformationen, die im Fenster angezeigt werden
     */
    public getProduktinformationen(String produktName, String naehrwerte) {
        setTitle(produktName);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/Logo_Schobbow.png")).getImage());

        String NaerwerteFormatiert = naehrwerte.replace(", ", "\n");
        JTextArea textArea = new JTextArea(NaerwerteFormatiert);
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        JScrollPane scrollPane= new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}