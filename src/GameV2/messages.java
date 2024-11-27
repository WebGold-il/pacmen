package GameV2;

import javax.swing.*;
import java.awt.*;

public class messages {
    public static JLabel scoreLable;
    public static JLabel lives;

    static {
        scoreLable = new JLabel("ניקוד: 0");
        scoreLable.setFont(new Font("Ariel", Font.BOLD,20));
        scoreLable.setBounds(10,10,100,30);
        scoreLable.setForeground(Color.BLACK);
        scoreLable.setLocation(430,599);
    }

    static {
        lives = new JLabel("חיים: 3");
        lives.setFont(new Font("Ariel", Font.BOLD,20));
        lives.setBounds(10,10,100,30);
        lives.setForeground(Color.BLACK);
        lives.setLocation(330,599);
    }

    public static void victory(){
        JOptionPane.showMessageDialog(null,"מזל טוב!! הניקוד הסופי שלך הוא:" + Game.score);
        System.exit(0); // מסיים את התוכנית וסוגר את החלון
    }
}
