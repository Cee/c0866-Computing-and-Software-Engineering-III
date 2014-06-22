package excalibur.game.presentation.tools;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Xc on 2014/5/16.
 */
public class AnimateController {
    private ArrayList<? extends JComponent> panels;
    private int totalTime;
    private int tag;
    private Thread backThread;
    private Thread startThread;

    public AnimateController(final ArrayList<? extends JComponent> panels, int totalTime) {
        this.panels = panels;
        this.totalTime = totalTime;

    }

    public void start() {
        try {
            backThread.interrupt();
        } catch (NullPointerException e) {

        }
        startThread = new StartThread();
        startThread.start();

    }

    public void back() {
        try {
            startThread.interrupt();
        } catch (NullPointerException e) {

        }
        backThread = new BackThread();

        backThread.start();
    }

    public MouseListener getMouseAdapter() {
        return new MouseAdapter() {
            boolean isIn = false;
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                back();
            }
        };
    }

    public class StartThread extends Thread {
        public void run() {
            int size = panels.size();
            for (int i = tag; i < size; ++i) {
                int time =  (totalTime * (i +1) )/ size- (totalTime * i )/ size;
                if (time > 0) {
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        break;
                    }
                }

                tag = i;
                panels.get(i).setVisible(true);
                if((i - 1) >= 0 ){
                    panels.get(i-1).setVisible(false);
//                    panels.get(i-1).repaint();
                }
//                refreshMethod.run();
//                panels.get(i).repaint();
            }
        }
    }

    class BackThread extends Thread {
        public void run() {
            int size = panels.size();
            for (int i = tag; i > 0; --i) {
                int time =  (totalTime * (i +1) )/ size- (totalTime * i )/ size;
                if (time > 0) {
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                tag = i;
                panels.get(i).setVisible(true);
                if((i + 1) < panels.size() ){
                    panels.get(i+1).setVisible(false);
//                    panels.get(i+1).repaint();
                }
//                panels.get(i).repaint();
            }
        }
    }
}

