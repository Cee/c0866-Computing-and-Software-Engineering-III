package excalibur.game.presentation.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.MyJButton;
import excalibur.game.presentation.tools.FontLoader;
import excalibur.game.presentation.tools.StageController;

public class EndlessPanel extends JPanel
{
	JButton buttonContinue;
	JButton buttonRestart;
	JButton btMyItems;
	
	/**
	 * Create the panel.
	 */
	public EndlessPanel()
	{
		Font font=FontLoader.loadFont(Constant.FontSrc.ZHUNYUAN,20);
		
		setBounds(0,0,800,600);
		setLayout(null);
		
		// add background picture
		JLabel backgroundLabel=new JLabel(new ImageIcon(Constant.PictureSrc.endless_background));
		backgroundLabel.setBounds(0,0,800,600);
		add(backgroundLabel,-1);
		
		buttonContinue=new MyJButton(new ImageIcon(Constant.PictureSrc.endless_continue));
		buttonContinue.setBounds(332,163,133,133);
		add(buttonContinue,0);
		buttonContinue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Media.playSound(Media.ENTER);
				Media.playBGM(Media.GAME);
				StageController.moveToStage(new EndlessGameBoard(DataUtils.getInstance().getLastRecord()));

			}
		});
		
		buttonRestart=new MyJButton(new ImageIcon(Constant.PictureSrc.endless_restart));
		buttonRestart.setBounds(185,235,97,97);
		add(buttonRestart,0);
		buttonRestart.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				Media.playSound(Media.ENTER);
				Media.playBGM(Media.GAME);
				StageController.moveToStage(new EndlessGameBoard());
			}
		});
		
		btMyItems=new MyJButton(new ImageIcon(Constant.PictureSrc.endless_mydecoration));
		btMyItems.setBounds(498,250,102,102);
		add(btMyItems,0);
		btMyItems.addActionListener(new ActionListener()
		{
			
			@Override public void actionPerformed(ActionEvent e)
			{
				Media.playSound(Media.ENTER);
				StageController.moveToStage(new MyItemPanel());
			}
		});
		
		JLabel currentLevelLabel=new JLabel("当前关卡",JLabel.CENTER);
		currentLevelLabel.setBounds(315,438,167,29);
		currentLevelLabel.setFont(font);
		currentLevelLabel.setForeground(Color.BLACK);
		add(currentLevelLabel,0);
		
		DataUtils dataUtils=DataUtils.getInstance();
		int lastLevel=dataUtils.getLastRecord();
		int lastTimeRemained=dataUtils.getRemainTime();
		JLabel levelAndTimeLabel=new JLabel("第"+Integer.toString(lastLevel)+"关　剩余"+Integer.toString(lastTimeRemained)+"秒",JLabel.CENTER);
		levelAndTimeLabel.setBounds(302,466,196,35);
		levelAndTimeLabel.setFont(font);
		levelAndTimeLabel.setForeground(new Color(255,149,161));
		add(levelAndTimeLabel,0);
		
		int topLevel=dataUtils.getBestRecord();
		JLabel peakLabel=new JLabel("战绩巅峰："+Integer.toString(topLevel),JLabel.LEFT);
		peakLabel.setBounds(93,517,218,41);
		peakLabel.setFont(font);
		peakLabel.setForeground(new Color(188,188,188));
		add(peakLabel,0);
		
		ImageIcon backIcon=new ImageIcon(Constant.PictureSrc.backBt);
		MyJButton backBt=new MyJButton(backIcon);
		backBt.setBounds(Constant.UILocation.returnX,Constant.UILocation.returnY,backIcon.getIconWidth(),backIcon.getIconHeight());
		backBt.addMouseListener(new BackListener());
		add(backBt,0);
		
	}
	
	class BackListener extends MouseAdapter
	{
		
		@Override public void mouseClicked(MouseEvent e)
		{
			Media.playSound(Media.RETURN);
			StageController.returnToMain(EndlessPanel.this);
		}
		
		@Override public void mouseEntered(MouseEvent e)
		{
			super.mouseEntered(e);
		}
		
		@Override public void mouseExited(MouseEvent e)
		{
			super.mouseExited(e);
		}
		
	}
}
