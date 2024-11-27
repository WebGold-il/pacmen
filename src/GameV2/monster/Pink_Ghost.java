package GameV2.monster;

import GameV2.D_Map;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Pink_Ghost extends JPanel implements Runnable {

    private Image image;
    private int dx = 0;
    private int dy = 0;
    private Random random = new Random();
    private Thread moveThread;
    private boolean isMoving = true;

    public Pink_Ghost() {
        setBounds(280, 280, 20, 20);
        setOpaque(false);
        loadImage();
        startMoving();
    }

    public void loadImage() {
        try {
            image = ImageIO.read(new File("C:/Users/JBH/IdeaProjects/zevi/src/GameV2/img/PinkGhost.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void startMoving() {
        moveThread = new Thread(this);
        moveThread.start();
    }

    @Override
    public void run() {
        while (isMoving) {
            move();
            try {
                Thread.sleep(180); // מהירות התנועה - מהירה יותר
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void move() {
        if (dx == 0 && dy == 0 || random.nextInt(20) == 0) { // פחות סיכוי לשנות כיוון
            chooseNewDirection();
        }

        int newX = getX() + dx * 20;
        int newY = getY() + dy * 20;

        int mapX = newX / 20;
        int mapY = newY / 20;

        if (isValidMove(mapX, mapY)) {
            setLocation(newX, newY);
        } else {
            chooseNewDirection();
        }
    }

    private boolean isValidMove(int mapX, int mapY) {
        return mapX >= 0 && mapX < D_Map.D_Map[0].length &&
               mapY >= 0 && mapY < D_Map.D_Map.length &&
               D_Map.D_Map[mapY][mapX] != 1;
    }

    private void chooseNewDirection() {
        int direction = random.nextInt(4);
        switch (direction) {
            case 0:
                dx = 0;
                dy = -1;
                break;
            case 1:
                dx = 0;
                dy = 1;
                break;
            case 2:
                dx = -1;
                dy = 0;
                break;
            case 3:
                dx = 1;
                dy = 0;
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void stopMoving() {
        isMoving = false;
        if (moveThread != null) {
            moveThread.interrupt();
        }
    }
}
