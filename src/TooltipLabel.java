import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

// Extension of JLabel class for labels with custom tooltips
public class TooltipLabel extends JLabel {
    private static BufferedImage tipImage;

    static {   // all tooltips share the same png
        try {
            tipImage = ImageIO.read(Objects.requireNonNull(
                    TooltipLabel.class.getResource("/Resources/ToolTip.png")));
        } catch (Exception e) {
            e.printStackTrace();   // falls back to the normal tooltip
        }
    }

    private Component anchor;      // the panel the tooltip is placed next to
    private final int gap = 6;     // pixels between the panel and the tooltip
    private JToolTip measureTip;   // only used to measure the tooltip size

    public void setAnchor(Component anchor) {
        this.anchor = anchor;
    }

    @Override
    public JToolTip createToolTip() {
        if (tipImage == null) {
            return super.createToolTip(); // create normal tooltip when png is missing
        }
        JToolTip tip = new ImageToolTip(tipImage);
        tip.setComponent(this);   // set owner of tooltip (label)
        return tip;
    }

    // position of the tooltip, relative to the top-left corner of the caller label
    @Override
    public Point getToolTipLocation(MouseEvent e) {
        String text = getToolTipText();
        if (anchor == null || text == null) {
            return null;   // default position
        }

        // measure the tooltip
        if (measureTip == null) {
            measureTip = createToolTip();
        }
        measureTip.setTipText(text);
        Dimension size = measureTip.getPreferredSize();

        // top-left corner of the anchor panel
        Point p = SwingUtilities.convertPoint(anchor, 0, 0, this);

        // left of the panel, centered in height
        int x = p.x - size.width - gap;
        int y = p.y + (anchor.getHeight() - size.height) / 2;
        return new Point(x, y);
    }
}
