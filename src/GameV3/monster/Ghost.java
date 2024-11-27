package GameV3.monster;

import GameV3.D_Map;
import GameV3.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * מחלקת בסיס לכל הרוחות במשחק
 * כל רוח יורשת ממחלקה זו ומוסיפה את ההתנהגות הייחודית שלה
 */
public class Ghost extends JPanel implements Runnable {
    // תמונת הרוח
    protected BufferedImage image;
    // האם הרוח בתנועה
    protected boolean isMoving = true;
    // אובייקט ליצירת מספרים אקראיים
    protected Random random = new Random();
    // כיוון התנועה הנוכחי
    protected int currentDirection;
    // השחקן שהרוח רודפת אחריו
    protected Player player;
    // שינוי בקואורדינטות X ו-Y
    protected int dx = 0;
    protected int dy = 0;
    // מהירות התנועה של הרוח
    protected int speed = 1;
    // צבע הרוח (לשימוש בזיהוי ויזואלי)
    protected Color ghostColor;

    /**
     * בנאי המחלקה
     * @param player השחקן במשחק
     * @param startX מיקום התחלתי X
     * @param startY מיקום התחלתי Y
     * @param imagePath נתיב לתמונת הרוח
     * @param color צבע הרוח
     */
    public Ghost(Player player, int startX, int startY, String imagePath, Color color) {
        this.player = player;
        this.ghostColor = color;
        
        // הגדרת מיקום וגודל הרוח
        setBounds(startX, startY, 20, 20);
        setOpaque(false);
        
        // טעינת תמונת הרוח
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("שגיאה בטעינת תמונת הרוח: " + imagePath);
            // יצירת תמונה פשוטה במקרה של שגיאה
            image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            g.setColor(color);
            g.fillOval(0, 0, 20, 20);
            g.dispose();
        }
    }

    /**
     * בדיקה האם ניתן לזוז למיקום החדש
     * @param newX קואורדינטת X חדשה
     * @param newY קואורדינטת Y חדשה
     * @return האם התנועה אפשרית
     */
    protected boolean canMove(int newX, int newY) {
        // המרת הקואורדינטות למיקום במפה
        int mapX = newX / 20;
        int mapY = newY / 20;
        
        // בדיקה האם המיקום החדש חוקי
        return mapX >= 0 && mapX < D_Map.D_Map[0].length &&
               mapY >= 0 && mapY < D_Map.D_Map.length &&
               D_Map.D_Map[mapY][mapX] != 1;
    }

    /**
     * בחירת כיוון תנועה חדש
     */
    protected void chooseNewDirection() {
        // בחירת כיוון אקראי (0=למעלה, 1=ימינה, 2=למטה, 3=שמאלה)
        currentDirection = random.nextInt(4);
        
        // עדכון שינוי הקואורדינטות בהתאם לכיוון
        switch (currentDirection) {
            case 0: // למעלה
                dx = 0;
                dy = -speed;
                break;
            case 1: // ימינה
                dx = speed;
                dy = 0;
                break;
            case 2: // למטה
                dx = 0;
                dy = speed;
                break;
            case 3: // שמאלה
                dx = -speed;
                dy = 0;
                break;
        }
    }

    /**
     * הזזת הרוח
     */
    protected void moveGhost() {
        if (!isMoving) return;

        // ניסיון לזוז למיקום החדש
        int newX = getX() + dx;
        int newY = getY() + dy;

        // אם אפשר לזוז למיקום החדש
        if (canMove(newX, newY)) {
            setLocation(newX, newY);
        } else {
            // אם לא, בחירת כיוון חדש
            chooseNewDirection();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void run() {
        while (true) {
            moveGhost();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
