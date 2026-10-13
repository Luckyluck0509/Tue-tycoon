import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

// java swing elements
public class MainScreen extends javax.swing.JFrame {
    // importing icons
    ImageIcon backgroundIcon;
    ImageIcon lectureIcon;
    ImageIcon upgradeButtonIcon;
    ImageIcon buttonIcon;
    ImageIcon bookIcon;
    ImageIcon nerrrdIcon;
    ImageIcon reportIcon;

    private JPanel mainPanel; // bound to the .form file
    private JPanel mainContainer;
    public JLabel background;

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

    private JButton rebirthButton;

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

    public JProgressBar lectureProgress;
    private JProgressBar courseProgress;
    private JProgressBar rebirthProgress;
    private JProgressBar creditProgress;

    public GameManager gameManager;
    public Player player;
    public SaveFile saveFile;
    public MainScreen self = this;
    public Rebirth rebirth;
    public double dt;


    public MainScreen(Player p, GameManager gm, SaveFile saveFile, Rebirth rebirth) {
        // constructor
        this.player = p;
        this.gameManager = gm;
        this.saveFile = saveFile;
        this.rebirth = rebirth;

        try {
            backgroundIcon = new ImageIcon(getClass().getResource("/Recources/Background.png"));
            lectureIcon = new ImageIcon(getClass().getResource("/Recources/GoToLecture.png"));
            upgradeButtonIcon = new ImageIcon(getClass().getResource("/Recources/Button.png"));
            buttonIcon = new ImageIcon(getClass().getResource("/Recources/Button2.png"));
            bookIcon = new ImageIcon(getClass().getResource("/Recources/Book.png"));
            nerrrdIcon = new ImageIcon(getClass().getResource("/Recources/Nerrrd.png"));
            reportIcon = new ImageIcon(getClass().getResource("/Recources/Report.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        // pixel art progress bars
        applyPixelStyle(lectureProgress, "bar_track.png", "bar_fill.png");
        applyPixelStyle(courseProgress, "bar_track.png", "bar_fill.png");
        applyPixelStyle(rebirthProgress, "bar_track.png", "bar_fill.png");

        lectureProgress.setValue(0);
        lectureProgress.setMaximum(gameManager.lectureLength);
        courseProgress.setValue(0);
        rebirthProgress.setValue(0);

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
        this.overlay(null, background, mainContainer, mainPanel, 1280, 800);

        // set buttons
        studyButton.setIcon(bookIcon);

        lectureIconLabel.setIcon(lectureIcon);
        goToLectureButton.setIcon(upgradeButtonIcon);
        this.overlay(new BorderLayout(), goToLectureButton, lectureButtonOverlay, lecturePanel, 550, 200);

        studentIconLabel.setIcon(nerrrdIcon);
        hireStudentButton.setIcon(upgradeButtonIcon);
        this.overlay(new BorderLayout(), hireStudentButton, studentButtonOverlay, studentPanel, 550, 200);

        courseIconLabel.setIcon(reportIcon);
        finishCourseButton.setIcon(upgradeButtonIcon);
        this.overlay(new BorderLayout(), finishCourseButton, courseButtonOverlay, coursePanel, 550, 200);

        saveButton.setIcon(buttonIcon);
        this.overlay(new BorderLayout(), saveButton, saveButtonOverlay, savePanel, 280, 45);

        exitButton.setIcon(buttonIcon);
        this.overlay(new BorderLayout(), exitButton, exitButtonOverlay, exitPanel, 280, 45);

        // trigger studyButtonPressed() when the study button is clicked
        studyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!player.inLecture) {
                    gameManager.studyButtonPressed();
                }
            }
        });

