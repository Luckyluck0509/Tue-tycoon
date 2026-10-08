import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RebirthPopup extends javax.swing.JFrame {
    // render java swing elements
    private JPanel rebirthPopup;
    private JPanel rebirthPanel;
    private JButton rebirthButton;
    private JLabel quartileLabel;
    private JLabel mulitplierLabel;
    private JLabel rebirthLabel;

    public MainScreen mainScreen;
    public Player player;
    public GameManager gameManager;
    public SaveFile saveFile;
    public Rebirth rebirth;



    public RebirthPopup(MainScreen mainScreen, Player player, GameManager gameManager, SaveFile saveFile) {
        // constructor
        this.mainScreen = mainScreen;
        this.player = player;
        this.gameManager = gameManager;
        this.saveFile = saveFile;
        this.rebirth = mainScreen.rebirth;


        setContentPane(rebirthPanel);

        // JPanel attributes
        setTitle("Rebirth");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(mainScreen);
        setResizable(false);
        setVisible(true);

        // JLabel formatting
        rebirthLabel.setFont(new Font("Arial", Font.BOLD, 30));
        quartileLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        mulitplierLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // render Next quartile text
        if (gameManager.quartile == 4){
            quartileLabel.setText(String.format("Quartile %d -> end of year", gameManager.quartile));
        }else {
            quartileLabel.setText(String.format("Quartile %d -> %d", gameManager.quartile, gameManager.quartile + 1));
        }

        // render multiplier increase text
        mulitplierLabel.setText(String.format("%1$,.2f multiplier increase", rebirth.MultiplierAmount(gameManager, player)));

        // trigger RebirthAction when the rebirth button is pressed
        rebirthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameManager.courseNumber > 2) {
                    rebirth.RebirthAction(gameManager, player);
                    closePopup();
                }

            }
        });
    }

    void closePopup() {
        this.dispose();
    }
}
