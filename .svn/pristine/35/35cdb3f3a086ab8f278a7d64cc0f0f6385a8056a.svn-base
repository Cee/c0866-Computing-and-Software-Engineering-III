package excalibur.game.presentation.myuicomponent;

import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.tools.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Xc on 2014/5/23.
 */
public class rankingList extends JPanel {
    private ArrayList<String> nameList;
    private ArrayList<Integer> scoreList;

    private Font  font = FontLoader.loadFont("zhunyuan.ttf",30);



    public rankingList(ArrayList<String> nameList, ArrayList<Integer> scoreList) {
        this.nameList = nameList;
        this.scoreList = scoreList;
        setOpaque(false);

        setPreferredSize ( new Dimension(Constant.RankingPanel.lineWidth,26 * nameList.size() + 30));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int height = fm.getHeight();
        int blank = 3;
//        System.out.println(height + blank);
        for(int i = 0 ; i < nameList.size() ; ++i){
            int y = 30+ (height + blank) * i;
            int x = 15 ;
            g.drawString(String.format("%02d:   %s   %d",i+1,nameList.get(i),scoreList.get(i)),x,y);
        }
    }


}
