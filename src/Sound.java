import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    private Clip clip;

    public Sound(String path) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(getClass().getResource(path));
            clip = AudioSystem.getClip();
            clip.open(stream);
        } catch (Exception e) {
            System.out.println("Error loading sound: " + path);
            e.printStackTrace();
        }
    }

    public void playSound() {
        if (clip == null) {return;}
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}