package excalibur.game.presentation.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import excalibur.game.logic.SuperModuleTools;
import excalibur.game.logic.Tools;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.MyJButton;

public class ItemChoosePanel extends JPanel {
	ArrayList<MyJButton> itemList;

	public ItemChoosePanel() {
		itemList = new ArrayList<MyJButton>();
		setLayout(null);
		setBounds(0, 0, Constant.Config.WIDTH, Constant.Config.HEIGHT);
		setBackground(new Color(220, 245, 254));
		// 添加道具按钮
		String[] itemSrc = new String[] { Constant.PictureSrc.item1,
				Constant.PictureSrc.item2, Constant.PictureSrc.item3,
				Constant.PictureSrc.item4, Constant.PictureSrc.item5,
				Constant.PictureSrc.item6 };
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
		MyJButton startButton = new MyJButton(startIcon);
		startButton.setBounds(Constant.UILocation.itemStartX,
				Constant.UILocation.itemStartY, startIcon.getIconWidth(),
				startIcon.getIconHeight());
		add(startButton);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Tools> toolList = new ArrayList<>();
				toolList.add(new SuperModuleTools());
 				StageController.moveToStage(new SingleGameBoard(toolList),ItemChoosePanel.this);
			}
		});
		
		// 添加返回按钮
		ImageIcon returnIcon = new ImageIcon(Constant.PictureSrc.backBt);
		MyJButton returnButton = new MyJButton(returnIcon);
		returnButton.setBounds(Constant.UILocation.returnX,
				Constant.UILocation.returnY, returnIcon.getIconWidth(),
				returnIcon.getIconHeight());
		add(returnButton);
		returnButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StageController.returnToMain(ItemChoosePanel.this);
			}
			
		});
	}
}
