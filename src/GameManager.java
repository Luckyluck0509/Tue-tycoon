public class GameManager {
    Player player;

    double deltaTime = 0;
    public int quartile = 1;

    public GameManager(Player p) {
        this.player = p;
    }

    public void ButtonPressed() {
        player.knowledge += player.kpc;
    }

    void update() {
        double currTime = System.nanoTime();
        double previousTime = 0;
        double deltaTime = currTime - previousTime;
        previousTime = currTime;
    }
}
