import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.awt.image.*;

// java swing elements
public class MainScreen extends javax.swing.JFrame {
    // all panels in main screen
    private JPanel mainPanel;
    private JPanel mainContainer;
    private JPanel lecturePanel;
    private JPanel studentPanel;
    private JPanel coursePanel;
    private JPanel lectureButtonOverlay;
    private JPanel studentButtonOverlay;
    private JPanel courseButtonOverlay;
    private JPanel savePanel;
    private JPanel saveButtonOverlay;
    private JPanel exitPanel;
    private JPanel exitButtonOverlay;
    private JPanel rebirthPanel;
    private JPanel rebirthButtonOverlay;

    // all labels in main screen
    private JLabel background;
    private JLabel knowledgeLabel;
    private JLabel kpsLabel;
    private JLabel timeLabel;
    private JLabel multiplierLabel;
    private JLabel lectureIconLabel;
    private JLabel goToLectureButton;
    private JLabel kpcLabel;
    private JLabel studyButton;
    private JLabel hireStudentButton;
    private JLabel studentIconLabel;
    private JLabel numberOfStudentsLabel;
    private JLabel price;
    private JLabel courseNumberLabel;
    private JLabel finishCourseButton;
    private JLabel courseIconLabel;
    private JLabel finishCourseLabel;
    private JLabel saveButton;
    private JLabel exitButton;
    private JLabel feedbackLabel;
    private JLabel rebirthButton;
    private JLabel courseMultiplierLabel;
    private JLabel quartileNum;

    // all progress bars in main screen
    private JProgressBar lectureProgress;
    private JProgressBar courseProgress;
    private JProgressBar creditProgress;
    private JProgressBar rebirthProgress;

    // declaring icons
    ImageIcon backgroundIcon;
    ImageIcon lectureIcon;
    ImageIcon upgradeButtonIcon;
    ImageIcon buttonIcon;
    ImageIcon bookIcon;
    ImageIcon nerrrdIcon;
    ImageIcon reportIcon;
    ImageIcon rebirthButtonIcon;

    public GameManager gameManager;
    public Player player;
    public SaveFile saveFile;
    public MainScreen self = this;
    public Rebirth rebirth;

    // constructor
    public MainScreen(Player p, GameManager gm, SaveFile saveFile, Rebirth rebirth) {
        // constructor
        this.player = p;
        this.gameManager = gm;
        this.saveFile = saveFile;
        this.rebirth = rebirth;

        // set assets
        try {
            backgroundIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/Background.png")));
            lectureIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/GoToLecture.png")));
            upgradeButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/Button.png")));
            buttonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/Button2.png")));
            bookIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/Book.png")));
            nerrrdIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/Nerrrd.png")));
            reportIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/Report.png")));
            rebirthButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/ButtonRebirth.png")));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        // fixes a bug
        setFocusTraversalPolicy(new java.awt.ContainerOrderFocusTraversalPolicy());

        // pixel art progress bars
        gameManager.applyPixelStyle(lectureProgress, "UpgradeBarTrack.png", "UpgradeBarFill.png", 0);
        gameManager.applyPixelStyle(courseProgress, "UpgradeBarTrack.png", "UpgradeBarFill.png", 0);
        gameManager.applyPixelStyle(rebirthProgress, "RebirthBarTrack.png", "RebirthBarFill.png", 1);
        gameManager.applyPixelStyle(creditProgress, "BSABarTrack.png", "BSABarFill.png", 0);

        lectureProgress.setValue(0);
        lectureProgress.setMaximum(player.lectureLength);
        courseProgress.setValue(0);
        rebirthProgress.setValue(0);
        rebirthProgress.setMaximum(2);
        creditProgress.setValue(0);

        // set properties of JPanel and create the main screen
        setContentPane(mainPanel);
        setTitle("TUe Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 828);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        // set background
        background.setIcon(backgroundIcon);
        gameManager.overlay(null, background, mainContainer, mainPanel, 1280, 800);

        // set all buttons
        studyButton.setIcon(bookIcon);

        lectureIconLabel.setIcon(lectureIcon);
        goToLectureButton.setIcon(upgradeButtonIcon);
        gameManager.overlay(new BorderLayout(), goToLectureButton, lectureButtonOverlay, lecturePanel, 550, 200);

        studentIconLabel.setIcon(nerrrdIcon);
        hireStudentButton.setIcon(upgradeButtonIcon);
        gameManager.overlay(new BorderLayout(), hireStudentButton, studentButtonOverlay, studentPanel, 550, 200);

        courseIconLabel.setIcon(reportIcon);
        finishCourseButton.setIcon(upgradeButtonIcon);
        gameManager.overlay(new BorderLayout(), finishCourseButton, courseButtonOverlay, coursePanel, 550, 200);

        saveButton.setIcon(buttonIcon);
        gameManager.overlay(new BorderLayout(), saveButton, saveButtonOverlay, savePanel, 280, 56);

        exitButton.setIcon(buttonIcon);
        gameManager.overlay(new BorderLayout(), exitButton, exitButtonOverlay, exitPanel, 280, 56);

        rebirthButton.setIcon(rebirthButtonIcon);
        gameManager.overlay(new BorderLayout(), rebirthButton, rebirthButtonOverlay, rebirthPanel, 600, 56);

        // trigger studyButtonPressed() when the study button is clicked
        studyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gameManager.studyButtonPressed();
            }
        });

