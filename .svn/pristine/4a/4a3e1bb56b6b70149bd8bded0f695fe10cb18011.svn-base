package excalibur.game.presentation.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import excalibur.game.logic.networkadapter.GameRoom;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.MyJButton;
import excalibur.game.presentation.tools.StageController;

public class ItemChoosePanelForNet extends ItemChoosePanel{
	GameRoom gameRoom;
	boolean isClick;
	public ItemChoosePanelForNet(GameRoom gameRoom){
		super();
		this.gameRoom = gameRoom;
		isClick = false;
	}
	protected void addListener(){
		returnButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Media.playSound(Media.RETURN);
				StageController.returnToMain(ItemChoosePanelForNet.this);
			}
		});
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Media.playSound(Media.ENTER);
				Media.playBGM(Media.GAME);
				if (!isClick) {
					gameRoom.start(ItemChoosePanelForNet.this,toolList);
					isClick=true;
				}
//				SingleGameBoard singleGameBoard = new SingleGameBoard(toolList);
//				singleGameBoard.initPlayBoard();
// 				StageController.moveToStage(singleGameBoard,ItemChoosePanelForNet.this);
			}
		});
		ItemChooseListener listener = new ItemChooseListener();
		for (int i = 0; i < itemList.size(); i++) {
			itemList.get(i).addMouseListener(listener);
		}
	

	}
}
