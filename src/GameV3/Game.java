package GameV3;

import GameV3.monster.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * המחלקה הראשית של המשחק
 */
public class Game extends JFrame {
    // שם השחקן - סטטי כדי לאפשר גישה מכל המחלקות
    public static String playerName;
    private static Game instance;
    
    // השחקן
    private final Player player;
    
    // הרוחות
    private final Red_Ghost redGhost;
    private final Blue_Ghost blueGhost;
    private final Pink_Ghost pinkGhost;
    private final Orange_Ghost orangeGhost;
    
    // חלון המשחק
    private final JPanel gamePanel;
    private final JLabel scoreLabel;
    private final JLabel livesLabel;
    
    /**
     * יוצר משחק חדש
     */
    public Game() {
        // הגדרות חלון
        setTitle("פקמן");
        setSize(520, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // יצירת פאנל המשחק
        gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gamePanel.setBackground(Color.BLACK);
        setContentPane(gamePanel);
        
        // יצירת תוויות ניקוד וחיים
        scoreLabel = new JLabel("ניקוד: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(10, 10, 100, 20);
        gamePanel.add(scoreLabel);
        
        livesLabel = new JLabel("חיים: 3");
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setBounds(420, 10, 100, 20);
        gamePanel.add(livesLabel);
        
        // יצירת השחקן
        player = new Player(this);
        gamePanel.add(player);
        
        // יצירת הרוחות
        redGhost = new Red_Ghost(player);
        blueGhost = new Blue_Ghost(player);
        pinkGhost = new Pink_Ghost(player);
        orangeGhost = new Orange_Ghost(player);
        
        gamePanel.add(redGhost);
        gamePanel.add(blueGhost);
        gamePanel.add(pinkGhost);
        gamePanel.add(orangeGhost);
        
        // יצירת המפה
        createMap();
        
        // הוספת מאזין מקשים
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        player.moveRight();
                        break;
                    case KeyEvent.VK_LEFT:
                        player.moveLeft();
                        break;
                    case KeyEvent.VK_UP:
                        player.moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                        player.moveDown();
                        break;
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                player.stop();
            }
        });
        
        // התחלת תנועת השחקנים
        startGameThreads();
    }
    
    /**
     * יוצר את מפת המשחק
     */
    private void createMap() {
        for (int row = 0; row < D_Map.D_Map.length; row++) {
            for (int col = 0; col < D_Map.D_Map[0].length; col++) {
                if (D_Map.D_Map[row][col] == 1) {
                    // קיר
                    JPanel wall = new JPanel();
                    wall.setBounds(col * 20, row * 20, 20, 20);
                    wall.setBackground(Color.BLUE);
                    gamePanel.add(wall);
                } else if (D_Map.D_Map[row][col] == 0) {
                    // נקודה
                    JPanel dot = new JPanel();
                    dot.setBounds(col * 20 + 8, row * 20 + 8, 4, 4);
                    dot.setBackground(Color.WHITE);
                    gamePanel.add(dot);
                }
            }
        }
    }
    
    /**
     * מתחיל את תנועת השחקנים
     */
    private void startGameThreads() {
        // התחלת תנועת השחקן
        new Thread(player).start();
        
        // התחלת תנועת הרוחות
        new Thread(redGhost).start();
        new Thread(blueGhost).start();
        new Thread(pinkGhost).start();
        new Thread(orangeGhost).start();
    }
    
    /**
     * מאפס את מיקום הרוחות
     */
    public void resetGhostPositions() {
        redGhost.setBounds(260, 200, 20, 20);
        blueGhost.setBounds(260, 280, 20, 20);
        pinkGhost.setBounds(280, 280, 20, 20);
        orangeGhost.setBounds(300, 280, 20, 20);
    }
    
    /**
     * מעדכן את תצוגת הניקוד
     */
    public void updateScore(int score) {
        scoreLabel.setText("ניקוד: " + score);
    }
    
    /**
     * מעדכן את תצוגת החיים
     */
    public void updateLives(int lives) {
        livesLabel.setText("חיים: " + lives);
    }
    
    /**
     * מסיים את המשחק בניצחון
     */
    public void gameWon() {
        dispose();
        new GameOverScreen(player.getScore()).setVisible(true);
    }
    
    /**
     * מסיים את המשחק בהפסד
     */
    public void gameOver() {
        dispose();
        new GameOverScreen(player.getScore()).setVisible(true);
    }
    
    /**
     * מתחיל משחק חדש
     */
    public static void startGame() {
        SwingUtilities.invokeLater(() -> {
            if (instance != null) {
                instance.dispose();
            }
            instance = new Game();
            instance.setVisible(true);
        });
    }
    
    /**
     * הפונקציה הראשית
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StartScreen().setVisible(true);
        });
    }
}
