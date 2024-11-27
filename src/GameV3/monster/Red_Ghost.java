package GameV3.monster;

import GameV3.Player;
import java.awt.Color;

/**
 * הרוח האדומה - רודפת אחרי השחקן באופן אגרסיבי
 */
public class Red_Ghost extends Ghost {
    
    public Red_Ghost(Player player) {
        super(player, 260, 200, "src/GameV3/img/RedGhost.png", Color.RED);
        this.speed = 2; // מהירות גבוהה יותר
    }

    @Override
    protected void chooseNewDirection() {
        // הרוח האדומה מנסה לרדוף אחרי השחקן
        int playerX = player.getX();
        int playerY = player.getY();
        
        // בחירת הכיוון שיקרב את הרוח לשחקן
        if (Math.abs(playerX - getX()) > Math.abs(playerY - getY())) {
            // תנועה אופקית
            dx = (playerX > getX()) ? speed : -speed;
            dy = 0;
        } else {
            // תנועה אנכית
            dx = 0;
            dy = (playerY > getY()) ? speed : -speed;
        }
        
        // אם אי אפשר לזוז בכיוון הרצוי, בחירת כיוון אקראי
        if (!canMove(getX() + dx, getY() + dy)) {
            super.chooseNewDirection();
        }
    }
}