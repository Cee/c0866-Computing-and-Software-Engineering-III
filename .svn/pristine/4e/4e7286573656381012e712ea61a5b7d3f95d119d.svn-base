package excalibur.game.logic;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.ui.PlayBoard;

public class TimeTool extends Tools{

	public TimeTool(PlayBoard playBoard) {
		super(playBoard);
	}
	public TimeTool(){
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "time";
	}
	@Override
	public int getGold() {
		// TODO Auto-generated method stub
		return 500;
	}
	@Override
	public void affect() {
		System.out.println("Tool Time");
		if (!DataUtils.getInstance().consumeGold(getGold())) {
			return;
		}
		playBoard.setBonus("time", playBoard.getBonus("time")+3.0);

	}

}
