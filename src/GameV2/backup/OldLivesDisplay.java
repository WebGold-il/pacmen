package GameV2.backup;

import javax.swing.*;
import java.awt.*;

/**
 * גיבוי של תצוגת החיים המקורית
 * נשמר למקרה שנרצה לחזור לתצוגה הפשוטה של מספר
 */
public class OldLivesDisplay {
    public static JLabel lives;

    static {
        lives = new JLabel("חיים: 3");
        lives.setFont(new Font("Ariel", Font.BOLD,20));
        lives.setBounds(10,10,100,30);
        lives.setForeground(Color.BLACK);
        lives.setLocation(330,599);
    }

    // פונקציה לעדכון מספר החיים
    public static void updateLives(int currentLives) {
        lives.setText("חיים: " + currentLives);
    }
}
