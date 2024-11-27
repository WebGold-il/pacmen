package GameV3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

public class GameOverScreen extends JFrame {
    private Timer animationTimer;
    private int pacmanAngle = 0;
    private boolean pacmanMouthOpen = true;
    private ArrayList<Point> dots;
    private final Color BACKGROUND_COLOR = new Color(0, 0, 40); // כחול כהה
    private final Color PACMAN_COLOR = new Color(255, 255, 0); // צהוב
    private JPanel gamePreviewPanel;
    private float alpha = 0f;
    private Timer fadeTimer;
    private final int finalScore;

    public GameOverScreen(int score) {
        this.finalScore = score;
        setTitle("Pac-Man - סיום משחק");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // יצירת פאנל שקוף לתצוגה מקדימה של המשחק
        gamePreviewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // צביעת הרקע
                g2d.setColor(BACKGROUND_COLOR);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // הגדרת שקיפות
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                
                // ציור נקודות
                g2d.setColor(Color.WHITE);
                if (dots != null) {
                    for (Point dot : dots) {
                        g2d.fillOval(dot.x, dot.y, 6, 6);
                    }
                }
                
                // ציור פקמן מונפש
                g2d.setColor(PACMAN_COLOR);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (pacmanMouthOpen) {
                    g2d.fill(new Arc2D.Double(350, 250, 50, 50, pacmanAngle, 300, Arc2D.PIE));
                } else {
                    g2d.fillOval(350, 250, 50, 50);
                }
            }
        };
        gamePreviewPanel.setOpaque(false);
        
        // יצירת פאנל שקוף לטופס
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f));
                g2d.setColor(new Color(0, 0, 0, 200));
                g2d.fillRoundRect(50, 50, getWidth() - 100, getHeight() - 100, 20, 20);
            }
        };
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        
        // כותרת מעוצבת
        JLabel titleLabel = new JLabel("Game Over");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(PACMAN_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // תווית הניקוד
        JLabel scoreLabel = new JLabel("הניקוד הסופי שלך: " + finalScore);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // כפתורים
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton restartButton = createStyledButton("משחק חדש");
        restartButton.addActionListener(e -> {
            dispose();  // סגירת מסך סיום המשחק
            new Game().setVisible(true);
        });

        JButton exitButton = createStyledButton("יציאה");
        exitButton.addActionListener(e -> {
            dispose();  // סגירת מסך סיום המשחק
            System.exit(0);
        });

        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // הוספת רכיבים לפאנל הטופס
        formPanel.add(Box.createVerticalGlue());
        formPanel.add(titleLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        formPanel.add(scoreLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalGlue());

        // הוספת הפאנלים למסך
        add(gamePreviewPanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.CENTER);
        
        // יצירת נקודות רקע
        createDots();
        
        // הפעלת אנימציה
        startAnimation();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(BACKGROUND_COLOR);
        button.setBackground(PACMAN_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 255, 128)); // צהוב בהיר יותר
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PACMAN_COLOR);
            }
        });
        
        return button;
    }

    private void createDots() {
        dots = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dots.add(new Point(
                (int) (Math.random() * getWidth()),
                (int) (Math.random() * getHeight())
            ));
        }
    }

    private void startAnimation() {
        animationTimer = new Timer(100, e -> {
            pacmanAngle = (pacmanAngle + 5) % 360;
            pacmanMouthOpen = !pacmanMouthOpen;
            gamePreviewPanel.repaint();
        });
        animationTimer.start();
    }
}
