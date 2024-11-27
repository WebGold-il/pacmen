package GameV3;

import javax.swing.*;
import java.awt.*;

/**
 * מחלקת השחקן (פקמן)
 */
public class Player extends JPanel implements Runnable {
    // תמונות השחקן לכל כיוון
    private ImageIcon currentImage;
    private final ImageIcon rightImage;
    private final ImageIcon leftImage;
    private final ImageIcon upImage;
    private final ImageIcon downImage;
    
    // מהירות תנועה
    private int dx = 0;
    private int dy = 0;
    private final int SPEED = 2;
    
    // מצב המשחק
    private int lives = 3;
    private int score = 0;
    private boolean isMoving = true;
    
    // המשחק הראשי
    private final Game game;

    /**
     * יוצר שחקן חדש
     */
    public Player(Game game) {
        this.game = game;
        
        // הגדרת מיקום וגודל
        setBounds(260, 460, 20, 20);
        setOpaque(false);
        
        // טעינת תמונות
        String basePath = "src/GameV3/img/";
        rightImage = new ImageIcon(basePath + "pacman_right.png");
        leftImage = new ImageIcon(basePath + "pacman_left.png");
        upImage = new ImageIcon(basePath + "pacman_up.png");
        downImage = new ImageIcon(basePath + "pacman_down.png");
        currentImage = rightImage;
    }

    /**
     * תזוזה ימינה
     */
    public void moveRight() {
        dx = SPEED;
        dy = 0;
        currentImage = rightImage;
    }

    /**
     * תזוזה שמאלה
     */
    public void moveLeft() {
        dx = -SPEED;
        dy = 0;
        currentImage = leftImage;
    }

    /**
     * תזוזה למעלה
     */
    public void moveUp() {
        dx = 0;
        dy = -SPEED;
        currentImage = upImage;
    }

    /**
     * תזוזה למטה
     */
    public void moveDown() {
        dx = 0;
        dy = SPEED;
        currentImage = downImage;
    }

    /**
     * עצירת תנועה
     */
    public void stop() {
        dx = 0;
        dy = 0;
    }

    /**
     * בדיקה האם ניתן לזוז למיקום החדש
     */
    private boolean canMove(int newX, int newY) {
        // המרה למיקום במפה
        int mapX = newX / 20;
        int mapY = newY / 20;
        
        // בדיקת גבולות והתנגשות עם קירות
        return mapX >= 0 && mapX < D_Map.D_Map[0].length &&
               mapY >= 0 && mapY < D_Map.D_Map.length &&
               D_Map.D_Map[mapY][mapX] != 1;
    }

    /**
     * איסוף נקודות
     */
    private void collectDots() {
        int mapX = getX() / 20;
        int mapY = getY() / 20;
        
        if (D_Map.D_Map[mapY][mapX] == 0) {
            D_Map.D_Map[mapY][mapX] = 2; // 2 = נקודה שנאספה
            score += 10;
            
            // בדיקת ניצחון
            if (score >= 2000) {
                game.gameWon();
            }
        }
    }

    /**
     * איבוד חיים
     */
    public void loseLife() {
        lives--;
        if (lives <= 0) {
            game.gameOver();
        } else {
            // חזרה למיקום ההתחלתי
            setBounds(260, 460, 20, 20);
            game.resetGhostPositions();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentImage != null) {
            currentImage.paintIcon(this, g, 0, 0);
        }
    }

    @Override
    public void run() {
        while (isMoving) {
            // חישוב המיקום החדש
            int newX = getX() + dx;
            int newY = getY() + dy;
            
            // תזוזה אם אפשר
            if (canMove(newX, newY)) {
                setLocation(newX, newY);
                collectDots();
            }
            
            // השהייה קצרה
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Getters
    public int getLives() { return lives; }
    public int getScore() { return score; }
}
