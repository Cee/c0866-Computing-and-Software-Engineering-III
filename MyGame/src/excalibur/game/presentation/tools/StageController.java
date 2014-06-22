package excalibur.game.presentation.tools;

import javax.swing.JFrame;
import javax.swing.JPanel;

import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.ui.StartUI;

public class StageController {
	static JFrame mainFrame;
	static boolean isChanging;
	static MyLinkedStack<JPanel> panelStack;
	public StageController(JFrame mainFrame){
		StageController.mainFrame = mainFrame;
		isChanging = false;
		panelStack = new MyLinkedStack<>();
		panelStack.push(((StartUI)mainFrame).menuPanel);
	}
	
	/**
	 * 保留原界面，新界面从下方浮出
	 * @param panel
	 */
	public static void moveToStage(final JPanel panel){
		if (isChanging)
			return;
		isChanging = true;
		panel.setLocation(0,Constant.Config.HEIGHT);
		mainFrame.add(panel,0);
		mainFrame.repaint();
		new Thread(){
			public void run(){
				for (int i = Constant.Config.HEIGHT; i >=0; i=i-20){
					panel.setLocation(0, i);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				isChanging = false;
				if (!panelStack.isEmpty()) {
					JPanel oldPanel = panelStack.pop();
					oldPanel.setVisible(false);
					panelStack.push(oldPanel);
				}
				panelStack.push(panel);
			}
		}.start();
	}
	
	/**
	 * 去除原来界面 新界面从右侧出现
	 * @param panel
	 * @param oldPanel
	 */
	public static void moveToStage(final JPanel panel,final JPanel oldPanel){
		if (isChanging)
			return;
		isChanging = true;
		//mainFrame.remove(oldPanel);	
		panel.setLocation(Constant.Config.WIDTH,0);
		mainFrame.add(panel,0);
		mainFrame.repaint();
		new Thread(){
			public void run(){
				for (int i = Constant.Config.WIDTH; i >=0; i=i-20){
					panel.setLocation(i, 0);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				mainFrame.remove(oldPanel);
				panelStack.pop();
				panelStack.push(panel);
				isChanging = false;
			}
		}.start();
	}
	
	
	public static void returnToMain(final JPanel panel){
		if (isChanging)
			return;
		isChanging = true;
		panelStack.pop();
		JPanel oldPanel = panelStack.pop();
		if (oldPanel!=null){
			oldPanel.setVisible(true);
			StartUI.setLevelAndNum();
			panelStack.push(oldPanel);
		}
			
		new Thread(){
			public void run(){
				for (int i = 0; i <= Constant.Config.HEIGHT; i=i+20){
					panel.setLocation(0, i);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				mainFrame.remove(panel);
				mainFrame.repaint();
				isChanging = false;
			}
		}.start();
	}
	
	public static void returnToMain(final int jump){
		isChanging = true;
		new Thread(){
			public void run(){
				for (int i = 0 ; i < jump;i++){
					JPanel panel = panelStack.pop();
					JPanel oldPanel = panelStack.pop();
					if (oldPanel!=null){
						oldPanel.setVisible(true);
						panelStack.push(oldPanel);
					} else {
						break;
					}
					for (int j = 0; j <= Constant.Config.HEIGHT; j=j+20){
						panel.setLocation(0, j);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					mainFrame.remove(panel);
					mainFrame.repaint();
				}
				isChanging = false;
			}
		}.start();

		

	}
	

}
