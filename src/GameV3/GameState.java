package GameV3;

public class GameState {
    private static GameState instance;
    private int score;
    private int lives;
    private boolean victory;

    private GameState() {
        reset();
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
        // Check if score reaches victory condition
        if (score >= 1000) {
            victory = true;
        }
    }

    public int getLives() {
        return lives;
    }

    public void decrementLives() {
        if (lives > 0) {
            lives--;
            if (lives <= 0) {
                victory = false; // Game over condition
            }
        }
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public void reset() {
        score = 0;
        lives = 3;
        victory = false;
    }
}
