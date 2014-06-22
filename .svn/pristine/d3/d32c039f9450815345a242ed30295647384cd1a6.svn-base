package excalibur.game.presentation.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import excalibur.game.logic.BombTools;
import excalibur.game.logic.QuickRemindTools;
import excalibur.game.logic.ScoreUpTools;
import excalibur.game.logic.SuperModuleTools;
import excalibur.game.logic.TimeTool;
import excalibur.game.logic.Tools;
import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.MyJButton;
import excalibur.game.presentation.tools.StageController;

public class ItemChoosePanel extends JPanel {
	ArrayList<MyJButton> itemList;
	ArrayList<Tools> toolList;
	ArrayList<Tools> allTools;
	Tools randomTools;
	MyJButton returnButton;
	int currentMoney;
	MyJButton startButton;
	
	JLabel item1Hint;
	JLabel item2Hint;
	JLabel item3Hint;
	JLabel item4Hint;
	JLabel item5Hint;
	JLabel item6Hint;
	ImageIcon item1HIcon = new ImageIcon(Constant.PictureSrc.item1_H);
	ImageIcon item2HIcon = new ImageIcon(Constant.PictureSrc.item2_H);
	ImageIcon item3HIcon = new ImageIcon(Constant.PictureSrc.item3_H);
	ImageIcon item4HIcon = new ImageIcon(Constant.PictureSrc.item4_H);
	ImageIcon item5HIcon = new ImageIcon(Constant.PictureSrc.item5_H);
	ImageIcon item6HIcon = new ImageIcon(Constant.PictureSrc.item6_H);
	
	String[] itemSrc = new String[] { Constant.PictureSrc.item1,
			Constant.PictureSrc.item2, Constant.PictureSrc.item3,
			Constant.PictureSrc.item4, Constant.PictureSrc.item5,
			Constant.PictureSrc.item6 };
	String[] itemChosenSrc = new String[] { Constant.PictureSrc.item1_C,
			Constant.PictureSrc.item2_C, Constant.PictureSrc.item3_C,
			Constant.PictureSrc.item4_C, Constant.PictureSrc.item5_C,
			Constant.PictureSrc.item6_C };
	public ItemChoosePanel() {
		itemList = new ArrayList<MyJButton>();
		toolList = new ArrayList<Tools>();
		allTools = new ArrayList<Tools>();
		allTools.add(new ScoreUpTools());
		allTools.add(new QuickRemindTools());
		allTools.add(new SuperModuleTools());
		allTools.add(new TimeTool());
		allTools.add(new BombTools());
		
		setLayout(null);
		setBounds(0, 0, Constant.Config.WIDTH, Constant.Config.HEIGHT);
		setBackground(new Color(220, 245, 254));
		// 添加道具按钮
		
		int[] itemX = new int[] { Constant.UILocation.item1X,
				Constant.UILocation.item2X, Constant.UILocation.item3X,
				Constant.UILocation.item4X, Constant.UILocation.item5X,
				Constant.UILocation.item6X };
		int[] itemY = new int[] { Constant.UILocation.item1Y,
				Constant.UILocation.item2Y, Constant.UILocation.item3Y,
				Constant.UILocation.item4Y, Constant.UILocation.item5Y,
				Constant.UILocation.item6Y };
		      
		for (int i = 0; i < itemSrc.length; i++) {
			ImageIcon icon = new ImageIcon(itemSrc[i]);
			MyJButton button = new MyJButton(icon);
			button.setBounds(itemX[i], itemY[i], icon.getIconWidth(),
					icon.getIconHeight());
			itemList.add(button);
			add(button);
		}

		// 添加开始按钮
		ImageIcon startIcon = new ImageIcon(Constant.PictureSrc.gameStart);
	    startButton = new MyJButton(startIcon);
		startButton.setBounds(Constant.UILocation.itemStartX,
				Constant.UILocation.itemStartY, startIcon.getIconWidth(),
				startIcon.getIconHeight());
		add(startButton);
		
		//添加hint标签
		item1Hint = new JLabel();
		item1Hint.setBounds(60,120,item1HIcon.getIconWidth(),item1HIcon.getIconHeight());
		add(item1Hint);
		item2Hint = new JLabel();
		item2Hint.setBounds(10,280,item2HIcon.getIconWidth(),item2HIcon.getIconHeight());
		add(item2Hint);
		item3Hint = new JLabel();
		item3Hint.setBounds(120,390,item3HIcon.getIconWidth(),item3HIcon.getIconHeight());
		add(item3Hint);
		item4Hint = new JLabel();
		item4Hint.setBounds(580,120,item4HIcon.getIconWidth(),item4HIcon.getIconHeight());
		add(item4Hint);
		item5Hint = new JLabel();
		item5Hint.setBounds(600,260,item5HIcon.getIconWidth(),item5HIcon.getIconHeight());
		add(item5Hint);
		item6Hint = new JLabel();
		item6Hint.setBounds(590,400,item6HIcon.getIconWidth(),item6HIcon.getIconHeight());
		add(item6Hint);
		
		// 添加返回按钮
		ImageIcon returnIcon = new ImageIcon(Constant.PictureSrc.backBt);
	    returnButton = new MyJButton(returnIcon);
		returnButton.setBounds(Constant.UILocation.returnX,
				Constant.UILocation.returnY, returnIcon.getIconWidth(),
				returnIcon.getIconHeight());
		add(returnButton);
		addListener();
		currentMoney = DataUtils.getInstance().getGold();
	}
	protected void addListener(){
		returnButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Media.playSound(Media.ENTER);
				StageController.returnToMain(ItemChoosePanel.this);
			}
			
		});
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Media.playSound(Media.ENTER);
				SingleGameBoard singleGameBoard = new SingleGameBoard(toolList);
				singleGameBoard.initPlayBoard();
