package excalibur.game.presentation.myuicomponent;

import excalibur.game.presentation.tools.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xc on 2014/5/17.
 */
public class CirWithTex extends JLabel {
    private int degree;
    private int color;
    private int round;
    private int fontSize;
    private String text;
    private boolean isChangeLine;
    private static Map<Integer,Font> fontMap = new HashMap<>();

    public CirWithTex(int color, int round, int fontSize, String text, int degree , boolean isChangeLine) {
        this.color = color;
        this.round = round;
        this.fontSize = fontSize;
        this.text = text;
        this.degree = degree;
        this.isChangeLine =isChangeLine;
//        setContentAreaFilled(false);
//        setBorderPainted(false);
//        setFocusPainted(false);

        this.setOpaque(false);


    }

    public void setText(String text){
        this.text = text;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).scale(Math.cos(Math.PI * degree / 180), 1);
//        g.setColor(new Color(color).darker());
//        g.fillOval(1, 1, 2 * round, 2 * round);
        g.setColor(new Color(color));
        g.fillOval((int) (round / Math.cos(Math.PI * degree / 180)  - round ), 0, 2 * round, 2 * round);

        g.setColor(new Color(0xffffff));
//        g.setFont(new Font("幼圆", Font.BOLD, fontSize));

        g.setFont(getFont(fontSize));
        FontMetrics fm = g.getFontMetrics(); //取得FontMetrics类实体
//            g.drawLine(0,round,round*2,round);

        if (isChangeLine) {

            int x = (int) (round / Math.cos(Math.PI * degree / 180) - fm.stringWidth(text.substring(0, 2)) / 2 );
            int x2 = (int) (round/ Math.cos(Math.PI * degree / 180) - fm.stringWidth(text.substring(2, 4)) / 2 );

            g.drawString(text.substring(0, 2), x, round - fm.getHeight() + fm.getAscent());
            g.drawString(text.substring(2, 4), x2, round + fm.getAscent());
        } else {
            int width =  fm.stringWidth(text) ; //取得字符串宽度

            ((Graphics2D) g).drawString(text, (float)( round / Math.cos(Math.PI * degree / 180) - width / 2), round - fm.getHeight() / 2 + fm.getAscent());

        }

    }

    Font getFont(int size){
        if( fontMap.containsKey(size) ){

        }else{
            fontMap.put(size,FontLoader.loadFont("youyuan.ttf",size));
        }
        return fontMap.get(size);

    }
//
//    class MyIcon implements Icon {
//
//        public MyIcon(){
//
//        }
//        @Override
//        public void paintIcon(Component c, Graphics g, int x, int y) {
//            paintComponent2(g);
//
//        }
//
//        @Override
//        public int getIconWidth() {
//            return round * 2 + 1;
//        }
//
//        @Override
//        public int getIconHeight() {
//            return round * 2 + 1;
//        }
//    }
}