import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class GameManager {
    Player player;
    Random rand = new Random();

    // all sounds
    Sound studySound1 = new Sound("/Resources/Sounds/studySound1.wav");
    Sound studySound2 = new Sound("/Resources/Sounds/studySound2.wav");
    Sound upgradeSound = new Sound("/Resources/Sounds/upgradeSound.wav");
    Sound courseSound = new Sound("/Resources/Sounds/courseSound.wav");
    Sound rebirthSound = new Sound("/Resources/Sounds/rebirthSound.wav");
    Sound popupSound = new Sound("/Resources/Sounds/popupSound.wav");
    Sound exitSound = new Sound("/Resources/Sounds/exitSound.wav");
    Sound mutedSound = new Sound("/Resources/Sounds/mutedSound.wav");
    Sound resetSound = new Sound("/Resources/Sounds/resetSound.wav");
    Sound failSound = new Sound("/Resources/Sounds/failSound.wav");

    // length of textfeedback time
    public double sleepTime;

    // constructor
    public GameManager(Player p) {
        this.player = p;
    }

    // calculate cost of hiring a new student
    public int getStudentPrice() {
        return (int)(20 * Math.pow(1.15, player.numStudents));
    }

    // updates player knowledge when study button is pressed
    public void studyButtonPressed() {
        if (!player.inLecture) {
            player.knowledge += player.kpc * player.multiplier;
            player.totalKnowledge += player.kpc * player.multiplier;
        }
        int num = rand.nextInt(4);

        switch (num) {
            case 1 -> {
                studySound2.playSound();
            } default -> {
                studySound1.playSound();
            }
        }

        player.knowledge += player.kpc * player.multiplier;
        player.totalKnowledge += player.kpc * player.multiplier;
    }

    // going to lecture, updates kpc and freezes study action
    public void goToLectureButtonPressed() {
        if (!player.inLecture) {
            upgradeSound.playSound();
            player.inLecture = true;
            player.lectureTime = 4 * Math.pow(player.kpc, 1.35);
            player.lectureLength = (int) player.lectureTime;
            player.lecturesAttended ++;
        }
    }

    // hires a student, increases kps
    public void addStudentButtonPressed() {
        if (player.knowledge >= this.getStudentPrice()) {
            Student student = new Student(this.getStudentPrice(), player);
            player.numStudents += 1;
            player.kps += student.kps;
            player.studentsHired ++;
        }
    }

    // sets course to finished, adds ects, add multiplier
    public void finishCourseButtonPressed() {
        if (player.knowledge >= player.courseRequirement && player.courseNumber <= 3) {
            player.courses[player.quartile - 1][player.courseNumber - 1] = true;
            player.courseNumber += 1;
            player.creditProgress += 5;
        if (player.knowledge >= player.courseRequirement && player.courseNumber <= 3) {
            courseSound.playSound();
            player.courses[player.quartile - 1][player.courseNumber - 1] = true;
            player.courseNumber += 1;
            player.creditProgress += 5;

            if (player.courseNumber <= 3) {
                player.multiplier += 0.10;
            } else {
                player.multiplier += 0.20;
            }
            player.knowledge -= player.courseRequirement;
        }
            if (player.courseNumber <= 3) {
                player.multiplier += 0.10;
            } else {
                player.multiplier += 0.20;
            }
            player.knowledge -= player.courseRequirement;
        }
    }


    // overlays a panel on top of a label
    public void overlay(LayoutManager layout, JLabel backdrop, JPanel container, JPanel parentPanel, int width, int height) {
        JLayeredPane new_layeredPane = new JLayeredPane();
        new_layeredPane.setPreferredSize(new Dimension(width, height));
        new_layeredPane.setBounds(0, 0, width, height);
        backdrop.setBounds(0, 0, width, height);
        container.setBounds(0, 0, width, height);
        new_layeredPane.add(backdrop, JLayeredPane.DEFAULT_LAYER);
        new_layeredPane.add(container, JLayeredPane.PALETTE_LAYER);
        parentPanel.setLayout(layout);
        parentPanel.add(new_layeredPane);
    }

    // loads a PNG from the Resources folder
    private BufferedImage loadImage(String name) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResource("/Resources/" + name)));
        } catch (Exception e) {
            System.err.println("Could not load /Resources/" + name);
            e.printStackTrace();
            return null;
        }
    }

    // swaps a progress bar's look for the PNG version. track = empty bar, fill = full bar
    public void applyPixelStyle(JProgressBar bar, String trackName, String fillName, int orientation) {
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
        player.courseRequirement = (int)(15000 * player.courseNumber * Math.pow(2.3, player.quartile - 1));

        // update feedback time
        sleepTime -= player.deltaTime;
    }
}
