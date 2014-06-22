package excalibur.game.presentation.ui;

import excalibur.game.logic.networkadapter.Client;
import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.*;
import excalibur.game.presentation.tools.AnimateController;
import excalibur.game.presentation.tools.StageController;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RankingPanel extends JPanel {
    private JLabel bgLabel;
    private MyJButton returnButton;
    private MyJButton dailyGameCountButton;
    private MyJButton scorePerGameButton;
    private MyJButton averageScoreButton;


    private CirWithTex totalGameCountLabel;
    private CirWithTex maxComboLabel;
    private CirWithTex maxScoreLabel;

    private ArrayList<CirWithTex> totalGameCountRcs = new ArrayList<>();
    private ArrayList<CirWithTex> maxComboRcs = new ArrayList<>();
    private ArrayList<CirWithTex> maxScoreRcs = new ArrayList<>();

    private AnimateController totalGameCountAc;
    private AnimateController maxComboAc;
    private AnimateController maxScoreAc;

    private int lineX = Constant.RankingPanel.lineX;
    private int lineY = Constant.RankingPanel.lineY;
    private int lineHeight = Constant.RankingPanel.lineHeight;
    private int lineWidth = Constant.RankingPanel.lineWidth;

    private JPanel currentXYLine;
    private MouseListener backToMenuListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            StageController.returnToMain(RankingPanel.this);
        }

    };

    private JScrollPane panel;

    private MouseListener backToMainListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            mainPage();
        }

    };

    public RankingPanel() {
        setLayout(null);
        try {
        	Client.launchConnection();
            Client.writeMyScore();
            Client.getHighestScores();
		} catch (Exception e) {
		}
        
        setBounds(0, 0, Constant.Config.WIDTH, Constant.Config.HEIGHT);

        setComponents();
        addAnimateComponents();
        addComponents();


        addListeners();
        addAnimates();


        //添加左侧部件
        addLeftComponents();
        addBackground();

        mainPage();
    }

    private void addAnimateComponents() {


        //总局数
        int round = Constant.RankingPanel.gameCountRound;
        int x = Constant.RankingPanel.gameCountPosX;
        int y = Constant.RankingPanel.gameCountPosY;
        int color = Constant.RankingPanel.gameCountColor;

        for (int i = 0; i <= 90; i += 5) {
            CirWithTex totalGameCountCt = new CirWithTex(color, round, Constant.RankingPanel.gameCountFontSize, "总局数", i, false);
            totalGameCountRcs.add(totalGameCountCt);
            totalGameCountCt.setVisible(false);
            totalGameCountCt.setBounds(x, y, round * 2, round * 2);
            add(totalGameCountCt, 0);
        }
        for (int i = 85; i >= 0; i -= 5) {
            CirWithTex totalGameCountCt = new CirWithTex(color, round, Constant.RankingPanel.gameCountFontSizeBack, "" + DataUtils.getInstance().getGameCount(), i, false);
            totalGameCountRcs.add(totalGameCountCt);
            totalGameCountCt.setVisible(false);
            totalGameCountCt.setBounds(x, y, round * 2, round * 2);
            add(totalGameCountCt, 0);
        }
        totalGameCountAc = new AnimateController(totalGameCountRcs, Constant.RankingPanel.animateTime);


        //最高得分
        round = Constant.RankingPanel.maxScoreRound;
        x = Constant.RankingPanel.maxScoreX;
        y = Constant.RankingPanel.maxScoreY;
        color = Constant.RankingPanel.maxScoreColor;
        for (int i = 0; i <= 90; i += 5) {
            CirWithTex maxScoreRc = new CirWithTex(color, round, Constant.RankingPanel.maxScoreFontSize, "最高得分", i, true);
            maxScoreRcs.add(maxScoreRc);
            maxScoreRc.setVisible(false);
            maxScoreRc.setBounds(x, y, round * 2, round * 2);
            add(maxScoreRc, 0);
        }
        for (int i = 85; i >= 0; i -= 5) {
            CirWithTex maxScoreRc = new CirWithTex(color, round, Constant.RankingPanel.maxScoreFontSizeBack, "" + DataUtils.getInstance().getMaxScore(), i, false);
            maxScoreRcs.add(maxScoreRc);
            maxScoreRc.setVisible(false);
            maxScoreRc.setBounds(x, y, round * 2, round * 2);
            add(maxScoreRc, 0);
        }
        maxScoreAc = new AnimateController(maxScoreRcs, Constant.RankingPanel.animateTime);


        //最高连击
        round = Constant.RankingPanel.maxComboRound;
        x = Constant.RankingPanel.maxComboX;
        y = Constant.RankingPanel.maxComboY;
        color = Constant.RankingPanel.maxComboColor;
        for (int i = 0; i <= 90; i += 5) {
            CirWithTex maxComboRc = new CirWithTex(color, round, Constant.RankingPanel.maxComboFontSize, "最高连击", i, true);
            maxComboRcs.add(maxComboRc);
            maxComboRc.setVisible(false);
            maxComboRc.setBounds(x, y, round * 2, round * 2);
            add(maxComboRc, 0);
        }
        for (int i = 85; i >= 0; i -= 5) {
            CirWithTex maxComboRc = new CirWithTex(color, round, Constant.RankingPanel.maxComboFontSizeBack, "" + DataUtils.getInstance().getMaxCombo(), i, false);
            maxComboRcs.add(maxComboRc);
            maxComboRc.setVisible(false);
            maxComboRc.setBounds(x, y, round * 2, round * 2);
            add(maxComboRc, 0);
        }
        maxComboAc = new AnimateController(maxComboRcs, Constant.RankingPanel.animateTime);

        totalGameCountRcs.get(0).setVisible(true);
        totalGameCountRcs.get(0).repaint();

        maxComboRcs.get(0).setVisible(true);
        maxComboRcs.get(0).repaint();


        maxScoreRcs.get(0).setVisible(true);
        maxScoreRcs.get(0).repaint();

    }

    private void addAnimates() {
        totalGameCountLabel.addMouseListener(totalGameCountAc.getMouseAdapter());


        maxScoreLabel.addMouseListener(maxScoreAc.getMouseAdapter());

        maxComboLabel.addMouseListener(maxComboAc.getMouseAdapter());
    }

    private void setComponents() {
        //背景
        ImageIcon bgIcon = new ImageIcon(Constant.PictureSrc.rankBg);
        bgLabel = new JLabel(bgIcon);
        bgLabel.setBounds(0, 0, bgIcon.getIconWidth(), bgIcon.getIconHeight());


        // 添加返回按钮
        ImageIcon returnIcon = new ImageIcon(Constant.PictureSrc.backBt);
        returnButton = new MyJButton(returnIcon);
        returnButton.setBounds(Constant.UILocation.returnX,
                Constant.UILocation.returnY, returnIcon.getIconWidth(),
                returnIcon.getIconHeight());

        dailyGameCountButton = new MyJButton(Constant.PictureSrc.dailyGameCountIcon, 1);
        dailyGameCountButton.setBounds(Constant.RankingPanel.dailyGameCountX, Constant.RankingPanel.dailyGameCountY, dailyGameCountButton.getIcon().getIconWidth(), dailyGameCountButton.getIcon().getIconHeight());

        scorePerGameButton = new MyJButton(Constant.PictureSrc.scorePerGame, 1);
        scorePerGameButton.setBounds(Constant.RankingPanel.scorePerGameX, Constant.RankingPanel.scorePerGameY, scorePerGameButton.getIcon().getIconWidth(), scorePerGameButton.getIcon().getIconHeight());

        averageScoreButton = new MyJButton(Constant.PictureSrc.averageScorePerDay, 1);
        averageScoreButton.setBounds(Constant.RankingPanel.averageScoreX, Constant.RankingPanel.averageScoreY, averageScoreButton.getIcon().getIconWidth(), averageScoreButton.getIcon().getIconHeight());


        totalGameCountLabel = new CirWithTex(Constant.RankingPanel.gameCountColor, Constant.RankingPanel.gameCountRound, 22, "", 0, false); //
        totalGameCountLabel.setBounds(Constant.RankingPanel.gameCountPosX, Constant.RankingPanel.gameCountPosY, Constant.RankingPanel.gameCountRound * 2, Constant.RankingPanel.gameCountRound * 2);

        maxComboLabel = new CirWithTex(Constant.RankingPanel.maxComboColor, Constant.RankingPanel.maxComboRound, 26, "", 0, false); //最高连击
        maxComboLabel.setBounds(Constant.RankingPanel.maxComboX, Constant.RankingPanel.maxComboY, Constant.RankingPanel.maxComboRound * 2, Constant.RankingPanel.maxComboRound * 2);

        maxScoreLabel = new CirWithTex(Constant.RankingPanel.maxScoreColor, Constant.RankingPanel.maxScoreRound, 20, "", 0, false); //最高得分
        maxScoreLabel.setBounds(Constant.RankingPanel.maxScoreX, Constant.RankingPanel.maxScoreY, Constant.RankingPanel.maxScoreRound * 2, Constant.RankingPanel.maxScoreRound * 2);


    }


    private void addListeners() {
        returnButton.addMouseListener(backToMenuListener);

        dailyGameCountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dailyGameCount();
            }
        });

        scorePerGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scorePerGame();
            }
        });

        averageScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                averageScorePerDay();
            }
        });

        maxScoreLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                waigua();
            }
        });

    }

    private void addBackground() {
        add(bgLabel);
    }

    private void addComponents() {

        add(dailyGameCountButton);

        add(scorePerGameButton);

        add(averageScoreButton);

        add(returnButton);

        add(totalGameCountLabel);

        add(maxComboLabel);

        add(maxScoreLabel);


    }

    private void addLeftComponents() {
        //WAIGUA
//        final JButton waigua = new JButton("外挂");
//        waigua.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                waigua();
//            }
//        });
//        waigua.setBounds(100, 100, 50, 50);
//        add(waigua, 0);
    }


    private void waigua() {
        JFrame waiguaiFrame = new JFrame();
        final TextField tf1 = new TextField();
        final TextField tf2 = new TextField();
        final TextField tf3 = new TextField();

        tf1.setText("0");
        tf2.setText("20000");

        tf3.setText("10");

        JButton jb = new JButton("增加记录");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int day = Integer.parseInt(tf1.getText());
                final int socre = Integer.parseInt(tf2.getText());
                final int combo = Integer.parseInt(tf3.getText());

                DataUtils.getInstance().addRecordByWaiGua(day, socre, combo);
            }
        });

        JButton[] items = new JButton[6];
        for(int i = 0 ; i < 6 ; ++i){
            items[i] = new JButton("获得饰品"+(i+1));

            final int ci = i+1;
            items[i].addActionListener(new ActionListener() {
                @Override

                public void actionPerformed(ActionEvent e) {
                    DataUtils.getInstance().gainShipin("Unname"+ci);
                }
            });
        }

        waiguaiFrame.setLayout(new FlowLayout());
        waiguaiFrame.add(new JLabel("day:"));
        waiguaiFrame.add(tf1);
        waiguaiFrame.add(new JLabel("score:"));
        waiguaiFrame.add(tf2);
        waiguaiFrame.add(new JLabel("combo:"));
        waiguaiFrame.add(tf3);
        waiguaiFrame.add(jb);

        for (JButton onejb : items){
            waiguaiFrame.add(onejb);
        }

        waiguaiFrame.setSize(400, 300);


        waiguaiFrame.setLocationRelativeTo(null);
        waiguaiFrame.setVisible(true);



    }

    private void safeAdd(JPanel xyLine) {
        removeXyLine();
        currentXYLine = xyLine;
        add(currentXYLine, 0);

    }

    private void removeXyLine() {
        if (currentXYLine != null) {
            remove(currentXYLine);
            currentXYLine = null;
            repaint();
        }

        if (panel != null) {
            remove(panel);
            panel = null;
            repaint();

        }
    }

    private void dailyGameCount() {
        setBactToMain();
        ArrayList<Integer> dailyGameCountList = DataUtils.getInstance().getDailyGameCount(-10, 0);
        MyXYLine xyLine = new MyXYLine(dailyGameCountList);
        xyLine.setBounds(lineX, lineY, lineWidth, lineHeight);
        safeAdd(xyLine);
        repaint();
    }

    private void scorePerGame() {
        setBactToMain();
        ArrayList<Integer> scoreList = DataUtils.getInstance().getScorePerGame(7);
        MyXYLine xyLine = new MyXYLine(scoreList);
        xyLine.setBounds(lineX, lineY, lineWidth, lineHeight);
        safeAdd(xyLine);
        repaint();
    }

    private void averageScorePerDay() {
        setBactToMain();
        ArrayList<Integer> averageScoreList = DataUtils.getInstance().getAverageScorePerDay(-10, 0);
        MyXYLine xyLine = new MyXYLine(averageScoreList);
        xyLine.setBounds(lineX, lineY, lineWidth, lineHeight);
        safeAdd(xyLine);
        repaint();
    }

    private void setBactToMain() {
        MouseListener[] listeners = returnButton.getMouseListeners();
        for (MouseListener listener : listeners) {
            returnButton.removeMouseListener(listener);
        }

        returnButton.addMouseListener(backToMainListener);

    }

    private void setBackToMenu() {
        MouseListener[] listeners = returnButton.getMouseListeners();
        for (MouseListener listener : listeners) {
            returnButton.removeMouseListener(listener);
        }

        returnButton.addMouseListener(backToMenuListener);

    }

    private void mainPage() {
//        removeXyLine();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Integer> scoreList = new ArrayList<>();

        ArrayList<DataUtils.RankingTupe> rankingListData = DataUtils.getInstance().getRankingList();
        for (int i = 0; i < rankingListData.size(); ++i) {
            nameList.add(rankingListData.get(i).name);
            scoreList.add(rankingListData.get(i).score);
        }

        JPanel rankingList = new rankingList(nameList, scoreList);
        rankingList.setBounds(Constant.RankingPanel.xyRect);
//        panel = new JScrollPane(rankingList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        panel.setBounds(Constant.RankingPanel.xyRect);
//        panel.setOpaque(false);
//        panel.getViewport().setOpaque(false);
//        panel.setBorder(null);
//        panel.setVerticalScrollBar(new JScrollBar());
//        panel.setWheelScrollingEnabled(true);


        safeAdd(rankingList);

        repaint();
        setBackToMenu();

    }

}
