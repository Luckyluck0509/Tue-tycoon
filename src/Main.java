import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Main {
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
        } catch (Exception e) {
            gameManager.failSound.playSound();
            System.out.println("Corrupted savefile, please delete it");
            e.printStackTrace();
            System.exit(0);
        }

        // everything that touches Swing is created on the EDT to prevent visual glitches
        SwingUtilities.invokeLater(() -> {
            Rebirth rebirth = new Rebirth();
            MainScreen mainScreen = new MainScreen(player, gameManager, saveFile, rebirth);

            // timer
            Timer timer = new Timer(16, e -> {
                long currentTime = System.nanoTime();
                player.deltaTime = (currentTime - lastTime) / 1000000000.0;

                if (player.time >= 0 && player.quartile <= 4) {
                    gameManager.update();
                    mainScreen.screenUpdate();
                } else if (!player.bsaTriggered) {
                    // render and trigger bsaReport when time runs out
                    BsaPopup bsaPopup = new BsaPopup(mainScreen, gameManager, player, saveFile);
                    bsaPopup.triggerReport();
                    player.bsaTriggered = true;
                }
                // updates music, play next song if this one is finished
                music.updateMusic(player.deltaTime);

                lastTime = currentTime;
            });

            timer.start();
        });
    }
}