        // trigger goToLectureButtonPressed when the go to the lectures button is pressed, render progress bar
        lecturePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!player.inLecture) {
                    gameManager.goToLectureButtonPressed();
                    lectureProgress.setMaximum(gameManager.lectureLength);
                }
            }
        });

        // trigger addStudentButtonPressed() when the add student button is pressed
        studentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (player.knowledge >= player.getStudentPrice()) {
                    gameManager.addStudentButtonPressed();
                }
            }
        });

        finishCourseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (player.knowledge >= gameManager.courseRequirement && gameManager.courseNumber <= 3) {
                    gameManager.finishCourseButtonPressed();
                    if (gameManager.courseNumber <= 3) {
                        player.multiplier += 0.05;
                    } else {
                        player.multiplier += 0.1;
                    }
                    player.knowledge -= gameManager.courseRequirement;
                }
            }
        });

        savePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                SavePopup savePopup = new SavePopup(self, player, gameManager, saveFile);
            }
        });

        exitPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(0);
            }
        });

        // open the rebirth popup menu when the rebirth button is pressed
        rebirthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RebirthPopup rebirthPopup = new RebirthPopup(self, p, gm, saveFile);
            }
        });
    }

    // update all variables every frame
    void update() {
        this.dt = gameManager.deltaTime;

        // update lecture time
        if (gameManager.lectureTime <= 0) {
            player.inLecture = false;
        } else {
            gameManager.lectureTime -= dt;
        }

        // update clock
        this.gameManager.time -= dt;
        int seconds = (int) Math.ceil(this.gameManager.time) % 60;
        int minutes = (int) Math.ceil(this.gameManager.time) / 60;

        // update knowledge
        player.knowledge += player.kps * player.multiplier * dt;

        // update course requirement
        gameManager.setCourseRequirement();

        // render clock
        if (seconds >= 10) {
            timeLabel.setText(minutes + " : " + seconds);
        } else {
            timeLabel.setText(minutes + " : 0" + seconds);
        }

        // render knowledge, kps and multiplier
        knowledgeLabel.setText((int)player.knowledge + " Knowledge");
        kpsLabel.setText("KPS: " + Math.round(player.kps));
        multiplierLabel.setText(String.format("Multiplier: %.2f", player.multiplier));

        // render course progression
        if (gameManager.courseNumber <= 3) {
            courseNumberLabel.setText(String.valueOf(gameManager.courseNumber));
            courseProgress.setMaximum(gameManager.courseRequirement);
            if (player.knowledge <= gameManager.courseRequirement) {
                courseProgress.setValue((int) Math.round(player.knowledge));
            } else {
                courseProgress.setValue(gameManager.courseRequirement);
            }
            finishCourseLabel.setText("Finish course");
        } else {
            finishCourseLabel.setText("Finished all Courses");
            courseProgress.setMaximum(100);
            courseProgress.setValue(100);
        }

        // render go to lecture and add student attributes
        lectureProgress.setValue((int) Math.ceil(gameManager.lectureTime));
        kpcLabel.setText(String.valueOf(player.kpc));
        price.setText(String.valueOf(player.getStudentPrice()));
        numberOfStudentsLabel.setText(String.valueOf(player.numStudents));

        // render rebirth progress
        if (gameManager.courseNumber <= 3) {
            rebirthProgress.setValue(gameManager.courseNumber - 1);
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

    // loads a PNG from the Recources folder
    private BufferedImage loadImage(String name) {
        try {
            return ImageIO.read(getClass().getResource("/Recources/" + name));
        } catch (Exception e) {
            System.err.println("Could not load /Recources/" + name);
            e.printStackTrace();
            return null;
        }
    }

    // swaps a progress bar's look for the PNG version
    // track = empty bar
    // fill = full bar
    private void applyPixelStyle(JProgressBar bar, String trackName, String fillName) {
        BufferedImage track = loadImage(trackName);
        BufferedImage fill = loadImage(fillName);
        if (track == null || fill == null) return;   // keep the default look if loading failed

        bar.setUI(new ImageProgressBarUI(track, fill));
        bar.setBorderPainted(false);
        bar.setOpaque(false);
    }
}
