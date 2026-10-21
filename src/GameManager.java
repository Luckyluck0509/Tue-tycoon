import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameManager {
    Player player;
    Music music;
    Random rand = new Random();

    // all sounds
    Sound studySound1;
    Sound studySound2;
    Sound upgradeSound;
    Sound courseSound;
    Sound rebirthSound;
    Sound popupSound;
    Sound exitSound;
    Sound mutedSound;
    Sound resetSound;
    Sound failSound;

    // constructor
    public GameManager(Player p, Music m) {
        this.player = p;
        this.music = m;

        // all sounds
        try {
            studySound1 = new Sound("/Resources/Sounds/studySound1.wav");
            studySound2 = new Sound("/Resources/Sounds/studySound2.wav");
            upgradeSound = new Sound("/Resources/Sounds/upgradeSound.wav");
            courseSound = new Sound("/Resources/Sounds/courseSound.wav");
            rebirthSound = new Sound("/Resources/Sounds/rebirthSound.wav");
            popupSound = new Sound("/Resources/Sounds/popupSound.wav");
            exitSound = new Sound("/Resources/Sounds/exitSound.wav");
            mutedSound = new Sound("/Resources/Sounds/mutedSound.wav");
            resetSound = new Sound("/Resources/Sounds/resetSound.wav");
            failSound = new Sound("/Resources/Sounds/failSound.wav");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    // calculate cost of hiring a new student
    public int getStudentPrice() {
        return (int) (20 * Math.pow(1.15, player.numStudents));
    }

    // updates player knowledge when study button is pressed
    public void studyButtonPressed(JLabel button) {
        if (!player.inLecture) {
            player.knowledge += player.kpc * player.multiplier;
            player.totalKnowledge += player.kpc * player.multiplier;

            int num = rand.nextInt(4);
            if (num == 1) {
                studySound2.playSound();
            } else {
                studySound1.playSound();
            }
        }
    }

    // going to lecture, updates kpc and freezes study action
    public void goToLectureButtonPressed() {
        if (!player.inLecture) {
            upgradeSound.playSound();
            player.inLecture = true;
            player.lectureTime = 4 * Math.pow((player.kpc / player.multiplier), 1.35);
            player.lectureLength = (int) player.lectureTime;
            player.lecturesAttended++;
        }
    }

    // hires a student, increases kps
    public void addStudentButtonPressed() {
        if (player.knowledge >= this.getStudentPrice()) {
            upgradeSound.playSound();
            Student student = new Student(this.getStudentPrice(), player);
            player.numStudents += 1;
            player.kps += student.kps;
            player.studentsHired++;
        }
    }

    // sets course to finished, adds ects, add multiplier, subtract knowledge
    public void finishCourseButtonPressed() {
        if (player.knowledge >= player.courseRequirement && player.courseNumber <= 3) {
            courseSound.playSound();

            if (player.courseNumber < 3) {
                player.multiplier += 0.10;
            } else {
                player.multiplier += (0.20 * player.multiplier);
            }
            player.courses[player.quartile - 1][player.courseNumber - 1] = true;
            player.knowledge -= player.courseRequirement;
            player.creditProgress += 5;
            player.courseNumber++;
        }
    }


    // overlays a panel on top of a label
    public void overlay(LayoutManager layout, JLabel backdrop, JPanel container,
                        JPanel parentPanel, int width, int height) {
        JLayeredPane newLayeredPane = new JLayeredPane();
        newLayeredPane.setPreferredSize(new Dimension(width, height));
        newLayeredPane.setBounds(0, 0, width, height);
        backdrop.setBounds(0, 0, width, height);
        container.setBounds(0, 0, width, height);
        newLayeredPane.add(backdrop, JLayeredPane.DEFAULT_LAYER);
        newLayeredPane.add(container, JLayeredPane.PALETTE_LAYER);
        parentPanel.setLayout(layout);
        parentPanel.add(newLayeredPane);
    }

    // loads a PNG from the Resources folder
    private BufferedImage loadImage(String name) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass()
                    .getResource("/Resources/" + name)));
        } catch (Exception e) {
            System.err.println("Could not load /Resources/" + name);
            e.printStackTrace();
            return null;
        }
    }

    // swaps a progress bar's look for the PNG version. track = empty bar, fill = full bar
    public void applyPixelStyle(JProgressBar bar, String trackName,
                                String fillName, int orientation) {
        BufferedImage track = loadImage(trackName);
        BufferedImage fill = loadImage(fillName);

        bar.setUI(new ImageProgressBarUI(track, fill, orientation));
        bar.setBorderPainted(false);
        bar.setOpaque(false);
    }

    // closes a pop-up
    void closePopup(javax.swing.JFrame frame) {
        frame.dispose();
    }

    // updates variables
    public void update() {
        // update lecture time
        if (player.lectureTime < 0) {
            player.kpc *= 1.5;
            player.lectureTime = 0;
        } else if (player.lectureTime == 0) {
            player.inLecture = false;
        } else {
            player.lectureTime -= player.deltaTime;
        }

        // update clock
        player.time -= player.deltaTime;
        player.seconds = (int) Math.ceil(player.time) % 60;
        player.minutes = (int) Math.ceil(player.time) / 60;

        // update knowledge
        player.knowledge += player.kps * player.multiplier * player.deltaTime;
        player.totalKnowledge += player.kps * player.multiplier * player.deltaTime;

        // update course requirement
        player.courseRequirement = (int) (15000 * player.courseNumber
                * Math.pow(2.3, player.quartile - 1));

        // update feedback time
        player.sleepTime -= player.deltaTime;

        // updates music, play next song if this one is finished
        music.updateMusic(player.deltaTime);
    }
}
