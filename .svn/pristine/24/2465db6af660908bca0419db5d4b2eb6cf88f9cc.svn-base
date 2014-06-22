package excalibur.game.presentation.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StageController {
	static JFrame mainFrame;
	public StageController(JFrame mainFrame){
		StageController.mainFrame = mainFrame;
	}
	
	public static void moveToStage(JPanel panel){
		mainFrame.add(panel,0);
		mainFrame.repaint();
	}
	
	public static void moveToStage(JPanel panel,JPanel oldPanel){
		mainFrame.remove(oldPanel);
		moveToStage(panel);
	}
	public static void returnToMain(JPanel panel){
		mainFrame.remove(panel);
		mainFrame.repaint();
	}

}
