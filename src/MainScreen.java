import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainScreen extends javax.swing.JFrame {
    private JPanel mainPanel; // bound to the .form file
    private JPanel panel1;
    public JButton studyButton;



    public MainScreen() {
        setTitle("TUe Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(true);


        studyButton.addActionListener(new Listener());

        setVisible(true);
    }
}

class Listener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("pressed");
    }
}
