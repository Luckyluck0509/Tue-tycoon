import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// java swing elements
public class MainScreen extends javax.swing.JFrame {
    private JPanel MainPanel; // bound to the .form file
    public JButton studyButton;
    public JLabel knowledgeLabel;
    private JButton addStudentButton;
    private JButton goToLectureButton;
    private JLabel KPSLabel;
    private JLabel TimeLabel;
    private JLabel MultiplierLabel;
    private JLabel KPCLabel;
    private JLabel KPC;
    private JProgressBar lectureTime;
    private JLabel Price;
    private JLabel NumberOfStudents;
    private JPanel LecturePanel;
    private JPanel HirePanel;
    private JButton finishCourseButton;
    private JPanel CoursePanel;
    private JLabel CourseNumberLabel;
    private JProgressBar KnowledgeProgressBar;
    private JButton Exit;
    private JButton Save;
    private JProgressBar CourseProgress;
    private JButton Rebirth;
    private JProgressBar CreditProgress;

    public GameManager gameManager;
    public Player player;
    public SaveFile saveFile;
    public MainScreen self = this;
    public Rebirth rebirth;
    public double dt;


    public MainScreen(Player p, GameManager gm, SaveFile saveFile, Rebirth rebirth) {
        this.player = p;
        this.gameManager = gm;
        this.saveFile = saveFile;
        this.rebirth = rebirth;

        // set properties of JPanel
        setContentPane(MainPanel);
        setTitle("TUe Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        // format JLabels
        TimeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        knowledgeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        KPSLabel.setFont(new Font("Arial", Font.BOLD, 30));
        MultiplierLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        KPC.setFont(new Font("Arial", Font.BOLD, 15));
        KPCLabel.setFont(new Font("Arial", Font.BOLD, 15));
        Price.setFont(new Font("Arial", Font.BOLD, 15));
        NumberOfStudents.setFont(new Font("Arial", Font.BOLD, 15));
        CourseNumberLabel.setFont(new Font("Arial", Font.BOLD, 15));

        // trigger studyButtonPressed() when the study button is clicked
        studyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player.inLecture) {
                    gameManager.studyButtonPressed();
                }
            }
        });

        // trigger addStudentButtonPressed() when the add student button is pressed
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.knowledge >= player.getStudentPrice()) {
                    gameManager.addStudentButtonPressed();
                }
            }
        });

        // trigger goToLectureButtonPressed when the go to lecture button is pressed, render progress bar
        goToLectureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player.inLecture) {
                    gameManager.goToLectureButtonPressed();
                    lectureTime.setMaximum((int) Math.ceil(gameManager.lectureTime));
                }
            }
        });

        // trigger finishCourseButtonPressed() when the finish course button is pressed
        finishCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.knowledge >= gameManager.courseRequirement && gameManager.courseNumber <= 3) {
                    gameManager.finishCourseButtonPressed();
                    player.knowledge -= gameManager.courseRequirement;
                }
            }
        });

        // quit the program when the exit button is pressed
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // open the save popup menu when the save button is pressed
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SavePopup savePopup = new SavePopup(self, player, gameManager, saveFile);
            }
        });

        // open the rebirth popup menu when the rebirth button is pressed
        Rebirth.addActionListener(new ActionListener() {
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
        player.knowledge += player.kps * dt;

        // update course requirement
        gameManager.setCourseRequirement();

        // render clock
        if (seconds >= 10) {
            TimeLabel.setText(minutes + " : " + seconds);
        } else {
            TimeLabel.setText(minutes + " : 0" + seconds);
        }

        // render knowledge, kps and multiplier
        knowledgeLabel.setText((int)player.knowledge + " Knowledge");
        KPSLabel.setText("KPS: " + Math.round(player.kps));
        MultiplierLabel.setText("Multiplier: " + player.multiplier);


        // render course progression
        if (gameManager.courseNumber <= 3) {
            CourseNumberLabel.setText(String.valueOf(gameManager.courseNumber));
            KnowledgeProgressBar.setMaximum(gameManager.courseRequirement);
            if (player.knowledge <= gameManager.courseRequirement) {
                KnowledgeProgressBar.setValue((int) Math.round(player.knowledge));
            }
        } else {
            CourseNumberLabel.setText("Finished all Courses");
            KnowledgeProgressBar.setMaximum(100);
            KnowledgeProgressBar.setValue(100);
        }


        // render go to lecture and add student attributes
        KPCLabel.setText(String.valueOf(player.kpc));
        lectureTime.setValue((int) Math.ceil(gameManager.lectureTime));
        Price.setText(String.valueOf(player.getStudentPrice()));
        NumberOfStudents.setText(String.valueOf(player.numStudents));
    }
}
