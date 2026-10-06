import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    public MainScreen(Player p, GameManager gm) {
        this.player = p;
        this.gameManager = gm;

        setContentPane(MainPanel);
        setTitle("TUe Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        TimeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        knowledgeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        KPSLabel.setFont(new Font("Arial", Font.BOLD, 30));
        MultiplierLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        KPC.setFont(new Font("Arial", Font.BOLD, 15));
        KPCLabel.setFont(new Font("Arial", Font.BOLD, 15));
        Price.setFont(new Font("Arial", Font.BOLD, 15));
        NumberOfStudents.setFont(new Font("Arial", Font.BOLD, 15));
        CourseNumberLabel.setFont(new Font("Arial", Font.BOLD, 15));

        studyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player.inLecture) {
                    gameManager.studyButtonPressed();
                }
            }
        });

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.knowledge >= player.getStudentPrice()) {
                    gameManager.addStudentButtonPressed();
                }
            }
        });

        goToLectureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player.inLecture) {
                    gameManager.goToLectureButtonPressed();
                    lectureTime.setMaximum((int) Math.ceil(gameManager.lectureTime));
                }
            }
        });

        finishCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    void update() {
        player.knowledge += player.kps * gameManager.deltaTime;

        TimeLabel.setText("00 : 00");
        knowledgeLabel.setText("Knowledge: " + (int)player.knowledge);
        KPSLabel.setText("KPS: " + Math.round(player.kps));
        MultiplierLabel.setText("Multiplier: " + player.multiplier);

        KPCLabel.setText(String.valueOf(player.kpc));
        lectureTime.setValue((int) Math.ceil(gameManager.lectureTime));
        Price.setText(String.valueOf(player.getStudentPrice()));
        NumberOfStudents.setText(String.valueOf(player.numStudents));
    }
}
