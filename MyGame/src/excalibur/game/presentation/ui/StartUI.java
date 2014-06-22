package excalibur.game.presentation.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import excalibur.game.logic.networkadapter.Client;
import excalibur.game.logic.networkadapter.WaitingRoom;
import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.logic.syslogic.DataUtils.Shipin;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.MyJButton;
import excalibur.game.presentation.tools.Animator;
import excalibur.game.presentation.tools.FontLoader;
import excalibur.game.presentation.tools.StageController;

public class StartUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int shift = Constant.Config.HEIGHT;
	StageController controller;
	public JPanel menuPanel;
	JLabel bgLabel;
	JLabel levelLabel;
	MyJButton startBt;
	MyJButton paihangBt;
	MyJButton settingsBt;
	MyJButton exitBt;
	//控制动画播放
	Animator animator ;
	//是否进入游戏模式选择
	boolean isSelectingModel=false;
	static JLabel goldNumLabel;
	static JLabel levelNumLabel;
	public StartUI() {
		
		// 关闭
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 大小
		setSize(Constant.Config.WIDTH, Constant.Config.HEIGHT);
		// 不可伸缩
		setResizable(false);
		setLocationRelativeTo(null);
		// 标题
		setTitle(Constant.Config.title);
		// 背景
		ImageIcon menuBg = new ImageIcon(Constant.PictureSrc.mainBg);
		bgLabel = new JLabel(menuBg);
		bgLabel.setBounds(0, -shift, menuBg.getIconWidth(),
				menuBg.getIconHeight());
		getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
		JPanel jp = (JPanel) this.getContentPane();
		jp.setOpaque(false);
		String cursorStr = "";
		Shipin[] shipins = DataUtils.getInstance().getSelectShipin();
		boolean hasShipinBlock=false;
		for (int i = 0; i < shipins.length; i++) {
			if (shipins[i]!=null&&shipins[i].type==2) {
				hasShipinBlock=true;
				cursorStr = Constant.PictureSrc.IMGSRC+"cursor/"+shipins[i].name+".PNG";
				break;
			} 
		}
		if (!hasShipinBlock) {
			cursorStr = Constant.PictureSrc.IMGSRC+"cursor/"+"default"+".PNG";
		}
		Image cursorImage = Toolkit.getDefaultToolkit().getImage(cursorStr);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,new Point(0, 0), "mycursor");
		

		// 按钮Panel
		menuPanel = new JPanel();
		menuPanel.setOpaque(false);
		menuPanel.setLayout(null);
		menuPanel
				.setBounds(0, 0, Constant.Config.WIDTH, Constant.Config.HEIGHT);
		setCursor(cursor);

		// 排行榜
		ImageIcon paihangBg = new ImageIcon(Constant.PictureSrc.paiHangBt);
		paihangBt = new MyJButton(paihangBg);
		paihangBt.setBounds(Constant.UILocation.paihangX,
				Constant.UILocation.paihangY, paihangBg.getIconWidth(),
				paihangBg.getIconHeight());
		paihangBt.addMouseListener(new paihangListener());
		menuPanel.add(paihangBt);

		// 开始游戏
		ImageIcon startBg = new ImageIcon(Constant.PictureSrc.startBt);
		startBt = new MyJButton(startBg);
		startBt.setBounds(Constant.UILocation.startX,
				Constant.UILocation.startY, startBg.getIconWidth(),
				startBg.getIconHeight());
		startBt.addMouseListener(new StartGameListener());
		menuPanel.add(startBt);

		// 设置
		ImageIcon settingsBg = new ImageIcon(Constant.PictureSrc.settingsBt);
		settingsBt = new MyJButton(settingsBg);
		settingsBt.setBounds(Constant.UILocation.settingsX,
				Constant.UILocation.settingsY, settingsBg.getIconWidth(),
				settingsBg.getIconHeight());
		settingsBt.addMouseListener(new SettingListener());
		menuPanel.add(settingsBt);

		// 退出
		ImageIcon exitBg = new ImageIcon(Constant.PictureSrc.exitBt);
		exitBt = new MyJButton(exitBg);
		exitBt.setBounds(Constant.UILocation.exitX, Constant.UILocation.exitY,
				exitBg.getIconWidth(), exitBg.getIconHeight());
		exitBt.addMouseListener(new ExitListener());
		menuPanel.add(exitBt);
		
		ImageIcon levelBgIcon = new ImageIcon(Constant.PictureSrc.levelBg);
		levelLabel = new JLabel();
		levelLabel.setIcon(levelBgIcon);
		levelLabel.setBounds(Constant.UILocation.levelX,Constant.UILocation.levelY,levelBgIcon.getIconWidth(),levelBgIcon.getIconHeight());
		
		goldNumLabel = new JLabel();
		goldNumLabel.setBounds(Constant.UILocation.levelX+20,Constant.UILocation.levelY+40,50,20);
		goldNumLabel.setText(10000+"");
		goldNumLabel.setFont(FontLoader.loadFont("youyuan.ttf",15));
		goldNumLabel.setForeground(Color.white);
		
		
		levelNumLabel = new JLabel();
		levelNumLabel.setBounds(Constant.UILocation.levelX+80,Constant.UILocation.levelY+75,50,20);
		levelNumLabel.setText(2+"");
		levelNumLabel.setFont(FontLoader.loadFont("youyuan.ttf",20));
		levelNumLabel.setForeground(Color.white);
		add(levelNumLabel);
		add(goldNumLabel);
		add(levelLabel);

		add(menuPanel);

		// setUndecorated(true);
		setVisible(true);
		
		controller = new StageController(this);
		setLevelAndNum();
		
		
