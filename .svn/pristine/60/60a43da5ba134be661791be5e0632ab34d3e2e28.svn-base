package excalibur.game.presentation.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import excalibur.game.logic.Tools;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.MyJButton;

public class SingleGameBoard extends JPanel {
	PlayBoard playBoard;
	JLabel timeBarLabel;
	boolean isStop=false;
	public SingleGameBoard(ArrayList<Tools> toolList){
		setLayout(null);
		setBounds(0,0,Constant.Config.WIDTH,Constant.Config.HEIGHT);
	
		JLabel scoreLabel = new JLabel("0");
		scoreLabel.setBounds(660, 100, 100, 50);
			
		playBoard =  new PlayBoard(scoreLabel,toolList);
		playBoard.setLocation(150,75);
		
		ImageIcon bgIcon = new ImageIcon(Constant.PictureSrc.gameBg);
		JLabel bgLabel = new JLabel(bgIcon);
		bgLabel.setBounds(0,0,bgIcon.getIconWidth(),bgIcon.getIconHeight());
		
		
		ImageIcon timeBarIcon = new ImageIcon(Constant.PictureSrc.timeBar);
		timeBarLabel = new JLabel(timeBarIcon);
		timeBarLabel.setBounds(0,0,timeBarIcon.getIconWidth(),timeBarIcon.getIconHeight());
		
		ImageIcon backIcon = new ImageIcon(Constant.PictureSrc.backBt);
		MyJButton backBt = new MyJButton(backIcon);
		backBt.setBounds(Constant.UILocation.returnX, Constant.UILocation.returnY,backIcon.getIconWidth(),backIcon.getIconHeight());
		backBt.addMouseListener(new BackListener());
		add(timeBarLabel);
		add(scoreLabel);
		add(backBt);
		add(playBoard);
		add(bgLabel);
		for (Tools tool:toolList){
			tool.setPlayBoard(playBoard);
			tool.affect();
		}
		new Thread(new TimeThread()).start();
	}
	class BackListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			isStop=true;
			StageController.returnToMain(SingleGameBoard.this);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
		}
		
	}
	
	class TimeThread implements Runnable{
		@Override
		public void run() {
			for (int time=200; time>0; time--){
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timeBarLabel.setLocation(timeBarLabel.getX()-Constant.Config.WIDTH/200, timeBarLabel.getY());			
				
				if (isStop){
					playBoard.timeUp(false);
					return;
				}
			}
			playBoard.timeUp(false);
		}
	}
}
