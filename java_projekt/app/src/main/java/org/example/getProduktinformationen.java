package org.example;

import javax.swing.*;
import java.awt.*;

public class getProduktinformationen extends JFrame {

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