package excalibur.game.logic;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.ui.PlayBoard;

public class BombTools extends Tools{

	public BombTools(PlayBoard playBoard) {
		super(playBoard);
	}
	public BombTools()
	{
		
	}
	@Override
	public String getName() {
		return "bomb";
	}
	@Override
	public int getGold() {
		// TODO Auto-generated method stub
		return 800;
	}
	@Override
	public void affect() {
		System.out.println("Tool Bombo");
		if (!DataUtils.getInstance().consumeGold(getGold())) {
			return;
		}
		playBoard.setBonus("bomb", playBoard.getBonus("bomb")+1.0);
	}

	
}
