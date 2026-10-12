import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProgressBarUI extends BasicProgressBarUI {
    private final BufferedImage track, fill;

    // constructor
    public ImageProgressBarUI(BufferedImage track, BufferedImage fill) {
        this.track = track;
        this.fill = fill;
    }


    // overriding normal rendering of a progressbar
    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        int w = c.getWidth();
        int h = c.getHeight();

        // empty bar, stretched to the component size
        g2.drawImage(track, 0, 0, w, h, null);

        // filled part: full image, cropped to left part, make sure we don't cut pixels in half
        double scale = (double) w / fill.getWidth();
        int artPixelsFilled = (int) Math.round(progressBar.getPercentComplete() * fill.getWidth());
        int fillW = (int) (artPixelsFilled * scale);
        if (fillW > 0) {
            g2.setClip(0, 0, fillW, h);
            g2.drawImage(fill, 0, 0, w, h, null);
        }

        // dispose drawer class
        g2.dispose();
    }
}
