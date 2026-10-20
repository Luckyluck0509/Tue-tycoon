import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    private Clip clip;
    private String path;

    public Sound(String path) {
        this.path = path;
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
        if (clip == null) {
            return;
        }
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }


    // calculate sound length for background music
    public float getSoundLength() {
        if (clip == null) {
            return 0;
        }
        return clip.getMicrosecondLength() / 1_000_000f;
    }


    // set the volume of the song to the inputted volume, 0.0 = silent, 1.0 = full volume
    public void setVolume(float volume) {
        if (clip == null || !clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            return;
        }
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        // convert volume to decibels, only what the music file supports
        float dB = (float) (20.0 * Math.log10(Math.max(volume, 0.0001f)));
        dB = Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), dB));
        gain.setValue(dB);
    }
}