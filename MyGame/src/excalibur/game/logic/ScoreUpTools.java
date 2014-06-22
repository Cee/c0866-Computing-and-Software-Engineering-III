package excalibur.game.logic;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.ui.PlayBoard;

public class ScoreUpTools extends Tools{

	public ScoreUpTools(PlayBoard playBoard) {
		super(playBoard);
	}
	public ScoreUpTools(){
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "score";
	}
	@Override
	public int getGold() {
		// TODO Auto-generated method stub
		return 600;
	}
	@Override
	public void affect() {
		System.out.println("Tool ScoreUp");
		if (!DataUtils.getInstance().consumeGold(getGold())) {
			return;
		}
		playBoard.setBonus("score", playBoard.getBonus("score")+0.1);

	}

}
