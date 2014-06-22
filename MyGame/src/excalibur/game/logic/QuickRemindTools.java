package excalibur.game.logic;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.ui.PlayBoard;

public class QuickRemindTools extends Tools{

	public QuickRemindTools(PlayBoard playBoard) {
		super(playBoard);
	}

	public QuickRemindTools(){
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "remind";
	}
	@Override
	public int getGold() {
		// TODO Auto-generated method stub
		return 200;
	}
	@Override
	public void affect() {
		System.out.println("Tool QuickRemind");
		if (!DataUtils.getInstance().consumeGold(getGold())) {
			return;
		}
		playBoard.setBonus("remind", playBoard.getBonus("remind")+2.0);

	}

	
}
