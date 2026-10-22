import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import javax.swing.*;

public class SavePopup extends javax.swing.JFrame {
    // render java swing elements
    private JPanel mainPanel;
    private JLabel dateLabel;
    private JPanel resetPanel;
    private JPanel savePanel;
    private JLabel resetButton;
    private JPanel resetOverlay;
    private JLabel saveButton;
    private JPanel saveOverlay;
    private JPanel container;
    private JLabel background;

    // declaring icons
    ImageIcon backgroundIcon;
    ImageIcon buttonIcon;
    ImageIcon buttonIconHighlight;

    public MainScreen mainScreen;
    public Player player;
    public GameManager gameManager;
    public SaveFile saveFile;
    public SavePopup self;
    public Rebirth rebirth = new Rebirth();

    // constructor
    public SavePopup(MainScreen mainScreen, Player player,
                     GameManager gameManager, SaveFile saveFile) {
        // constructor
        this.mainScreen = mainScreen;
        this.player = player;
        this.gameManager = gameManager;
        this.saveFile = saveFile;
        this.self = this;

        // JPanel attributes
        setContentPane(mainPanel);
        setTitle("Save");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainPanel.setPreferredSize(new Dimension(400, 322));
        pack();
        setLocationRelativeTo(mainScreen);
        setResizable(false);

        // set assets
        try {
            backgroundIcon = new ImageIcon(Objects.requireNonNull(getClass()
                    .getResource("/Resources/PopupBackground.png")));
            buttonIcon = new ImageIcon(Objects.requireNonNull(getClass()
                    .getResource("/Resources/SaveButton.png")));
            buttonIconHighlight = new ImageIcon(Objects.requireNonNull(getClass()
                    .getResource("/Resources/SaveButtonHighlighted.png")));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        background.setIcon(backgroundIcon);
        gameManager.overlay(null, background, container, mainPanel, 400, 322);

        resetButton.setIcon(buttonIcon);
        gameManager.overlay(new BorderLayout(), resetButton, resetOverlay, resetPanel, 170, 100);

        saveButton.setIcon(buttonIcon);
        gameManager.overlay(new BorderLayout(), saveButton, saveOverlay, savePanel, 170, 100);

        // render last save text
        String date = "No save found";
        try (Scanner reader = new Scanner(saveFile.savePath);){
            reader.nextLine();
            date = reader.nextLine().substring(1);
        } catch (IOException error) {
            date = "No savefile found";
            error.printStackTrace();
        } catch (Exception error) {
            date = "Empty savefile";
            error.printStackTrace();
        }

        dateLabel.setText(date);

        //trigger reset() when the reset button is pressed
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetButton.setIcon(buttonIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetButton.setIcon(buttonIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                resetButton.setIcon(buttonIcon);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resetButton.setIcon(buttonIconHighlight);
                gameManager.resetSound.playSound();
                rebirth.reset(player, 1);
                mainScreen.feedback("Successful reset");
                mainScreen.clickMeLabel.setText("CLICK THE BOOK TO STUDY");
                mainScreen.clickMeLabel2.setText("AND GAIN KNOWLEDGE");
                gameManager.closePopup(self);
            }
        });

        // trigger save() when the save button is pressed
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
                try {
                    saveFile.save(player, gameManager, mainScreen);
                    mainScreen.feedback("Successful save");
                    gameManager.popupSound.playSound();
                } catch (IOException error) {
                    error.printStackTrace();
                    gameManager.failSound.playSound();
                    JOptionPane.showMessageDialog(mainPanel, "Error saving");
                    mainScreen.feedback("IOException error, try again");
                }
                gameManager.closePopup(self);
            }
        });
        
        setVisible(true);
    }
}
