import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class BsaPopup extends javax.swing.JFrame {
    private JButton RESTARTButton;
    private JButton EXITButton;
    private JPanel bsaPanel;
    private JPanel buttonPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
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
    private JLabel yearCompleteLabel;
    private JLabel passedLabel;
    private JLabel creditsLabel;
    private JLabel timeLabel;

    public GameManager gm;
    public Player p;
    public MainScreen ms;
    public SaveFile save;
    public Rebirth rebirth;


    public BsaPopup(MainScreen mainScreen, GameManager gameManager, Player player, SaveFile saveFile) {
        // constructor
        this.ms = mainScreen;
        this.gm = gameManager;
        this.p = player;
        this.save = saveFile;
        this.rebirth = mainScreen.rebirth;

        setContentPane(bsaPanel);

        // JPanel attributes
        setTitle("BSA Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 470);
        setLocationRelativeTo(mainScreen);
        setResizable(false);

        // JLabel formatting
        creditsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        lecturesAttendedLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        studentsHiredLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        totalKnowledgeLabel.setFont(new Font("Arial", Font.PLAIN, 18));


        //Restart button
        RESTARTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rebirth.reset(gameManager, player, 1);
                closePopup();
            }
        });


        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rebirth.reset(gameManager, player, 1);
                try {
                    save.save(p, gm, ms);
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
        creditsLabel.setText(String.format("Total credits: %d/60", p.creditProgress));

        // time label
        int remaining = (int) Math.ceil(this.gm.time);
        int elapsed = 3600 - remaining;
        int minutes = elapsed / 60;
        int seconds = elapsed % 60;
        timeLabel.setText(String.format("Time taken: %02d:%02d", minutes, seconds));

        // lectures label
        lecturesAttendedLabel.setText(String.format("Lectures attended: %d", p.lecturesAttended));

        // students label
        studentsHiredLabel.setText(String.format("Students hired: %d", p.studentsHired));

        // knowledge label
        totalKnowledgeLabel.setText(String.format("Total knowledge: %.0f", p.totalKnowledge));

        setVisible(true);
    }

    void closePopup() {
        this.dispose();
    }
}
