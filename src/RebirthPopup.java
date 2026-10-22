import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.*;

public class RebirthPopup extends javax.swing.JFrame {
    // all panels in rebirth pop-up
    private JPanel mainPanel;
    private JPanel container;
    private JPanel rebirthPanel;
    private JPanel rebirthButtonOverlay;

    // all labels in rebirth pop-up
    private JLabel rebirthButton;
    private JLabel quartileLabel;
    private JLabel multiplierLabel;
    private JLabel background;

    // declaring icons
    ImageIcon backgroundIcon;
    ImageIcon buttonIcon;
    ImageIcon buttonIconHighlight;

    public MainScreen mainScreen;
    public Player player;
    public GameManager gameManager;
    public SaveFile saveFile;
    public Rebirth rebirth;
    public RebirthPopup self;

    // constructor
    public RebirthPopup(MainScreen mainScreen, Player player, GameManager gameManager,
                        SaveFile saveFile) {
        // constructor
        this.mainScreen = mainScreen;
        this.player = player;
        this.gameManager = gameManager;
        this.saveFile = saveFile;
        this.rebirth = mainScreen.rebirth;
        this.self = this;

        // JPanel attributes
        setContentPane(mainPanel);
        setTitle("Rebirth");
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
                    .getResource("/Resources/RebirthButton.png")));
            buttonIconHighlight = new ImageIcon(Objects.requireNonNull(getClass()
                    .getResource("/Resources/RebirthButtonHighlighted.png")));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        background.setIcon(backgroundIcon);
        gameManager.overlay(null, background, container, mainPanel, 400, 322);

        rebirthButton.setIcon(buttonIcon);
        gameManager.overlay(new BorderLayout(), rebirthButton,
                            rebirthButtonOverlay, rebirthPanel, 300, 175);

        // render Next quartile text
        if (player.quartile == 4) {
            quartileLabel.setText(String.format("Quartile %d -> end of year", player.quartile));
        } else {
            quartileLabel.setText(String.format("Quartile %d -> %d",
                                  player.quartile, player.quartile + 1));
        }

        // render multiplier increase text
        multiplierLabel.setText(String.format("%1$,.2f multiplier increase",
                                              rebirth.multiplierAmount(player)));

        // trigger RebirthAction when the rebirth button is pressed
        rebirthButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rebirthButton.setIcon(buttonIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rebirthButton.setIcon(buttonIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (player.courseNumber > 2) {
                    rebirthButton.setIcon(buttonIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                rebirthButton.setIcon(buttonIconHighlight);
                if (player.courseNumber > 2) {
                    gameManager.rebirthSound.playSound();
                    rebirth.rebirthAction(player);
                    if (player.quartile <= 4) {
                        mainScreen.feedback("Successful rebirth");
                    } else {
                        mainScreen.feedback("Finished year");
                    }
                    gameManager.closePopup(self);
                } else {
                    mainScreen.feedback("You haven't finished 2 courses yet");
                    gameManager.closePopup(self);
                }
            }
        });

        setVisible(true);
    }
}
