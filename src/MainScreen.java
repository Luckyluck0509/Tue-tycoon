import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// java swing elements
public class MainScreen extends javax.swing.JFrame {
    // importing icons
    ImageIcon backgroundIcon = new ImageIcon("src/Recources/Background.png");
    ImageIcon lectureIcon = new ImageIcon("src/Recources/GoToLecture.png");

    private JPanel mainPanel; // bound to the .form file
    private JPanel mainContainer;
    public JLabel background;

    private JPanel lecturePanel;
    private JPanel studentPanel;
    private JPanel coursePanel;

    private JButton studyButton;
    public JButton goToLectureButton = new JButton();
    private JButton addStudentButton;
    private JButton finishCourseButton;
    private JButton Rebirth;
    private JButton Save;
    private JButton Exit;

    private JLabel knowledgeLabel;
    private JLabel KPSLabel;
    private JLabel TimeLabel;
    private JLabel MultiplierLabel;
    private JLabel KPCLabel;
    private JLabel KPC;
    private JLabel Price;
    private JLabel NumberOfStudents;
    private JLabel CourseNumberLabel;

    public JProgressBar lectureTime = new JProgressBar();
    private JProgressBar KnowledgeProgressBar;
    private JProgressBar CourseProgress;
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

        // set properties of JPanel and create the main screen
        setContentPane(mainPanel);
        setTitle("TUe Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 828);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        background.setIcon(backgroundIcon);
        mainPanel.setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1280, 800);

        background.setBounds(0, 0, 1280, 800);
        mainContainer.setBounds(0, 0, 1280, 800);

        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(mainContainer, JLayeredPane.PALETTE_LAYER);

        mainPanel.add(layeredPane);

        // format JLabels
        TimeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        knowledgeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        KPSLabel.setFont(new Font("Arial", Font.BOLD, 30));
        MultiplierLabel.setFont(new Font("Arial", Font.PLAIN, 20));
//        KPC.setFont(new Font("Arial", Font.BOLD, 15));
//        KPCLabel.setFont(new Font("Arial", Font.BOLD, 15));
//        Price.setFont(new Font("Arial", Font.BOLD, 15));
//        NumberOfStudents.setFont(new Font("Arial", Font.BOLD, 15));
//        CourseNumberLabel.setFont(new Font("Arial", Font.BOLD, 15));

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
//        addStudentButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (player.knowledge >= player.getStudentPrice()) {
//                    gameManager.addStudentButtonPressed();
//                }
//            }
//        });

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
//        finishCourseButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (player.knowledge >= gameManager.courseRequirement && gameManager.courseNumber <= 3) {
//                    gameManager.finishCourseButtonPressed();
//                    player.knowledge -= gameManager.courseRequirement;
//                }
//            }
//        });

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
//            CourseNumberLabel.setText(String.valueOf(gameManager.courseNumber));
//            KnowledgeProgressBar.setMaximum(gameManager.courseRequirement);
            if (player.knowledge <= gameManager.courseRequirement) {
//                KnowledgeProgressBar.setValue((int) Math.round(player.knowledge));
            }
        } else {
            CourseNumberLabel.setText("Finished all Courses");
//            KnowledgeProgressBar.setMaximum(100);
//            KnowledgeProgressBar.setValue(100);
        }


        // render go to lecture and add student attributes
//        KPCLabel.setText(String.valueOf(player.kpc));
//        lectureTime.setValue((int) Math.ceil(gameManager.lectureTime));
//        Price.setText(String.valueOf(player.getStudentPrice()));
//        NumberOfStudents.setText(String.valueOf(player.numStudents));
    }

    public void createButton(JPanel panel, ImageIcon icon, String text, String value, JButton button) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 400, 200);

        JPanel components = new JPanel();

        button.setBounds(0, 0, 400, 200);
        components.setBounds(0, 0, 400, 200);

        JLabel image = new JLabel(icon);
        image.setBounds(5, 5, 48, 96);
        components.add(image);

        JLabel prop = new JLabel(text);
        prop.setBounds(75, 5, 200, 64);
        components.add(prop);

        JLabel amount = new JLabel(value);
        amount.setBounds(295, 50, 395, 100);
        components.add(amount);

        layeredPane.add(button, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(components, JLayeredPane.PALETTE_LAYER);

        panel.add(layeredPane);
    }
}