//		Client.launchConnection();
//		Client.writeMyScore();
//		Client.getHighestScores();
		
		 addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent e) {
//			    int a = DialogCreator.twoButtonDialog("温馨提示", "您确定要退出游戏吗？", "确定", "取消");
//			    if (a == 1) {  
			    	Client.close();
			     System.exit(0);  //关闭
//			    }
			   } 
			  });
	
		 
//		 redrock open background sound
//		 Media.playBGM(Media.MAIN);
	}
	public static void setLevelAndNum()
	{
		levelNumLabel.setText(DataUtils.getInstance().getLevel()+"");
		goldNumLabel.setText(DataUtils.getInstance().getGold()+"");
	}
	class paihangListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Media.playSound(Media.ENTER);
			if (!isSelectingModel){
				StageController.moveToStage(new RankingPanel());
			} else {
				Client.launchConnection();
				WaitingRoom waitingRoom = Client.getWaitingRoom();
				waitingRoom.enterRoom(0);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (!isSelectingModel){
				paihangBt.setIcon(new ImageIcon(
						Constant.PictureSrc.paiHangBt_Entered));	
			}
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (!isSelectingModel){
				paihangBt.setIcon(new ImageIcon(Constant.PictureSrc.paiHangBt));
			} 
		}

	}

	class StartGameListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			if(!isSelectingModel){
				startBt.setIcon(new ImageIcon(Constant.PictureSrc.startBt_Entered));
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			Media.playSound(Media.ENTER);
			if (!isSelectingModel){
				isSelectingModel=true;
				animator = new Animator();
				animator.setAnimation(Constant.PictureSrc.startBt_Ani, startBt);
				animator.setAnimation(Constant.PictureSrc.paiHangBt_Ani, paihangBt);
				animator.setAnimation(Constant.PictureSrc.settingsBt_Ani, settingsBt);
				animator.setAnimation(Constant.PictureSrc.exit_Ani, exitBt);
				animator.startAnimation(30,false);	
			} else {
//				controller.moveToStage(new SingleGameBoard());
				StageController.moveToStage(new ItemChoosePanel());
			}
		}
		
		public void mouseExited(MouseEvent e){
			if(!isSelectingModel){
				startBt.setIcon(new ImageIcon(Constant.PictureSrc.startBt));
			}
		}
	}
	
	class ExitListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Media.playSound(Media.ENTER);
			if (!isSelectingModel){
				System.exit(0);
				Client.close();
			} else {
				animator.startAnimation(30,true);
				isSelectingModel=false;
			}
		}
		
		public void mouseEntered(MouseEvent e){
			if(!isSelectingModel){
				exitBt.setIcon(new ImageIcon(Constant.PictureSrc.exit_Entered));
			}else{
				
			}
		}
		
		public void mouseExited(MouseEvent e){
			if(!isSelectingModel){
				exitBt.setIcon(new ImageIcon(Constant.PictureSrc.exitBt));
			}
		}
	}
	
	class SettingListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			Media.playSound(Media.ENTER);
			if (!isSelectingModel){
				StageController.moveToStage(new PreferencePanel());
			} else {
				StageController.moveToStage(new EndlessPanel());
			}
		}
		
		public void mouseEntered(MouseEvent e){
			if(!isSelectingModel){
				settingsBt.setIcon(new ImageIcon(Constant.PictureSrc.settingsBt_Entered));
			}else{
				
			}
		}
		
		public void mouseExited(MouseEvent e){
			if(!isSelectingModel){
				settingsBt.setIcon(new ImageIcon(Constant.PictureSrc.settingsBt));
				
			}
		}
	}
	
}
