package GameV3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

public class StartScreen extends JFrame {
    private String playerName;
    private JTextField nameField;
    private Timer animationTimer;
    private int pacmanAngle = 0;
    private boolean pacmanMouthOpen = true;
    private ArrayList<Point> dots;
    private final Color BACKGROUND_COLOR = new Color(0, 0, 40); // כחול כהה
    private final Color PACMAN_COLOR = new Color(255, 255, 0); // צהוב
    private JPanel gamePreviewPanel;
    private float alpha = 0f;
    private Timer fadeTimer;

    public StartScreen() {
        setTitle("Pac-Man - מסך פתיחה");
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
                for (Point dot : dots) {
                    g2d.fillOval(dot.x, dot.y, 6, 6);
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
        JLabel titleLabel = new JLabel("Pac-Man");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(PACMAN_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // תווית משנה
        JLabel subtitleLabel = new JLabel(" V3 ברוכים הבאים למשחק");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // שדה טקסט מעוצב
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(250, 40));
        nameField.setFont(new Font("Arial", Font.PLAIN, 18));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PACMAN_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // הוספת מאזין ללחיצה על ENTER בשדה הטקסט
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String name = nameField.getText().trim();
                    if (!name.isEmpty()) {
                        dispose();  // סגירת חלון הפתיחה
                        new Game().setVisible(true);
                    }
                }
            }
        });

        // תווית לשם משתמש
        JLabel nameLabel = new JLabel("הכנס את שמך:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // כפתור התחל מעוצב
        JButton startButton = new JButton("התחל משחק");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setForeground(Color.BLACK);
        startButton.setBackground(PACMAN_COLOR);
        startButton.setMaximumSize(new Dimension(200, 50));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        
        // הוספת מאזין ללחיצה על ENTER בכפתור
        startButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String name = nameField.getText().trim();
                    if (!name.isEmpty()) {
                        dispose();  // סגירת חלון הפתיחה
                        new Game().setVisible(true);
                    }
                }
            }
        });

        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                startButton.setBackground(new Color(255, 255, 128));
            }
            public void mouseExited(MouseEvent evt) {
                startButton.setBackground(PACMAN_COLOR);
            }
        });
        
        startButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                dispose();  // סגירת חלון הפתיחה
                new Game().setVisible(true);
            }
        });
        
        // הוספת כל הרכיבים לפאנל הטופס
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(subtitleLabel);
        formPanel.add(Box.createVerticalStrut(40));
        formPanel.add(nameLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(40));
        formPanel.add(startButton);
        
        // הוספת הפאנלים לחלון
        add(gamePreviewPanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.CENTER);
        
        // יצירת נקודות רקע
        dots = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dots.add(new Point(
                (int) (Math.random() * getWidth()),
                (int) (Math.random() * getHeight())
            ));
        }
        
        // אנימציה של פקמן
        animationTimer = new Timer(100, e -> {
            pacmanAngle = (pacmanAngle + 5) % 360;
            pacmanMouthOpen = !pacmanMouthOpen;
            
            // הזזת הנקודות
            for (Point dot : dots) {
                dot.x = (dot.x + 2) % getWidth();
            }
            
            gamePreviewPanel.repaint();
        });
        animationTimer.start();
        
        // טיימר לאפקט הדהייה
        fadeTimer = new Timer(50, e -> {
            alpha -= 0.05f;
            if (alpha <= 0) {
                fadeTimer.stop();
                dispose();
                new Game().setVisible(true);
            }
            formPanel.repaint();
        });
    }
    
    private void fadeOut() {
        fadeTimer.start();
    }
    
    private void startGame() {
        animationTimer.stop();
        new Game().setVisible(true);
    }
    
    // פונקציה לבדיקת תקינות והתחלת המשחק
    private void tryStartGame() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(StartScreen.this,
                "אנא הכנס שם משתמש",
                "שגיאה",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        playerName = nameField.getText().trim();
        fadeOut();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartScreen startScreen = new StartScreen();
            startScreen.setVisible(true);
        });
    }
}
