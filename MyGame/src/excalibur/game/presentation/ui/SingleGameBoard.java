package excalibur.game.presentation.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.tools.Tool;

import excalibur.game.logic.BombTools;
import excalibur.game.logic.QuickRemindTools;
import excalibur.game.logic.ScoreUpTools;
import excalibur.game.logic.SuperModuleTools;
import excalibur.game.logic.TimeTool;
import excalibur.game.logic.Tools;
import excalibur.game.logic.gamelogic.SuperModelAdapter;
import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.logic.syslogic.DataUtils.Shipin;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.MyJButton;
import excalibur.game.presentation.tools.FontLoader;
import excalibur.game.presentation.tools.StageController;

public class SingleGameBoard extends JPanel implements SuperModelAdapter{
	protected PlayBoard playBoard;
	protected JLabel timeBarLabel;
	protected boolean isStop=false;
	protected JLabel scoreLabel;
	protected Boolean isSuper;
	protected ArrayList<Tools> toolList;
	ImageIcon bgIcon;
	ImageIcon superIcon;
	Font superFont[];
	JLabel superLabel;
	JLabel bgLabel;
	TimeThread timer;
	JLabel superBgLabel;
	private JLabel starLabel;

	public SingleGameBoard(ArrayList<Tools> toolList){
		superFont = new Font[10];
		for (int i = 0 ; i < 10 ; i++){
			superFont[i]=FontLoader.loadFont("Duck Tape.ttf", 10+3*i);
		}
		
		isSuper=false;
		this.toolList = toolList;
		setLayout(null);
		setBounds(0,0,Constant.Config.WIDTH,Constant.Config.HEIGHT);
	
	    scoreLabel = new JLabel("0");
		scoreLabel.setBounds(660, 100, 100, 50);
		scoreLabel.setFont(FontLoader.loadFont("zhunyuan.ttf", 20));
		superIcon = new ImageIcon(Constant.PictureSrc.superBg);
		
		
		Shipin[] shipins = DataUtils.getInstance().getSelectShipin();
		boolean hasShipinBlock=false;
		for (int i = 0; i < shipins.length; i++) {
			if (shipins[i]!=null&&shipins[i].type==0) {
				hasShipinBlock=true;
				bgIcon = new ImageIcon(Constant.PictureSrc.loadGameBg(shipins[i].name));
				break;
			} 
		}
		if (!hasShipinBlock) {
			bgIcon = new ImageIcon(Constant.PictureSrc.loadGameBg("default"));
		}
		
		
		bgLabel = new JLabel(bgIcon);
		bgLabel.setBounds(0,0,bgIcon.getIconWidth(),bgIcon.getIconHeight());
		
		
		ImageIcon timeBarIcon = new ImageIcon(Constant.PictureSrc.timeBar);
		timeBarLabel = new JLabel(timeBarIcon);
		timeBarLabel.setBounds(0,0,timeBarIcon.getIconWidth(),timeBarIcon.getIconHeight());

		superLabel = new JLabel();//显示连击数
		superLabel.setBounds(650,200,200,200);
		ImageIcon backIcon = new ImageIcon(Constant.PictureSrc.backBt);
		MyJButton backBt = new MyJButton(backIcon);
		backBt.setBounds(Constant.UILocation.returnX, Constant.UILocation.returnY,backIcon.getIconWidth(),backIcon.getIconHeight());
		backBt.addMouseListener(new BackListener());
		starLabel = new JLabel();
		ImageIcon starIcon = new ImageIcon(Constant.PictureSrc.star);
		starLabel.setBounds(350, 250, starIcon.getIconWidth(), starIcon.getIconHeight());
		starLabel.setIcon(starIcon);
		
		superBgLabel = new JLabel();
		superBgLabel.setBounds(100,5,superIcon.getIconWidth(),superIcon.getIconHeight());
		add(timeBarLabel);
		add(scoreLabel);
		add(backBt);
		add(superBgLabel);
		add(bgLabel);
		add(superLabel,0);
		setTools();
	}
	public void setTools(){
		ArrayList<Tools> allTools = new ArrayList<Tools>();
		allTools.add(new ScoreUpTools());
		allTools.add(new QuickRemindTools());
		allTools.add(new SuperModuleTools());
		allTools.add(new TimeTool());
		allTools.add(new BombTools());
		String[] itemSrc = new String[] { Constant.PictureSrc.item1,
				Constant.PictureSrc.item2, Constant.PictureSrc.item3,
				Constant.PictureSrc.item4, Constant.PictureSrc.item5,
				Constant.PictureSrc.item6 };
		String[] itemChosenSrc = new String[] { Constant.PictureSrc.item1_C,
				Constant.PictureSrc.item2_C, Constant.PictureSrc.item3_C,
				Constant.PictureSrc.item4_C, Constant.PictureSrc.item5_C,
				Constant.PictureSrc.item6_C };
		for (int i = 0 ; i < allTools.size();i++) {
				Tools tool = allTools.get(i);
				JLabel toolLabel = new JLabel();
				ImageIcon toolIcon = new ImageIcon(itemSrc[i]);
				toolLabel.setIcon(toolIcon);
				switch (tool.getName()) {
				case "time":		
					toolLabel.setBounds(20,380, toolIcon.getIconWidth(), toolIcon.getIconHeight());
					break;
				case "remind":
					toolLabel.setBounds(20,480,toolIcon.getIconWidth(), toolIcon.getIconHeight());
					break;
				case "super":
					toolLabel.setBounds(20,180,toolIcon.getIconWidth(), toolIcon.getIconHeight());
					break;
				case "bomb":
					toolLabel.setBounds(20,280,toolIcon.getIconWidth(), toolIcon.getIconHeight());
					break;
				case "score":
					toolLabel.setBounds(20,80,toolIcon.getIconWidth(), toolIcon.getIconHeight());

					break;
				default:
					break;
				}
				for (Tools selectedTool : toolList) {
					if (selectedTool.getName().equals(tool.getName())) {
						toolLabel.setIcon(new ImageIcon(itemChosenSrc[i]));
						break;
					}
				}
				add(toolLabel,0);
		}		
	}
	
