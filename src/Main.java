import javax.swing.Timer;

long lastTime = System.nanoTime();

void main() {
    Player p = new Player();
    SaveFile save = new SaveFile();
    GameManager gm = new GameManager(p);
    Rebirth r = new Rebirth();
    MainScreen ms = new MainScreen(p, gm, save, r);

    // save file loading in
    try {
        save.load(p, gm);
    } catch (IOException e) {
        e.printStackTrace();
    }


    // timer
    Timer timer = new Timer(16, e -> {
        long currentTime = System.nanoTime();
        gm.deltaTime = (currentTime - lastTime) / 1000000000.0;
        lastTime = currentTime;

        ms.update();
    });

    timer.start();

}
