import javax.swing.Timer;

long lastTime = System.nanoTime();

void main() {
    Player player = new Player();
    SaveFile saveFile = new SaveFile();
    Music music = new Music();
    GameManager gameManager = new GameManager(player, music);

    // play silent sound to prevent sound lag afterwards
    gameManager.mutedSound.playSound();

    // start music
    music.startMusic();

    // save file loading in
    try {
        saveFile.load(player, gameManager);
    } catch (IOException e) {
        e.printStackTrace();
    }

    Rebirth rebirth = new Rebirth();
    MainScreen mainScreen = new MainScreen(player, gameManager, saveFile, rebirth);

    // timer
    Timer timer = new Timer(16, e -> {
        long currentTime = System.nanoTime();
        player.deltaTime = (currentTime - lastTime) / 1000000000.0;
        lastTime = currentTime;
        if (player.time >= 0 && player.quartile <= 4) {
            gameManager.update();
            mainScreen.screenUpdate();
        } else if (!player.bsaTriggered) { // render and trigger bsaReport when time runs out
            BsaPopup bsaPopup = new BsaPopup(mainScreen, gameManager, player, saveFile);
            bsaPopup.triggerReport();
            player.bsaTriggered = true;
        }
    });

    timer.start();

}
