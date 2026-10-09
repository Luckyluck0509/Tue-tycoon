import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BsaPopup extends javax.swing.JFrame {
    private JButton RESTARTButton;
    private JButton EXITButton;
    private JPanel bsaPanel;
    private JLabel bsaLabel;
    private JLabel passedLabel;
    private JLabel creditsLabel;
    private JLabel timeLabel;

    public GameManager gameManager;
    public Player player;
    public MainScreen mainScreen;
    public SaveFile saveFile;
    public Rebirth rebirth;


    public BsaPopup(MainScreen mainScreen, GameManager gameManager, Player player, SaveFile saveFile) {
        // constructor
        this.mainScreen = mainScreen;
        this.gameManager = gameManager;
        this.player = player;
        this.saveFile = saveFile;
        this.rebirth = mainScreen.rebirth;

        setContentPane(bsaPanel);

        // JPanel attributes
        setTitle("BSA Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 470);
        setLocationRelativeTo(mainScreen);
        setResizable(false);
        setVisible(true);

        // JLabel formatting
        bsaLabel.setFont(new Font("Arial", Font.BOLD, 30));
        passedLabel.setFont(new Font("Arial", Font.BOLD, 40));
        creditsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 20));

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
                System.exit(0);
            }
        });
    }

    void closePopup() {
        this.dispose();
    }
}