//				Media.stopBGM(Media.MAIN);
				Media.playBGM(Media.GAME);
 				StageController.moveToStage(singleGameBoard,ItemChoosePanel.this);
			}
		});
		ItemChooseListener listener = new ItemChooseListener();
		for (int i = 0; i < itemList.size(); i++) {
			itemList.get(i).addMouseListener(listener);
		}

	}
	
	class ItemChooseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			Media.playSound(Media.ENTER);
			MyJButton selectedButton = (MyJButton)e.getSource();
			int index = itemList.indexOf(selectedButton);
			if (index>=0&&index<5) {
				if (selectedButton.isSure()) {
					if (currentMoney<allTools.get(index).getGold()) {
						return;
					} else {
						currentMoney = currentMoney-allTools.get(index).getGold();
						allTools.get(index).setImageName(itemChosenSrc[index]);
						toolList.add(allTools.get(index));
						selectedButton.setIcon(new ImageIcon(itemChosenSrc[index]));
						selectedButton.setSure(!selectedButton.isSure());
					}
				}else {
					selectedButton.setSure(!selectedButton.isSure());
					currentMoney = currentMoney+allTools.get(index).getGold();
					toolList.remove(allTools.get(index));
					selectedButton.setIcon(new ImageIcon(itemSrc[index]));

				}
			} else if (index==5){
				if (selectedButton.isSure()) {
					if (currentMoney<1000) {
						return;
					} else{
						currentMoney = currentMoney-1000;
						int randomIndex = (int)(Math.random()*4);
						randomTools = allTools.get(randomIndex);
						randomTools.setImageName(itemChosenSrc[randomIndex]);
						selectedButton.setIcon(new ImageIcon(itemChosenSrc[index]));
						toolList.add(randomTools);
						selectedButton.setSure(!selectedButton.isSure());
					}
				} else {
					currentMoney = currentMoney+1000;
					selectedButton.setIcon(new ImageIcon(itemSrc[index]));
					toolList.remove(randomTools);
					selectedButton.setSure(!selectedButton.isSure());
				}
			}
		}
		
		public void mouseEntered(MouseEvent e){
			MyJButton selectedButton = (MyJButton)e.getSource();
			selectedButton.setSure(!selectedButton.isSure());
			int index = itemList.indexOf(selectedButton);
			switch (index) {
			case 0:
				item1Hint.setIcon(item1HIcon);
				break;
			case 1:
				item2Hint.setIcon(item2HIcon);
				break;
			case 2:
				item3Hint.setIcon(item3HIcon);
				break;
			case 3:
				item4Hint.setIcon(item4HIcon);
				break;
			case 4:
				item5Hint.setIcon(item5HIcon);
				break;
			case 5:
				item6Hint.setIcon(item6HIcon);
				break;
			default:
				break;
			}
		}
		
		public void mouseExited(MouseEvent e){
			MyJButton selectedButton = (MyJButton)e.getSource();
			selectedButton.setSure(!selectedButton.isSure());
			int index = itemList.indexOf(selectedButton);
			switch (index) {
			case 0:
				item1Hint.setIcon(null);
				break;
			case 1:
				item2Hint.setIcon(null);
				break;
			case 2:
				item3Hint.setIcon(null);
				break;
			case 3:
				item4Hint.setIcon(null);
				break;
			case 4:
				item5Hint.setIcon(null);
				break;
			case 5:
				item6Hint.setIcon(null);
				break;
			
			default:
				break;
			}
		}
	}
}