        // trigger goToLectureButtonPressed when the go to the lectures button is pressed, render progress bar
        lecturePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gameManager.goToLectureButtonPressed();
            }
        });

        // trigger addStudentButtonPressed() when the add student button is pressed
        studentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gameManager.addStudentButtonPressed();
            }
        });

        finishCourseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gameManager.finishCourseButtonPressed();
            }
        });

        savePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gameManager.popupSound.playSound();
                SavePopup savePopup = new SavePopup(self, player, gameManager, saveFile);
            }
        });

        // open the rebirth popup menu when the rebirth button is pressed
        rebirthButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gameManager.popupSound.playSound();
                RebirthPopup rebirthPopup = new RebirthPopup(self, p, gm, saveFile);
            }
        });

        // exit the game
        exitPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gameManager.exitSound.playSound();
                resetFeedback();
                System.exit(0);
            }
        });
    }


    // update all variables every frame
    void screenUpdate() {
        lectureProgress.setMaximum(player.lectureLength);

        // render and count down sleep timer and feedback label
        player.sleepTime -= player.deltaTime;
        if (player.sleepTime <= 0) {
            resetFeedback();
        }

        // render clock
        timeLabel.setText(String.format("%02d:%02d", player.minutes, player.seconds));

        // render knowledge, kps and multiplier
        knowledgeLabel.setText((int)player.knowledge + " Knowledge");
        kpsLabel.setText(String.format("KPS: %.2f", player.kps * player.multiplier));
        multiplierLabel.setText(String.format("Multiplier: %.2f", player.multiplier));

        // render course progression
        if (player.courseNumber <= 3) {
            courseNumberLabel.setText(String.valueOf(player.courseNumber));
            courseProgress.setMaximum(player.courseRequirement);
            if (player.knowledge <= player.courseRequirement) {
                courseProgress.setValue((int) Math.round(player.knowledge));
            } else {
                courseProgress.setValue(player.courseRequirement);
            }
            finishCourseLabel.setFont(new Font("Arial", Font.BOLD, 36));
            finishCourseLabel.setText("Finish course");
            if (player.courseNumber < 3) {
                courseMultiplierLabel.setText("+ 0.1 multiplier");
            } else {
                courseMultiplierLabel.setText("+ 0.2 multiplier");
            }
        } else {
            finishCourseLabel.setFont(new Font("Arial", Font.BOLD, 35));
            finishCourseLabel.setText("Finished all Courses");
            courseMultiplierLabel.setText("NO!");
            courseProgress.setMaximum(100);
            courseProgress.setValue(100);
        }

        // render go to lecture and add student attributes
        lectureProgress.setValue((int) Math.ceil(player.lectureTime));
        kpcLabel.setText(String.format("%.2f", player.kpc * player.multiplier));
        price.setText(String.valueOf(gameManager.getStudentPrice()));
        numberOfStudentsLabel.setText(String.valueOf(player.numStudents));

        // render rebirth progress
        if (player.courseNumber <= 3) {
            rebirthProgress.setValue(player.courseNumber - 1);
        } else {
            rebirthProgress.setValue(2);
        }
        quartileNum.setText(String.valueOf(player.quartile));

        // render bsa progress
        creditProgress.setValue(player.creditProgress);

        // render and trigger bsaReport when time runs out
        if (player.time <= 0) {
            BsaPopup bsaPopup = new BsaPopup(self, gameManager, player, saveFile);
            bsaPopup.triggerReport();
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
            return ImageIO.read(getClass().getResource("/Resources/" + name));
        } catch (Exception e) {
            System.err.println("Could not load /Resources/" + name);
            e.printStackTrace();
            return null;
        }
    }

    // swaps a progress bar's look for the PNG version
    // track = empty bar
    // fill = full bar
    private void applyPixelStyle(JProgressBar bar, String trackName, String fillName, int orientation) {
        BufferedImage track = loadImage(trackName);
        BufferedImage fill = loadImage(fillName);

        bar.setUI(new ImageProgressBarUI(track, fill, orientation));
        bar.setBorderPainted(false);
        bar.setOpaque(false);
    }

    public void feedback(String message) {
        feedbackLabel.setText(message);
        gameManager.sleepTime = 2.0;
    }

    public void resetFeedback() {
        feedbackLabel.setText("");
    }
}
