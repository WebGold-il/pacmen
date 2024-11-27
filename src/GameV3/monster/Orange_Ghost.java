package GameV3.monster;

import GameV3.Player;
import java.awt.Color;

/**
 * הרוח הכתומה - נעה בצורה מעגלית
 */
public class Orange_Ghost extends Ghost {
    private int moveCounter = 0;
    
    public Orange_Ghost(Player player) {
        super(player, 300, 280, "src/GameV3/img/orange_ghost.png", Color.ORANGE);
        this.speed = 1;
    }

    @Override
    protected void chooseNewDirection() {
        // הרוח הכתומה נעה בצורה מעגלית
        moveCounter = (moveCounter + 1) % 4;
        
        switch (moveCounter) {
            case 0: // ימינה
                dx = speed;
                dy = 0;
                break;
            case 1: // למטה
                dx = 0;
                dy = speed;
                break;
            case 2: // שמאלה
                dx = -speed;
                dy = 0;
                break;
            case 3: // למעלה
                dx = 0;
                dy = -speed;
                break;
        }
        
        // אם אי אפשר לזוז בכיוון הרצוי, בחירת כיוון אקראי
        if (!canMove(getX() + dx, getY() + dy)) {
            super.chooseNewDirection();
        }
    }
}