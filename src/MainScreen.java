import javax.swing.*;
import java.awt.event.*;

public class MainScreen extends javax.swing.JFrame {
    private JPanel MainPanel; // bound to the .form file
    public JButton studyButton;
    private JLabel knowledgeLabel;

    public MainScreen() {
        setContentPane(MainPanel);
        setTitle("TUe Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        studyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println();
            }
        });
    }
}
