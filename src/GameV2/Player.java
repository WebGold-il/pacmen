package GameV2;

import javax.swing.*;
import java.awt.*;

/**
 * מחלקת השחקן (פקמן)
 * אחראית על:
 * - תנועת השחקן
 * - התנגשויות עם רוחות
 * - איסוף מטבעות
 * - ניהול חיים
 */
public class Player extends JPanel {
    // תמונת השחקן הנוכחית
    private ImageIcon gifIcon;
    // מימדי תמונת השחקן
    private int gifWidth;
    private int gifHeight;
    // מספר החיים הנוכחי של השחקן
    private int lives;

    /**
     * בנאי המחלקה - יוצר שחקן חדש
     * מגדיר:
     * - מיקום התחלתי
     * - תמונה התחלתית
     * - מספר חיים התחלתי
     */
    public Player() {
        // הגדרת מיקום וגודל השחקן במסך
        setBounds(260, 460, 40, 40);
        // הגדרת רקע שקוף
        setOpaque(false);
        // טעינת תמונת ברירת המחדל
        loadImage("C:\\Users\\JBH\\IdeaProjects\\zevi\\src\\GameV2\\img\\pacman.jpg");
        // הגדרת גודל השחקן
        gifWidth = 20;
        gifHeight = 20;
        // התחלה עם 3 חיים
        lives = 3;
    }

    /**
     * טוען תמונה חדשה לשחקן
     * משמש בעיקר לשינוי כיוון התנועה
     * @param path הנתיב לקובץ התמונה
     */
    public void loadImage(String path) {
        gifIcon = new ImageIcon(path);
    }

    /**
     * מחזיר את מספר החיים הנוכחי של השחקן
     * @return מספר החיים הנותרים
     */
    public int getLives() {
        return lives;
    }

    /**
     * מנסה להזיז את השחקן למיקום חדש
     * כולל בדיקות:
     * - התנגשות עם קירות
     * - מעבר דרך מנהרה
     * - התנגשות עם רוחות
     * - איסוף מטבעות
     * 
     * @param newX קואורדינטת X חדשה
     * @param newY קואורדינטת Y חדשה
     * @return האם התנועה הצליחה
     */
    public boolean tryMove(int newX, int newY) {
        // המרת הקואורדינטות למיקום במפה
        int mapX = newX / 20;
        int mapY = newY / 20;

        // בדיקת מעבר דרך המנהרה - צד ימין
        if (newX == 520 && newY == 280) {
            setLocation(0, 280);  // העברה לצד שמאל
            return true;
        }
        // בדיקת מעבר דרך המנהרה - צד שמאל
        if (newX == -20 && newY == 280) {
            setLocation(520, 280);  // העברה לצד ימין
            return true;
        }

        // בדיקה האם המיקום החדש חוקי (בתוך המפה ולא בקיר)
        if (mapX >= 0 && mapX < D_Map.D_Map[0].length &&
            mapY >= 0 && mapY < D_Map.D_Map.length &&
            D_Map.D_Map[mapY][mapX] != 1) {
            
            // עדכון מיקום השחקן
            setLocation(newX, newY);
            
            // בדיקת התנגשות עם רוחות
            if (checkGhostCollision()) {
                loseLife();
            }
            
            // בדיקה ואיסוף מטבעות
            checkCoinCollection(mapX, mapY);
            return true;
        }
        return false;
    }

    /**
     * בודק ואוסף מטבע במיקום הנוכחי
     * אם יש מטבע:
     * - מסתיר את המטבע אבל משאיר את הפאנל השחור
     * - מעדכן את הניקוד
     * - בודק אם נאספו כל המטבעות
     * 
     * @param mapX מיקום X במפה
     * @param mapY מיקום Y במפה
     */
    private void checkCoinCollection(int mapX, int mapY) {
        if (Game.coins[mapY][mapX] != null) {
            // מסירים את המטבע מהפאנל אבל משאירים את הפאנל השחור
            Game.coins[mapY][mapX].removeAll();
            Game.coins[mapY][mapX].revalidate();
            Game.coins[mapY][mapX].repaint();
            Game.coins[mapY][mapX] = null;
            
            // עדכון הניקוד
            Game.score++;
            messages.scoreLable.setText("ניקוד:" + Game.score);
            
            // בדיקה אם זה היה המטבע האחרון
            if (Game.score == Game.allScore) {
                messages.victory();
            }
        }
    }

    /**
     * מאפס את מיקומי השחקן והרוחות למיקום ההתחלתי
     * נקרא כאשר השחקן מאבד חיים
     */
    private void resetPositions() {
        // החזרת השחקן למיקום ההתחלתי
        setLocation(260, 460);
        
        // החזרת כל הרוחות למיקום ההתחלתי שלהן
        Game.red_ghost.setLocation(260, 200);
        Game.blue_ghost.setLocation(260, 280);
        Game.pink_ghost.setLocation(280, 280);
        Game.orange_ghost.setLocation(300, 280);
    }

    /**
     * בודק התנגשות בין השחקן לרוחות הרפאים
     * @return true אם יש התנגשות, false אחרת
     */
    private boolean checkGhostCollision() {
        // יצירת מלבן סביב השחקן לבדיקת התנגשויות
        Rectangle playerBounds = new Rectangle(getX(), getY(), gifWidth, gifHeight);

        // יצירת מלבנים סביב כל אחת מהרוחות
        Rectangle redGhostBounds = new Rectangle(Game.red_ghost.getX(), Game.red_ghost.getY(), 20, 20);
        Rectangle blueGhostBounds = new Rectangle(Game.blue_ghost.getX(), Game.blue_ghost.getY(), 20, 20);
        Rectangle pinkGhostBounds = new Rectangle(Game.pink_ghost.getX(), Game.pink_ghost.getY(), 20, 20);
        Rectangle orangeGhostBounds = new Rectangle(Game.orange_ghost.getX(), Game.orange_ghost.getY(), 20, 20);

        // בדיקת חיתוך בין מלבן השחקן לכל אחת מהרוחות
        return playerBounds.intersects(redGhostBounds) ||
               playerBounds.intersects(blueGhostBounds) ||
               playerBounds.intersects(pinkGhostBounds) ||
               playerBounds.intersects(orangeGhostBounds);
    }

    /**
     * מטפל באיבוד חיים של השחקן
     * - מוריד חיים
     * - מעדכן תצוגה
     * - מאפס מיקומים
     * - בודק אם המשחק נגמר
     */
    public void loseLife() {
        // הורדת חיים
        lives--;
        // עדכון תצוגת החיים
        messages.lives.setText("חיים: " + lives);
        // איפוס מיקומים
        resetPositions();
        
        // בדיקה אם נגמרו החיים
        if (lives <= 0) {
            // הצגת הודעת סיום משחק עם הניקוד הסופי
            JOptionPane.showMessageDialog(null, "הפסדת! הניקוד הסופי שלך הוא:" + Game.score);
            System.exit(0);
        }
    }

    /**
     * מצייר את השחקן על המסך
     * נקרא אוטומטית על ידי מערכת החלונות
     * @param g אובייקט הגרפיקה לציור
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // ציור תמונת השחקן אם היא קיימת
        if (gifIcon != null) {
            g.drawImage(gifIcon.getImage(), 0, 0, gifWidth, gifHeight, this);
        }
    }
}
