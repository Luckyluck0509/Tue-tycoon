public class GameManager {
    Player player;

    double deltaTime = 0;
    public int quartile = 1;

    public GameManager(Player p) {
        this.player = p;
    }

    public void StudyButtonPressed() {
        player.knowledge += player.kpc * player.multiplier;
    }

    void update() {
        double currTime = System.nanoTime();
        double previousTime = 0;
        this.deltaTime = currTime - previousTime;
        previousTime = currTime;
    }
}
