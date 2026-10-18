import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

public class BsaPopup extends javax.swing.JFrame {
    // all panels in BSA pop-up
    private JPanel bsaPanel;
    private JPanel buttonPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel tableOverlay;
    private JPanel tablePanel;
    private JPanel bsaContainer;

    // all labels in BSA pop-up
    private JLabel tableBackground;
    private JLabel q1c1;
    private JLabel q1c2;
    private JLabel q1c3;
    private JLabel q2c1;
    private JLabel q2c2;
    private JLabel q2c3;
    private JLabel q3c1;
    private JLabel q3c2;
    private JLabel q3c3;
    private JLabel q4c1;
    private JLabel q4c2;
    private JLabel q4c3;
    private JLabel gradeCircle;
    private JLabel totalKnowledgeLabel;
    private JLabel lecturesAttendedLabel;
    private JLabel studentsHiredLabel;
    private JLabel creditsLabel;
    private JLabel timeLabel;
    private JLabel background;
    private JLabel exitButton;
    private JLabel restartButton;

    // declaring icons
    ImageIcon bsaBackground;
    ImageIcon yearTable;
    ImageIcon exitIcon;
    ImageIcon restartIcon;
    ImageIcon crossIcon;
    ImageIcon checkIcon;
    ImageIcon gradeAPlus;
    ImageIcon gradeA;
    ImageIcon gradeAMinus;
    ImageIcon gradeBPlus;
    ImageIcon gradeB;
    ImageIcon gradeBMinus;
    ImageIcon gradeCPlus;
    ImageIcon gradeC;
    ImageIcon gradeCMinus;
    ImageIcon gradeDPlus;
    ImageIcon gradeD;
    ImageIcon gradeDMinus;
    ImageIcon gradeFPlus;
    ImageIcon gradeF;
    ImageIcon gradeFMinus;
    ImageIcon gradeZ;

    public GameManager gameManager;
    public Player player;
    public MainScreen mainScreen;
    public SaveFile save;
    public Rebirth rebirth;

    // constructor
    public BsaPopup(MainScreen mainScreen, GameManager gameManager, Player player, SaveFile saveFile) {
        this.mainScreen = mainScreen;
        this.gameManager = gameManager;
        this.player = player;
        this.save = saveFile;
        this.rebirth = mainScreen.rebirth;

        // set assets
        try {
            bsaBackground = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/RebirthBackground.png")));
            yearTable = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/YearTable.png")));
            exitIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/ExitPost-It.png")));
            restartIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/ResetPost-It.png")));
            crossIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/Cross.png")));
            checkIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/Check.png")));

            gradeAPlus = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/A+.png")));
            gradeA = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/A.png")));
            gradeAMinus = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/A-.png")));
            gradeBPlus = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/B+.png")));
            gradeB = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/B.png")));
            gradeBMinus = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/B-.png")));
            gradeCPlus = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/C+.png")));
            gradeC = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/C.png")));
            gradeCMinus = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/C-.png")));
            gradeDPlus = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/D+.png")));
            gradeD = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/D.png")));
            gradeDMinus = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/D-.png")));
            gradeFPlus = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/F+.png")));
            gradeF = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/F.png")));
            gradeFMinus = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/F-.png")));
            gradeZ = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/grades/Z.png")));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        setContentPane(bsaPanel);

        // set assets
        background.setIcon(bsaBackground);
        gameManager.overlay(null, background, bsaContainer, bsaPanel, 600, 470);

        tableBackground.setIcon(yearTable);
        gameManager.overlay(null, tableBackground, tableOverlay, tablePanel, 267, 192);

        exitButton.setIcon(exitIcon);
        restartButton.setIcon(restartIcon);

        // JPanel attributes
        setTitle("BSA Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 470);
        setLocationRelativeTo(mainScreen);
        setResizable(false);

        // restart button
        restartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                rebirth.reset(gameManager, player, 1);
                closePopup();
            }
        });

        // exit button
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                rebirth.reset(gameManager, player, 1);
                try {
                    save.save(BsaPopup.this.player, BsaPopup.this.gameManager, BsaPopup.this.mainScreen);
                } catch (IOException error) {
                    JOptionPane.showMessageDialog(mainScreen, "Error resetting");
                    error.printStackTrace();
                }
                System.exit(10000);
            }
        });
    }

    public void triggerReport() {
        // Total credits label
        creditsLabel.setText(String.format("Total credits: %d/60", player.creditProgress));

        // time label
        int remaining = (int) Math.ceil(player.time);
        int elapsed = 3600 - remaining;
        int minutes = elapsed / 60;
        int seconds = elapsed % 60;
        timeLabel.setText(String.format("Time taken: %02d:%02d", minutes, seconds));

        // lectures label
        lecturesAttendedLabel.setText(String.format("Lectures attended: %d", player.lecturesAttended));

        // students label
        studentsHiredLabel.setText(String.format("Students hired: %d", player.studentsHired));

        // knowledge label
        totalKnowledgeLabel.setText(String.format("Total knowledge: %.0f", player.totalKnowledge));

        // courses completion grid
        JLabel[][] labels = {{q1c1, q1c2, q1c3}, {q2c1, q2c2, q2c3}, {q3c1, q3c2, q3c3}, {q4c1, q4c2, q4c3}};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (player.courses[i][j]) {
                    labels[i][j].setIcon(checkIcon);
                } else {
                    labels[i][j].setIcon(crossIcon);
                }
            }
        }
        setGradeCircle();

        setVisible(true);
    }

    void closePopup() {
        this.dispose();
    }

    public void setGradeCircle() {
        switch (player.creditProgress) {
            case 60 -> {
                if (player.totalKnowledge >= 2000000) {
                    gradeCircle.setIcon(gradeAPlus);
                } else if (player.totalKnowledge >= 1800000) {
                    gradeCircle.setIcon(gradeA);
                } else {
                    gradeCircle.setIcon(gradeAMinus);
                }
            }
            case 55 -> {
                if (player.totalKnowledge >= 1800000) {
                    gradeCircle.setIcon(gradeBPlus);
                } else if (player.totalKnowledge >= 1600000) {
                    gradeCircle.setIcon(gradeB);
                } else {
                    gradeCircle.setIcon(gradeBMinus);
                }
            }
            case 50 -> {
                if (player.totalKnowledge >= 1600000) {
                    gradeCircle.setIcon(gradeCPlus);
                } else if (player.totalKnowledge >= 1400000) {
                    gradeCircle.setIcon(gradeC);
                } else {
                    gradeCircle.setIcon(gradeCMinus);
                }
            }
            case 45 -> {
                if (player.totalKnowledge >= 1400000) {
                    gradeCircle.setIcon(gradeDPlus);
                } else if (player.totalKnowledge >= 1200000) {
                    gradeCircle.setIcon(gradeD);
                } else {
                    gradeCircle.setIcon(gradeDMinus);
                }
            }
            default -> {
                if (player.totalKnowledge == 0 && player.lecturesAttended == 0) {
                    gradeCircle.setIcon(gradeZ);
                } else {
                    if (player.totalKnowledge >= 1200000) {
                        gradeCircle.setIcon(gradeFPlus);
                    } else if (player.totalKnowledge >= 1000000) {
                        gradeCircle.setIcon(gradeF);
                    } else {
                        gradeCircle.setIcon(gradeFMinus);
                    }
                }
            }
        }
    }
}
