import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

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

    public MainScreen mainScreen;
    public Player player;
    public GameManager gameManager;
    public SaveFile saveFile;
    public Rebirth rebirth;
    public RebirthPopup self;

    // constructor
    public RebirthPopup(MainScreen mainScreen, Player player, GameManager gameManager, SaveFile saveFile) {
        // constructor
        this.mainScreen = mainScreen;
        this.player = player;
        this.gameManager = gameManager;
        this.saveFile = saveFile;
        this.rebirth = mainScreen.rebirth;
        this.self = this;

        // JPanel attributes
        setTitle("Rebirth");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(mainScreen);
        setResizable(false);
        setVisible(true);
        setContentPane(mainPanel);

        // set assets
        try {
            backgroundIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/RebirthPopupBackground.png")));
            buttonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Recources/RebirthButton.png")));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        background.setIcon(backgroundIcon);
        gameManager.overlay(null, background, container, mainPanel, 400, 322);

        rebirthButton.setIcon(buttonIcon);
        gameManager.overlay(new BorderLayout(), rebirthButton, rebirthButtonOverlay, rebirthPanel, 300, 175);

        // render Next quartile text
        if (player.quartile == 4){
            quartileLabel.setText(String.format("Quartile %d -> end of year", player.quartile));
        }else {
            quartileLabel.setText(String.format("Quartile %d -> %d", player.quartile, player.quartile + 1));
        }

        // render multiplier increase text
        multiplierLabel.setText(String.format("%1$,.2f multiplier increase", rebirth.MultiplierAmount(gameManager, player)));

        // trigger RebirthAction when the rebirth button is pressed
        rebirthButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (player.courseNumber > 2) {
                    rebirth.RebirthAction(gameManager, player);
                    if (player.quartile <= 4) {
                        mainScreen.feedback("Successful rebirth");
                    } else {
                        mainScreen.feedback("Finished year");
                        BsaPopup bsaPopup = new BsaPopup(mainScreen, gameManager, player, saveFile);
                        bsaPopup.triggerReport();
                    }
                    gameManager.closePopup(self);
                }
            }
        });
    }
}
