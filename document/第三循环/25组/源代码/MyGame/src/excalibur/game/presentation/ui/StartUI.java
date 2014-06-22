package excalibur.game.presentation.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.MyJButton;
import excalibur.game.presentation.tools.Animator;

public class StartUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int shift = Constant.Config.HEIGHT;
	StageController controller;
	JPanel menuPanel;
	JLabel bgLabel;
	MyJButton startBt;
	MyJButton paihangBt;
	MyJButton settingsBt;
	MyJButton exitBt;
	//控制动画播放
	Animator animator ;
	//是否进入游戏模式选择
	boolean isSelectingModel=false;
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

		// 按钮Panel
		menuPanel = new JPanel();
		menuPanel.setOpaque(false);
		menuPanel.setLayout(null);
		menuPanel
				.setBounds(0, 0, Constant.Config.WIDTH, Constant.Config.HEIGHT);

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
		menuPanel.add(settingsBt);

		// 退出
		ImageIcon exitBg = new ImageIcon(Constant.PictureSrc.exitBt);
		exitBt = new MyJButton(exitBg);
		exitBt.setBounds(Constant.UILocation.exitX, Constant.UILocation.exitY,
				exitBg.getIconWidth(), exitBg.getIconHeight());
		exitBt.addMouseListener(new ExitListener());
		menuPanel.add(exitBt);
		

		add(menuPanel);

		// setUndecorated(true);
		setVisible(true);
		
		controller = new StageController(this);
	}

	class paihangListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isSelectingModel){
				StageController.moveToStage(new RankingPanel());
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
		}

		@Override
		public void mouseClicked(MouseEvent e) {
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
	}
	
	class ExitListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isSelectingModel){
				System.exit(0);
			} else {
				animator.startAnimation(30,true);
				isSelectingModel=false;
			}
		}
	}
	
	class SettingListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!isSelectingModel){
			} else {
			}
		}
	}
}
