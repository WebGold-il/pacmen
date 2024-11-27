package GameV2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListenerV2 implements KeyListener {
    private final Player player;

    public MyKeyListenerV2(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newX = player.getX();
        int newY = player.getY();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.loadImage("C:\\Users\\JBH\\IdeaProjects\\zevi\\src\\GameV2\\img\\left.gif");
                newX -= 20;
                break;
            case KeyEvent.VK_RIGHT:
                player.loadImage("C:\\Users\\JBH\\IdeaProjects\\zevi\\src\\GameV2\\img\\right.gif");
                newX += 20;
                break;
            case KeyEvent.VK_UP:
                player.loadImage("C:\\Users\\JBH\\IdeaProjects\\zevi\\src\\GameV2\\img\\up.gif");
                newY -= 20;
                break;
            case KeyEvent.VK_DOWN:
                player.loadImage("C:\\Users\\JBH\\IdeaProjects\\zevi\\src\\GameV2\\img\\down.gif");
                newY += 20;
                break;
        }

        player.tryMove(newX, newY);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}