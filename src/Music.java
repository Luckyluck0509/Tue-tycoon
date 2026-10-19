import java.util.Random;

public class Music {
    private Sound[] music;
    private Sound currentSong;
    private double musicLength = 0;   // time remaining in the current song
    private int lastIndex = -1;
    private final Random rand = new Random();
    private double gapTime = 10;
    private boolean waiting = false;
    private static final float MUSIC_VOLUME = 0.4f;

    public void startMusic() {
        try {
            music = new Sound[]{
                    new Sound("/Resources/Music/lofi1.wav"),
                    new Sound("/Resources/Music/lofi2.wav"),
                    new Sound("/Resources/Music/lofi3.wav"),
                    new Sound("/Resources/Music/lofi4.wav"),
                    new Sound("/Resources/Music/lofi5.wav"),
                    new Sound("/Resources/Music/lofi6.wav"),
                    new Sound("/Resources/Music/lofi7.wav"),
                    new Sound("/Resources/Music/lofi8.wav"),
                    new Sound("/Resources/Music/lofi9.wav")
            };
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }


        // set volume of all songs to MUSIC_VOLUME
        for (Sound song : music) {
            song.setVolume(MUSIC_VOLUME);
        }

        playNextSong();
    }

    private void playNextSong() {
        // play a random song, not same as currently playing
        int index;
        do {
            index = rand.nextInt(music.length);
        } while (music.length > 1 && index == lastIndex); // music.length > 1 so that it still works if there is only 1 music file
        lastIndex = index;

        currentSong = music[index];
        musicLength = currentSong.getSoundLength();
        currentSong.playSound();
    }

    // call this every tick with the delta time
    public void updateMusic(double deltaTime) {
        if (music == null) {
            return;   // startMusic() hasn't run yet
        }

        // wait 10 seconds between each song
        if (waiting) {
            gapTime -= deltaTime;
            if (gapTime <= 0) {
                waiting = false;
                playNextSong();
            }
            return;
        }

        // update musicLength and play next song if this one is finished
        musicLength -= deltaTime;
        if (musicLength <= 0) {
            playNextSong();
        }
    }
}