package GameV2;

import GameV2.monster.Blue_Ghost;
import GameV2.monster.Orange_Ghost;
import GameV2.monster.Pink_Ghost;
import GameV2.monster.Red_Ghost;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game {
    static int score = 0;
    static int allScore = 0;
    static JPanel[][] coins;
    static String playerName = "שחקן";  // ערך ברירת מחדל

    public static Red_Ghost red_ghost;
    public static Pink_Ghost pink_ghost;
    public static Orange_Ghost orange_ghost;
    public static Blue_Ghost blue_ghost;

    static Player player;
    private static JPanel livesPanel;
    private static JLabel[] lifeLabels;
    private static ImageIcon pacmanLifeIcon;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartScreen startScreen = new StartScreen();
            startScreen.setVisible(true);
        });
    }

    public static void startGame() {
        int[][] map = D_Map.D_Map;

        JFrame jFrame = new JFrame("Pac-Man");

        int winWidth = map[0].length * 20;
        int winHeight = map.length * 20;

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(winWidth, winHeight));

        JPanel mapjpanel = new JPanel(new GridLayout(map.length, map[0].length));
        mapjpanel.setBounds(0, 0, winWidth, winHeight);

        coins = new JPanel[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                JPanel jPanel1 = new JPanel();

                if (map[i][j] == 0) {
                    jPanel1.setBackground(Color.BLACK);
                    RoundCoin coin = new RoundCoin();
                    jPanel1.add(coin);
                    coins[i][j] = jPanel1;
                    allScore++;
                }
                if (map[i][j] == 1) {
                    jPanel1.setBackground(Color.BLUE);
                }
                if (map[i][j] == 2) {
                    jPanel1.setBackground(Color.YELLOW);

                }
                if (map[i][j] == 3) {
                    jPanel1.setBackground(Color.BLACK);

                }
                if (map[i][j] == 5) {
                    jPanel1.setBackground(Color.BLACK);

                }
                mapjpanel.add(jPanel1);

            }

        }

        player = new Player();
        layeredPane.add(player, JLayeredPane.PALETTE_LAYER);

        // יצירת והוספת הרוחות
        red_ghost = new Red_Ghost();
        blue_ghost = new Blue_Ghost();
        pink_ghost = new Pink_Ghost();
        orange_ghost = new Orange_Ghost();

        // הוספת הרוחות ללוח המשחק
        layeredPane.add(red_ghost, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(blue_ghost, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(pink_ghost, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(orange_ghost, JLayeredPane.PALETTE_LAYER);

        // הוספת שאר הרכיבים
        layeredPane.add(mapjpanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(messages.scoreLable, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(messages.lives, JLayeredPane.PALETTE_LAYER);

        // יצירת פאנל עליון
        createTopPanel(layeredPane);

        // הוספת תווית שם שחקן
        JLabel playerLabel = new JLabel("שחקן: " + playerName);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerLabel.setBounds(10, 10, 200, 30);
        playerLabel.setForeground(Color.BLACK);
        playerLabel.setLocation(200, 599);
        layeredPane.add(playerLabel, JLayeredPane.PALETTE_LAYER);

        jFrame.add(layeredPane);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jFrame.addKeyListener(new MyKeyListenerV2(player));
        jFrame.setFocusable(true);
        jFrame.requestFocus();
    }

    private static void createTopPanel(JLayeredPane layeredPane) {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        topPanel.setPreferredSize(new Dimension(layeredPane.getWidth(), 30));
        topPanel.setLayout(new BorderLayout());

        // יצירת פאנל לתצוגת חיים עם תמונות פקמן
        livesPanel = new JPanel();
        livesPanel.setBackground(Color.BLACK);
        livesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        // טעינת תמונת פקמן לחיים
        try {
            Image pacmanImage = ImageIO.read(new File("C:/Users/JBH/IdeaProjects/zevi/src/GameV2/img/pacman.jpg"));
            // שינוי גודל התמונה ל-20x20 פיקסלים
            Image scaledImage = pacmanImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            pacmanLifeIcon = new ImageIcon(scaledImage);
        } catch (IOException e) {
            System.out.println("שגיאה בטעינת תמונת פקמן: " + e);
            // במקרה של שגיאה, נשתמש בעיגול צהוב פשוט
            pacmanLifeIcon = createFallbackLifeIcon();
        }

        // יצירת מערך של תוויות לחיים
        lifeLabels = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            lifeLabels[i] = new JLabel(pacmanLifeIcon);
            livesPanel.add(lifeLabels[i]);
        }

        topPanel.add(livesPanel, BorderLayout.WEST);
        layeredPane.add(topPanel, JLayeredPane.DEFAULT_LAYER);
    }

    // יצירת אייקון חלופי במקרה שטעינת התמונה נכשלת
    private static ImageIcon createFallbackLifeIcon() {
        BufferedImage fallbackImage = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = fallbackImage.createGraphics();
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(0, 0, 20, 20);
        g2d.dispose();
        return new ImageIcon(fallbackImage);
    }

    // עדכון תצוגת החיים
    public static void updateLives(int currentLives) {
        for (int i = 0; i < lifeLabels.length; i++) {
            lifeLabels[i].setVisible(i < currentLives);
        }
        livesPanel.revalidate();
        livesPanel.repaint();
    }

    // מחלקה פנימית ליצירת מטבעות עגולים
    private static class RoundCoin extends JPanel {
        public RoundCoin() {
            setOpaque(false);
            setPreferredSize(new Dimension(8, 8));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(0, 0, 8, 8);
        }
    }
}
