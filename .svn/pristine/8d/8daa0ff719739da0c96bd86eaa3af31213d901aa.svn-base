package excalibur.game.presentation.ui;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.CirWithTex;
import excalibur.game.presentation.myuicomponent.MyJButton;
import excalibur.game.presentation.tools.FontLoader;
import excalibur.game.presentation.tools.StageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyItemPanel extends JPanel {

    final int shipinPanelX = 29;
    final int shipinPanelY = 56;
    private final Font dispFont = FontLoader.loadFont("youyuan.ttf",16);


    int isMoving = 0;
    int itemSize = 80;
    int goldColor = 0xffff97;

    ArrayList<DataUtils.Shipin> shipins = DataUtils.getInstance().getShipin();

    JButton savejb;
    JButton strengthenjb;

    JLabel goldCountCt = new JLabel(){
        @Override
        protected void paintComponent(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(new Color(goldColor));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setFont(FontLoader.loadFont("youyuan.ttf", 24));
            g.setColor(new Color(0xffffff));
            super.paintComponent(g);
        }
    };
//    CirWithTex goldCountCt = new CirWithTex(0xff7400, 50, 24, ""+DataUtils.getInstance().getGold(), 0, false);

    JLabel[] selectedlbs = new JLabel[3];
    ItemPanelHelper helper = new ItemPanelHelper(this);

    JLabel propBeforeStrengthenLabel = new JLabel();
    JLabel propAfterStrengthenLabel = new JLabel();



    /**
     * Create the panel.
     */
    public MyItemPanel() {
        setBounds(new Rectangle(0, 0, 800, 600));
        setLayout(null);
        setBackground(new Color(0xbef2ff));


        Rectangle[] itemRects = new Rectangle[12];

        int itemBgX = 79;
        int itemBgY = 261;



        int propWidth = 120;
        int propHeight = 176;

        Rectangle itemsBgRect = new Rectangle(itemBgX + 15, itemBgY, 370, 215);


        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                int index = i * 3 + j;
                itemRects[index] = new Rectangle(itemBgX +30  + j * 131, itemBgY+13 + i * 112, itemSize, itemSize);
            }
        }

        Rectangle strengthenRect = new Rectangle(256, 53, 130, 130);
        Rectangle saveRect = new Rectangle(552, 106, 70, 70);


        Rectangle[] selectedRect = new Rectangle[3];



        Rectangle selectedItemBgRect = new Rectangle(680, 175, 100, 311);


        for (int i = 0; i < 3; ++i) {
            selectedRect[i] = new Rectangle( 680 +10 , 175 + 100 *i +16 , itemSize, itemSize);
        }

        Rectangle propBeforeStrengthenRect = new Rectangle(120, 26, propWidth, propHeight);
        Rectangle propAfterStrengthenRect = new Rectangle(400, 26, propWidth, propHeight);

        Rectangle goldRect = new Rectangle(85, 480, 60, 60);
        Rectangle goldRect2 = new Rectangle(205, 480, 60, 60);
        Rectangle goldCountRect = new Rectangle(115, 480, 120, 60);


        propBeforeStrengthenLabel.setBounds(propBeforeStrengthenRect);
        propAfterStrengthenLabel.setBounds(propAfterStrengthenRect);
        propBeforeStrengthenLabel.setFont(dispFont);
        propAfterStrengthenLabel.setFont(dispFont);
        goldCountCt.setText("    " + DataUtils.getInstance().getGold());
        goldCountCt.setBounds(goldCountRect);

        //rects↑


        ImageIcon returnIcon = new ImageIcon(Constant.PictureSrc.backBt);
        JButton returnButton = new MyJButton(returnIcon);
        returnButton.setBounds(Constant.UILocation.returnX,
                Constant.UILocation.returnY, returnIcon.getIconWidth(),
                returnIcon.getIconHeight());


        JLabel itemsBgLb = new BgLabel();
        JLabel selectedItemBgLb = new BgLabel();
        JLabel afterStrenBgLb = new BgLabel("<html>强化后:<br><br><br><br><br><br><br><br><br><br></html>");
        afterStrenBgLb.setFont(dispFont);
        JLabel beforeStrenBglb = new BgLabel("<html>强化前:<br><br><br><br><br><br><br><br><br><br></html>");
        beforeStrenBglb.setFont(dispFont);

        itemsBgLb.setBounds(itemsBgRect);
        selectedItemBgLb.setBounds(selectedItemBgRect);
        beforeStrenBglb.setBounds(propBeforeStrengthenRect);
        afterStrenBgLb.setBounds(propAfterStrengthenRect);

        CirWithTex goldct = new CirWithTex(goldColor, 30, 24, "Gold", 0, false);
        CirWithTex goldct2 = new CirWithTex(goldColor, 30, 24, " ", 0, false);

        goldct.setBounds(goldRect);
        goldct2.setBounds(goldRect2);
        strengthenjb = new MyJButton(new CirWithTexIcon(0x94d1ff,60,40,"强化",0,false));
        strengthenjb.setDisabledIcon(new CirWithTexIcon(0x87a8c1,60,40,"强化",0,false));

        strengthenjb.setBounds(strengthenRect);
        strengthenjb.setEnabled(false);


        savejb = new MyJButton(new CirWithTexIcon(0x94d1ff,30,24,"保存",0,false));
        savejb.setDisabledIcon(new CirWithTexIcon(0x87a8c1,30,24,"保存",0,false));
        savejb.setBounds(saveRect);
        savejb.setEnabled(false);

        helper.selectedShipins = DataUtils.getInstance().getSelectShipin();
        for (int i = 0; i < 3; ++i) {
            selectedlbs[i] = new JLabel();
            selectedlbs[i].setBounds(selectedRect[i]);
            final int ii = i;
            selectedlbs[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    helper.unselect(ii);
                }
            });
        }
        for (int i = 0; i < 3; ++i) {
            if (helper.selectedShipins[i] != null) {
                selectedlbs[helper.selectedShipins[i].type].setIcon(new ImageIcon("img/decorate/" + helper.selectedShipins[i].name + ".png"));
            }
        }

        add(propAfterStrengthenLabel);
        add(propBeforeStrengthenLabel);
        add(strengthenjb);
        add(savejb);

        for (JLabel label : selectedlbs) {
            add(label);
        }


        for (int i = 0, j = 0; i < shipins.size(); ++i) {
            DataUtils.Shipin s = shipins.get(i);
            if (!s.isOwned) {
                continue;
            }
            MovingLabel label = new MovingLabel(new ImageIcon("img/decorate/" + s.name + ".png"), s);
            label.addMouseListener(label.getListener());
            label.setBounds(itemRects[j]);
            add(label);
            j++;
        }


        add(goldct);
        add(goldCountCt);
        add(goldct2);
        add(returnButton);
        add(itemsBgLb);
        add(selectedItemBgLb);
        add(beforeStrenBglb);
        add(afterStrenBgLb);

