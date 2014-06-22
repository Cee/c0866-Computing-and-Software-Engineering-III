package excalibur.game.presentation.myuicomponent;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Xc on 2014/5/17.
 */

public class RectAndCir extends JLabel {
    int color;
    int round;
    int length;

    public RectAndCir(int color, int round, int length) {
        this.color = color;
        this.round = round;
        this.length = length;
        setOpaque(true);
    }

    @Override
    public void update(Graphics g) {
//            super.update(g);
    }

    protected void paintComponent(Graphics g) {
        g.setColor(new Color(color));

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillRect(0, 0, length, 2 * round);
        g.fillOval(length - round, 0, 2 * round, 2 * round);
    }
}