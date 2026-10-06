import javax.swing.Timer;

long lastTime = System.nanoTime();

void main() {
    Player p = new Player();
    GameManager gm = new GameManager(p);
    MainScreen ms = new MainScreen(p, gm);

    try {
        SaveFile save = new SaveFile();
        save.load(p, gm);
    } catch (IOException e) {
        e.printStackTrace();
    }

    Timer timer = new Timer(16, e -> {
        long currentTime = System.nanoTime();
        gm.deltaTime = (currentTime - lastTime) / 1000000000.0;
        lastTime = currentTime;

        ms.update();
    });

    timer.start();

}