//addlisteners
        savejb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helper.save();
            }
        });
        strengthenjb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helper.strength();
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StageController.returnToMain(MyItemPanel.this);
                helper.saveItem();
            }
        });

    }

    class BgLabel extends JLabel {
        BgLabel(String s) {
            super(s);
        }

        BgLabel() {
            super();
        }

        protected void paintComponent(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g.setColor(new Color(0xddf7fd));
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g);
        }
    }

    ;


    class MovingLabel extends JLabel {
        int currentX;
        int currentY;

        int origineX;
        int origineY;


        int move = 1;
        int back = 2;

        int tag = move;

        DataUtils.Shipin shipin;

        public MovingLabel(ImageIcon r, DataUtils.Shipin s) {
            super(r);
            this.shipin = s;
        }


        @Override
        public void setBounds(Rectangle r) {
            super.setBounds(r);
            origineX = r.x;
            origineY = r.y;
            currentX = r.x;
            currentY = r.y;
        }

        public void moveTo(final int x, final int y) {
            isMoving += 1;
            new Thread() {

                @Override
                public void run() {
                    // x = ky + b
                    int copyofcurrentX = currentX;
                    int copyofcurrentY = currentY;
                    double k = (x - copyofcurrentX) / (double) (y - copyofcurrentY);

                    double yy = copyofcurrentY;
                    double xx = copyofcurrentX;
                    double b = xx - k * yy;
                    while (Math.abs(yy - y) > 1) {
                        MovingLabel.this.setLocation((int) xx, (int) yy);
                        yy += (yy > y) ? -1 : 1;
                        xx = k * yy + b;
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    MovingLabel.this.setLocation(x, y);
                    currentX = x;
                    currentY = y;
                    isMoving--;
                }

            }.start();

            tag = move + back - tag;
        }

        public void moveBack() {
            this.moveTo(origineX, origineY);
        }

        MouseListener getListener() {
            return new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.isMetaDown()) {
                        helper.select(MovingLabel.this);
                        return;
                    }
                    if (isMoving == 0) {
                        if (tag == back) {
                            helper.unSelectStrength();
                            return;
                        }
                        if (tag == move) {
                            helper.selectStrength(MovingLabel.this);
                        }
                    }
                }
            };
        }
    }

    public class CirWithTexIcon implements Icon {
        private int degree;
        private int color;
        private int round;
        private int fontSize;
        private String text;
        private boolean isChangeLine;
        private Map<Integer, Font> fontMap = new HashMap<>();

        public CirWithTexIcon(int color, int round, int fontSize, String text, int degree, boolean isChangeLine) {
            this.color = color;
            this.round = round;
            this.fontSize = fontSize;
            this.text = text;
            this.degree = degree;
            this.isChangeLine = isChangeLine;


        }

        public void setText(String text) {
            this.text = text;
            repaint();
        }


        Font getFont(int size) {
            if (fontMap.containsKey(size)) {

            } else {
                fontMap.put(size, FontLoader.loadFont("youyuan.ttf", size));
            }
            return fontMap.get(size);

        }

        @Override
        public void paintIcon(Component c, Graphics g, int xxx, int yyy) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D) g).scale(Math.cos(Math.PI * degree / 180), 1);
            g.setColor(new Color(color));
            g.fillOval((int) (round / Math.cos(Math.PI * degree / 180) - round), 0, 2 * round, 2 * round);

            g.setColor(new Color(0xffffff));

            g.setFont(getFont(fontSize));
            FontMetrics fm = g.getFontMetrics(); //取得FontMetrics类实体

            if (isChangeLine) {

                int x = (int) (round / Math.cos(Math.PI * degree / 180) - fm.stringWidth(text.substring(0, 2)) / 2);
                int x2 = (int) (round / Math.cos(Math.PI * degree / 180) - fm.stringWidth(text.substring(2, 4)) / 2);

                g.drawString(text.substring(0, 2), x, round - fm.getHeight() + fm.getAscent());
                g.drawString(text.substring(2, 4), x2, round + fm.getAscent());
            } else {
                int width = fm.stringWidth(text); //取得字符串宽度

                ((Graphics2D) g).drawString(text, (float) (round / Math.cos(Math.PI * degree / 180) - width / 2), round - fm.getHeight() / 2 + fm.getAscent());

            }
        }

        @Override
        public int getIconWidth() {
            return round * 2;
        }

        @Override
        public int getIconHeight() {
            return round * 2;
        }
    }
}
