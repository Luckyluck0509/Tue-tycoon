import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

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
    public JLabel clickMeLabel;
    public JLabel clickMeLabel2;

    // declaring icons
    ImageIcon backgroundIcon;
    ImageIcon lectureIcon;
    ImageIcon upgradeButtonIcon;
    ImageIcon upgradeButtonIconHighlight;
    ImageIcon buttonIcon;
    ImageIcon buttonIconHighlight;
    ImageIcon bookIcon;
    ImageIcon bookIconHighlight;
    ImageIcon nerrrdIcon;
    ImageIcon reportIcon;
    ImageIcon rebirthButtonIcon;
    ImageIcon rebirthButtonIconHighlight;

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
            upgradeButtonIconHighlight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/ButtonHighlighted.png")));
            buttonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/ButtonSmall.png")));
            buttonIconHighlight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/ButtonSmallHighlighted.png")));
            bookIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/Book.png")));
            bookIconHighlight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/BookHighlighted.png")));
            nerrrdIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/Nerrrd.png")));
            reportIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/Report.png")));
            rebirthButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/ButtonRebirth.png")));
            rebirthButtonIconHighlight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resources/ButtonRebirthHighlighted.png")));
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

        // tooltip timing (ms)
        ToolTipManager.sharedInstance().setInitialDelay(100);
        ToolTipManager.sharedInstance().setDismissDelay(8000);

        // tooltips
        ((TooltipLabel) goToLectureButton).setAnchor(lecturePanel);
        goToLectureButton.setToolTipText("<html>Attend a lecture for +50%<br>knowledge per click.<br>You can't study<br>during the lecture.</html>");

        ((TooltipLabel) hireStudentButton).setAnchor(studentPanel);
        hireStudentButton.setToolTipText("<html>Spend knowledge to<br>hire a student who<br>earns knowledge<br>for you every second.</html>");

        ((TooltipLabel) finishCourseButton).setAnchor(coursePanel);
        finishCourseButton.setToolTipText("<html>Spend knowledge to<br>finish the current course.<br>Earns 5 ECTS and a<br>permanent multiplier bonus.</html>");

        // trigger studyButtonPressed() when the study button is clicked
        studyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                studyButton.setIcon(bookIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                studyButton.setIcon(bookIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!player.inLecture) {
                    studyButton.setIcon(bookIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                studyButton.setIcon(bookIconHighlight);
                gameManager.studyButtonPressed(studyButton);
                if (player.totalKnowledge >= 1) {
                    clickMeLabel.setText(" ");
                    clickMeLabel2.setText(" ");
                }
            }
        });

        // trigger goToLectureButtonPressed when the go to the lectures button is pressed, render progress bar
        goToLectureButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                goToLectureButton.setIcon(upgradeButtonIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                goToLectureButton.setIcon(upgradeButtonIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!player.inLecture) {
                    goToLectureButton.setIcon(upgradeButtonIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                goToLectureButton.setIcon(upgradeButtonIconHighlight);
                gameManager.goToLectureButtonPressed();
            }
        });

        // trigger addStudentButtonPressed() when the add student button is pressed
        hireStudentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hireStudentButton.setIcon(upgradeButtonIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hireStudentButton.setIcon(upgradeButtonIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (player.knowledge >= gameManager.getStudentPrice()) {
                    hireStudentButton.setIcon(upgradeButtonIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                hireStudentButton.setIcon(upgradeButtonIconHighlight);
                gameManager.addStudentButtonPressed();
            }
        });

        // trigger finishCourseButtonPressed() when the finish course button is pressed
        finishCourseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                finishCourseButton.setIcon(upgradeButtonIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                finishCourseButton.setIcon(upgradeButtonIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (player.knowledge >= player.courseRequirement && player.courseNumber <= 3) {
                    finishCourseButton.setIcon(upgradeButtonIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                finishCourseButton.setIcon(upgradeButtonIconHighlight);
                gameManager.finishCourseButtonPressed();
            }
        });

        // trigger save pop-up when save button is pressed
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setIcon(buttonIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setIcon(buttonIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                saveButton.setIcon(buttonIcon);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                saveButton.setIcon(buttonIconHighlight);
                gameManager.popupSound.playSound();
                SavePopup savePopup = new SavePopup(self, player, gameManager, saveFile);
            }
        });

        // open the rebirth popup menu when the rebirth button is pressed
        rebirthButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rebirthButton.setIcon(rebirthButtonIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rebirthButton.setIcon(rebirthButtonIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                rebirthButton.setIcon(rebirthButtonIcon);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                rebirthButton.setIcon(rebirthButtonIconHighlight);
                gameManager.popupSound.playSound();
                RebirthPopup rebirthPopup = new RebirthPopup(self, p, gm, saveFile);
            }
        });

        // exit the game
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(buttonIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(buttonIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                exitButton.setIcon(buttonIcon);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                exitButton.setIcon(buttonIconHighlight);
                gameManager.exitSound.playSound();
                resetFeedback();
                System.exit(0);
            }
        });
    }

    public void feedback(String message) {
        feedbackLabel.setText(message);
        player.sleepTime = 5.0;
    }

    public void resetFeedback() {
        feedbackLabel.setText("");
    }

    // update all variables every frame
    void screenUpdate() {
        lectureProgress.setMaximum(player.lectureLength);

        // render and count down sleep timer and feedback label
        if (player.sleepTime <= 0) {
            resetFeedback();
        } else {
            player.sleepTime -= player.deltaTime;
//            System.out.println(player.sleepTime);
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
        kpcLabel.setText(String.format("Knowledge/click: %.2f", player.kpc * player.multiplier));
        price.setText(String.format("Price: %d", gameManager.getStudentPrice()));
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

    private void createUIComponents() {
        goToLectureButton = new TooltipLabel();
        hireStudentButton = new TooltipLabel();
        finishCourseButton = new TooltipLabel();
    }
}
