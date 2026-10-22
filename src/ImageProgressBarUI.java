import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ImageProgressBarUI extends BasicProgressBarUI {
    private final BufferedImage track;
    private final BufferedImage fill;
    private final int orientation;

    // constructor
    public ImageProgressBarUI(BufferedImage track, BufferedImage fill, int orientation) {
        this.track = track;
        this.fill = fill;
        this.orientation = orientation;
    }


    // overriding normal rendering of a progressbar
    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();

        int w = c.getWidth();
        int h = c.getHeight();

        // empty bar, stretched to the component size
        g2.drawImage(track, 0, 0, w, h, null);

        // filled part: full image, cropped to left part, make sure we don't cut pixels in half
        if (orientation == 0) {
            double scale = (double) w / fill.getWidth();
            int artPixelsFilled = (int) Math.round(progressBar.getPercentComplete()
                                  * fill.getWidth());
            int fillW = (int) Math.round(artPixelsFilled * scale);
            if (fillW > 0) {
                g2.setClip(0, 0, fillW, h);
                g2.drawImage(fill, 0, 0, w, h, null);
            }
        } else {
            double scale = (double) h / fill.getHeight();
            int artPixelsFilled = (int) Math.round(progressBar.getPercentComplete()
                                  * fill.getHeight());
            int fillH = (int) (artPixelsFilled * scale);
            if (fillH > 0) {
                g2.setClip(0, h - fillH, w, fillH);
                g2.drawImage(fill, 0, 0, w, h, null);
            }
        }

        // dispose drawer class
        g2.dispose();
    }
}
