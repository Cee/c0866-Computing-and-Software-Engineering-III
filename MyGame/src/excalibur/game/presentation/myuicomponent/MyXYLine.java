package excalibur.game.presentation.myuicomponent;

import excalibur.game.presentation.tools.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Xc on 2014/5/10.
 */
public class MyXYLine extends JPanel {

    ArrayList<Integer> valueList;
    ArrayList<Integer> xList;
    ArrayList<Integer> yList;


    private int millSeconds = 2000;
    private int endX;
    private boolean drawing = false;
    private int thick = 3;
    private Color dotColor = new Color(200,200,250);
    private Color lineColor = new Color(200,200,200);
    private Color fontColor = new Color(160,160,200);
    private JLabel bgLabel;
    private Color outColor = new Color(180,180,180);

    private final int dotRound = 3;
    private final int dotOuter = 2;

//    private final int borderWidth = 0;
    private Color borderColor = new Color(160,160,200);

    public MyXYLine(ArrayList<Integer> valueList) {
        setOpaque(false);
        this.valueList = valueList;
        animate();

    }


    @Override
    public void repaint() {
        if (!drawing) {
            animate();
        } else {
            super.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics gg) {
        super.paintComponent(gg);



        ((Graphics2D) gg).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        gg.setColor(borderColor);
//        ((Graphics2D) gg).setStroke(new BasicStroke(borderWidth));
//        gg.drawRoundRect(borderWidth,borderWidth,getWidth() - 2 * borderWidth,getHeight()-2*borderWidth,borderWidth,borderWidth);

        ((Graphics2D) gg).setStroke(new BasicStroke(thick));


        gg.setColor(lineColor);

        gg.setFont(new Font("Console", Font.BOLD, 15));

        if (!drawing) {
            endX = getWidth();
        }

        cal_xyList();
        final Graphics g = gg;


        for (int i = 1; i < valueList.size(); ++i) {
            int x1 = xList.get(i - 1);
            int y1 = yList.get(i - 1);
            int x2 = xList.get(i);
            int y2 = yList.get(i);
            g.setColor(lineColor);
            slowDraw(x1, y1, x2, y2, g, endX);


            FontMetrics fm = g.getFontMetrics();

            String s = "" + valueList.get(i - 1);
            g.setColor(fontColor);
            g.drawString(s, x1 - fm.stringWidth(s) / 2, y1 - 10);

            g.setColor(outColor);
            g.fillOval(x1 - dotRound - dotOuter, y1 - dotRound - dotOuter, (dotRound + dotOuter) * 2, (dotOuter + dotRound) * 2);
            g.setColor(dotColor);
            g.fillOval(x1 - dotRound, y1 - dotRound, dotRound * 2, dotRound * 2);


            if (i == valueList.size() - 1) {
                s = "" + valueList.get(i);
                g.setColor(fontColor);
                g.drawString(s, x2 - fm.stringWidth(s) / 2, y2 - 10);

                g.setColor(outColor);
                g.fillOval(x2 - dotRound - dotOuter, y2 - dotRound - dotOuter, (dotOuter + dotRound) * 2, (dotOuter + dotRound) * 2);
                g.setColor(dotColor);
                g.fillOval(x2 - dotRound, y2 - dotRound, dotRound * 2, dotRound * 2);
            }
        }



    }

    private void cal_xyList() {
        float height = (float) this.getHeight();
        float width = (float) this.getWidth();
        int max = max(valueList);
        int min = min(valueList);
        xList = new ArrayList<>();
        yList = new ArrayList<>();
//        System.out.println(height);
        for (int i = 0; i < valueList.size(); ++i) {
            int value = valueList.get(i);
            xList.add((int) (i * width * 0.9 / (valueList.size() - 1) + width * 0.05));
//            int y = getHeight() - (int) (height * 0.8 * (value - min) / (max - min) + getHeight()/20 )  ;
            int y = getHeight() - (int) (height * 0.8 * (value - min) / (max - min)) - getHeight() / 20;
//            System.out.println(y);
            yList.add(y);
        }
    }

    private void slowDraw(int x1, int y1, int x2, int y2, Graphics g, int end_x) {

        // y = ax +b
        float a = (y2 - y1) / (float) (x2 - x1);
        float b = y1 - a * x1;
        int x = x1;
        while (x < x2 && x < end_x) {
            int y = (int) (a * x + b);
            int xx = x + 1;
            int yy = (int) (a * xx + b);

            g.drawLine(x, y, xx, yy);
//                for (int i = 0; i <= Math.abs(a); ++i) {
//                        g.drawLine(x, y - i + j, x, y - i + j);
//                    }


            ++x;
        }

    }

    public void animate() {
        new Thread() {
            @Override
            public void run() {
                drawing = true;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (endX = 0; endX < getWidth(); ++endX) {
                    repaint();
                    try {
                        Thread.sleep(  millSeconds * (endX +1 )  / getWidth() - millSeconds * endX / getWidth());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                drawing = false;
            }
        }.start();
    }


    private int min(ArrayList<Integer> valueList) {
        int min = Integer.MAX_VALUE;
        for (int integer : valueList) {
            min = (min < integer) ? min : integer;
        }
        return min;
    }

    private int max(ArrayList<Integer> list) {
        int max = 0;
        for (int integer : list) {
            max = (max > integer) ? max : integer;
        }
        return max;
    }

    public static void main(String[] args) {
        ArrayList<Integer> rankList = new ArrayList<>();
        rankList.add(0);
        rankList.add(0);
        rankList.add(0);
        rankList.add(0);
        rankList.add(0);
        JFrame frame = new JFrame("DrawLine");
        final MyXYLine drawPanel = new MyXYLine(rankList);
        frame.getContentPane().add(drawPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setVisible(true);
//        drawPanel.animate();
    }

}





