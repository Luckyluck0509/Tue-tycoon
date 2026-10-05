import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainScreen extends javax.swing.JFrame {
    private JPanel MainPanel; // bound to the .form file
    public JButton studyButton;
    public JLabel knowledgeLabel;
    private JButton addStudentButton;
    private JButton goToLectureButton;

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

        knowledgeLabel.setFont(new Font("Arial", Font.BOLD, 40));

        studyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player.inLecture) {
                    gameManager.StudyButtonPressed();
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
                }
            }
        });
    }

    void update() {
        player.knowledge += player.kps * gameManager.deltaTime;

        knowledgeLabel.setText("Knowledge: " + (int)player.knowledge);
        addStudentButton.setText("Add Student. Price: " + player.getStudentPrice());
    }
}
