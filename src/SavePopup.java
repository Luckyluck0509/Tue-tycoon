import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Scanner;

public class SavePopup extends javax.swing.JFrame {
    // render java swing elements
    private JButton RESETButton;
    private JButton SAVEButton;
    private JLabel saveLabel;
    private JLabel lastSaveLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;

    public MainScreen mainScreen;
    public Player player;
    public GameManager gameManager;
    public SaveFile saveFile;

    public SavePopup(MainScreen mainScreen, Player player, GameManager gameManager, SaveFile saveFile) {
        // constructor
        this.mainScreen = mainScreen;
        this.player = player;
        this.gameManager = gameManager;
        this.saveFile = saveFile;

        setContentPane(mainPanel);

        // JPanel attributes
        setTitle("Save");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(mainScreen);
        setResizable(false);
        setVisible(true);

        // JLabel formatting
        saveLabel.setFont(new Font("Arial", Font.BOLD, 30));
        lastSaveLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        RESETButton.setBackground(Color.getColor("#CC2F1B"));

        // render last save text
        String date = "No save found";
        try {
            Scanner reader = new Scanner(saveFile.SAVE_PATH);
            reader.nextLine();
            date = reader.nextLine();
        } catch (IOException error) {
            error.printStackTrace();
        }

        dateLabel.setText(date);


        // trigger save() when the save button is pressed
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveFile.save(player, gameManager, mainScreen);
                } catch (IOException error) {
                    error.printStackTrace();
                }
                closePopup();
            }
        });

        // trigger reset() when the reset button is pressed
        RESETButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rebirth rebirth = new Rebirth();
                rebirth.reset(gameManager, player, 1);
                closePopup();
            }
        });
    }

    // close popup after clicking a button
    void closePopup() {
        this.dispose();
    }
}
