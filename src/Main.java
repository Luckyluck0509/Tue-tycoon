import javax.swing.Timer;

long lastTime = System.nanoTime();

void main() {
    Player p = new Player();
    SaveFile save = new SaveFile();
    GameManager gm = new GameManager(p);

    // save file loading in
    try {
        save.load(p, gm);
    } catch (IOException e) {
        e.printStackTrace();
    }

    Rebirth r = new Rebirth();
    MainScreen ms = new MainScreen(p, gm, save, r);

    // timer
    Timer timer = new Timer(16, e -> {
        long currentTime = System.nanoTime();
        gm.deltaTime = (currentTime - lastTime) / 1000000000.0;
        lastTime = currentTime;
        if (gm.time >= 0 && gm.quartile <= 4) {
            ms.update();
        }
    });

    timer.start();

}