	public void initPlayBoard(){
		playBoard =  new PlayBoard(scoreLabel,toolList);
		
		playBoard.setLocation(150,75);
		playBoard.setDelegate(this);
		
		add(playBoard,0);
		add(starLabel,0);
		starLabel.setVisible(false);
		startTiming();
		playBoard.setTimer(timer);
	}
	
	protected void startAddTimeAnimation(){
		new Thread(){
			public void run(){
				starLabel.setVisible(true);
				
				while (starLabel.getY()>=0) {
					starLabel.setLocation(starLabel.getX()+5, starLabel.getY()-5);
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				starLabel.setVisible(false);
				starLabel.setLocation(350, 250);

			}
		}.start();

	}
	
	class BackListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			backtomainMethod();
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
	
	protected void backtomainMethod(){
		Media.playSound(Media.ENTER);
		Media.playBGM(Media.MAIN);
		isStop=true;
		StageController.returnToMain(SingleGameBoard.this);
		
	}
	
	protected void startTiming(){
		timer = new TimeThread();
		new Thread(timer).start();

	}
	
	class TimeThread implements Runnable{
		double time;
		public void addTime(double d){
			time = time+d*200/60;
			startAddTimeAnimation();
			System.out.println("Add time"+d);
		}
		@Override
		public void run() {
			for (time=200;time>0;time--){
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timeBarLabel.setLocation(-Constant.Config.WIDTH/200*(int)(200-time), timeBarLabel.getY());			
				if (isStop){
					playBoard.timeUp(true);
					return;	
				}
			}
			playBoard.timeUp(false);
		}
	}


	@Override
	public void showSuperModel() {
		isSuper = true;
//		bgLabel.setIcon(superIcon);
		superBgLabel.setIcon(superIcon);
	}

	@Override
	public void hideSuperModel() {
		isSuper = false;
//		bgLabel.setIcon(bgIcon);
		superBgLabel.setIcon(null);
	}

	@Override
	public void showCombos(final int combo) {
		new Thread(){
			public void run(){
				if (combo==0) {
					superLabel.setText("");
					return;
				}
				if (isSuper) {
					superLabel.setForeground(Color.white);
				} else {
					superLabel.setForeground(Color.black);

				}
				superLabel.setText(combo+"");
				for (int i = 0 ; i < 10 ; i++){
					superLabel.setFont(superFont[i]);
					try {
						Thread.sleep(10+i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

	@Override
	public void backtoMain() {
		// TODO Auto-generated method stub
		Media.playBGM(Media.MAIN);
		StageController.returnToMain(SingleGameBoard.this);
	}

	@Override
	public void restart() {
		startTiming();
		playBoard.setTimer(timer);
		playBoard.restart();
	}
	
	
}
