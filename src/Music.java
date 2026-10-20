import java.util.Random;

public class Music {
    private Sound[] music;
    private Sound currentSong;
    private double musicLength = 0;   // time remaining in the current song
    private int lastIndex = -1;
    private final Random rand = new Random();
    private double gapTime = 3;
    private static final double GAP_SECONDS = 7;
    private boolean waiting = true;
    private static final float MUSIC_VOLUME = 0.4f;

    public void startMusic() {
        // load in music files
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
    }

    // play a random song, not same as currently playing
    private void playNextSong() {
        int index;
        do {
            index = rand.nextInt(music.length);
        } while (music.length > 1 && index == lastIndex); // music.length > 1 so that it still works if there is only 1 music file
        lastIndex = index;

        currentSong = music[index];
        musicLength = currentSong.getSoundLength();
        currentSong.playSound();
    }

    // update music, called every tick
    public void updateMusic(double deltaTime) {
        if (music == null) {
            return;   // startMusic() hasn't run yet
        }

        // wait gapTime seconds between each song
        if (waiting) {
            gapTime -= deltaTime;
            if (gapTime <= 0) {
                waiting = false;
                playNextSong();
            }
            return;
        }

        // song is playing: when it ends, start the pause
        musicLength -= deltaTime;
        if (musicLength <= 0) {
            waiting = true;
            gapTime = GAP_SECONDS;
        }
    }
}