package GameV2.monster;

import GameV2.D_Map;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * מחלקת הרוח האדומה
 * מאפיינים:
 * - תנועה אגרסיבית יותר
 * - מהירות תנועה גבוהה
 * - בדיקת התנגשויות עם קירות
 */
public class Red_Ghost extends JPanel implements Runnable {
    // תמונת הרוח
    private BufferedImage image;
    // האם הרוח בתנועה
    private boolean isMoving;
    // אובייקט ליצירת מספרים אקראיים
    private Random random;
    // כיוון התנועה הנוכחי
    private int currentDirection;

    /**
     * בנאי המחלקה
     * מגדיר:
     * - מיקום התחלתי
     * - גודל הרוח
     * - טעינת תמונה
     * - אתחול משתני תנועה
     */
    public Red_Ghost() {
        // הגדרת מיקום וגודל הרוח
        setBounds(260, 200, 20, 20);
        // הגדרת רקע שקוף
        setOpaque(false);
        // טעינת תמונת הרוח
        loadImage();
        // אתחול אובייקט למספרים אקראיים
        random = new Random();
        // הגדרת כיוון התחלתי אקראי
        currentDirection = random.nextInt(4);
        // הגדרת הרוח כנעה
        isMoving = true;
        // התחלת תהליך התנועה
        new Thread(this).start();
    }

    /**
     * טוען את תמונת הרוח מקובץ
     */
    public void loadImage() {
        try {
            image = ImageIO.read(new File("C:/Users/JBH/IdeaProjects/zevi/src/GameV2/img/RedGhost.png"));
        } catch (IOException e) {
            System.out.println("Failed to load red ghost image: " + e.getMessage());
        }
    }

    /**
     * מזיז את הרוח בכיוון הנוכחי
     * בודק התנגשויות עם קירות ומשנה כיוון במקרה הצורך
     * הרוח האדומה מתנהגת באופן אגרסיבי יותר ומשנה כיוון בתדירות גבוהה יותר
     */
    private void move() {
        // שמירת המיקום הנוכחי
        int currentX = getX();
        int currentY = getY();
        // מיקום חדש אפשרי
        int newX = currentX;
        int newY = currentY;

        // שינוי כיוון אקראי בסיכוי של 20%
        if (random.nextInt(5) == 0) {
            currentDirection = random.nextInt(4);
        }

        // חישוב המיקום החדש לפי הכיוון
        switch (currentDirection) {
            case 0: // למעלה
                newY -= 20;
                break;
            case 1: // ימינה
                newX += 20;
                break;
            case 2: // למטה
                newY += 20;
                break;
            case 3: // שמאלה
                newX -= 20;
                break;
        }

        // בדיקת מעבר דרך המנהרה - צד ימין
        if (newX == 520 && newY == 280) {
            setLocation(0, 280);
            return;
        }
        // בדיקת מעבר דרך המנהרה - צד שמאל
        if (newX == -20 && newY == 280) {
            setLocation(520, 280);
            return;
        }

        // המרת הקואורדינטות למיקום במפה
        int mapX = newX / 20;
        int mapY = newY / 20;

        // בדיקה האם המהלך חוקי
        if (isValidMove(mapX, mapY)) {
            // אם כן, זז למיקום החדש
            setLocation(newX, newY);
        } else {
            // אם לא, בוחר כיוון חדש אקראי
            currentDirection = random.nextInt(4);
        }
    }

    /**
     * בודק האם מהלך חוקי (לא פוגע בקיר)
     * @param mapX קואורדינטת X במפה
     * @param mapY קואורדינטת Y במפה
     * @return האם המהלך חוקי
     */
    private boolean isValidMove(int mapX, int mapY) {
        if (mapX >= 0 && mapX < D_Map.D_Map[0].length &&
            mapY >= 0 && mapY < D_Map.D_Map.length) {
            return D_Map.D_Map[mapY][mapX] != 1;
        }
        return false;
    }

    /**
     * מצייר את הרוח על המסך
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, 20, 20, this);
        }
    }

    /**
     * לולאת התנועה הראשית של הרוח
     * רץ ברקע ומזיז את הרוח כל 150 מילישניות
     * מהירות גבוהה יותר מהרוחות האחרות
     */
    @Override
    public void run() {
        while (isMoving) {
            move();
            try {
                // השהייה בין תנועות - קצרה יותר מהרוחות האחרות
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}