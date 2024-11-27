package GameV3.monster;

import GameV3.Player;
import java.awt.Color;

/**
 * הרוח הכחולה - מתנהגת בצורה אקראית יותר
 */
public class Blue_Ghost extends Ghost {
    
    public Blue_Ghost(Player player) {
        super(player, 260, 280, "src/GameV3/img/BlueGhost.png", Color.BLUE);
        this.speed = 1;
    }

    @Override
    protected void chooseNewDirection() {
        // הרוח הכחולה מתנהגת בצורה אקראית לחלוטין
        super.chooseNewDirection();
    }
}