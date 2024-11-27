package GameV3.monster;

import GameV3.Player;
import java.awt.Color;

/**
 * הרוח הוורודה - מנסה לחסום את השחקן
 */
public class Pink_Ghost extends Ghost {
    
    public Pink_Ghost(Player player) {
        super(player, 280, 280, "src/GameV3/img/pink_ghost.png", Color.PINK);
        this.speed = 1;
    }

    @Override
    protected void chooseNewDirection() {
        // הרוח הוורודה מנסה להגיע למיקום שלפני השחקן
        int targetX = player.getX() + 40; // 2 משבצות לפני השחקן
        int targetY = player.getY();
        
        // בחירת הכיוון שיקרב את הרוח למטרה
        if (Math.abs(targetX - getX()) > Math.abs(targetY - getY())) {
            dx = (targetX > getX()) ? speed : -speed;
            dy = 0;
        } else {
            dx = 0;
            dy = (targetY > getY()) ? speed : -speed;
        }
        
        // אם אי אפשר לזוז בכיוון הרצוי, בחירת כיוון אקראי
        if (!canMove(getX() + dx, getY() + dy)) {
            super.chooseNewDirection();
        }
    }
}