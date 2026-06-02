package org.example;

import java.awt.*;

/**
 * Stellt zentrale Schriftraben für die Benutzeroberfläche bereit.
 * 
 * Durch diese Klassen können mehrere UI-Komponenten dieselben Schriftarten verwenden,
 * ohne dass sie in jeder Klasse einzeln neu definiert werden müssen.
 */
public class UIStyles {

  /** Schriftart für Buttons */
  public static final Font BUTTON_FONT = new Font("Consolas", Font.PLAIN, 14);
  /** Schriftart für ComboBoxen */
  public static final Font COMBO_FONT = new Font("Consolas", Font.PLAIN, 14);
  /** Schriftart für Texteingabefelder */
  public static final Font TEXT_FIELD_FONT = new Font("Consolas", Font.PLAIN, 14);
  /** Schriftart für Listen */
  public static final Font LIST_FONT = new Font("Consolas", Font.PLAIN, 14);
}