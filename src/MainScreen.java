import javax.swing.*;
import java.awt.event.*;

public class MainScreen extends javax.swing.JFrame {
    private JPanel mainPanel; // bound to the .form file
    private JPanel panel1;
    private JButton studyButton;


    public MainScreen() {
        setTitle("TUe Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
}
