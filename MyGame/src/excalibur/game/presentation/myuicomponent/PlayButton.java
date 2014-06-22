package excalibur.game.presentation.myuicomponent;


import javax.swing.ImageIcon;

import excalibur.game.logic.gamelogic.Map.ACTION;
import excalibur.game.presentation.constant.Constant;

/**
 * PlayButton
 * 
 * @author luck
 * 
 */
public class PlayButton extends MyJButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int type;
	
	public PlayButton(ImageIcon icon) {
		super(icon);
	}
	public ACTION isNear(PlayButton playButton){
		int x = (getX()-playButton.getX())/Constant.Config.BT_WIDTH;
		int y = (getY()-playButton.getY())/Constant.Config.BT_HEIGHT;
		if (x==1&&y==0){
			return ACTION.LEFT;
		}
		if (x==-1&&y==0){
			return ACTION.RIGHT;
		}
		if (x==0&&y==1){
			return ACTION.UP;
		}
		if(x==0&&y==-1){
			return ACTION.DOWN;
		}
		return null;
	}
}
