import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

// class for tooltips of buttons with custom png background
public class ImageToolTip extends JToolTip {
    private final BufferedImage background;

    public ImageToolTip(BufferedImage background) {
        this.background = background;

        // properties
        setOpaque(false);   // make the overlay transparent
        setForeground(new Color(0xf3e03a));
        setFont(new Font("Monospaced", Font.BOLD, 14));
        setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10)); // padding
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        g2.dispose();

        super.paintComponent(g);   // draws the text on top
    }
}
