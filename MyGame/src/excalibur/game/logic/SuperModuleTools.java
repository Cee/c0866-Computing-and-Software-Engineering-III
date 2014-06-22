package excalibur.game.logic;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.ui.PlayBoard;

public class SuperModuleTools extends Tools{

	public SuperModuleTools(PlayBoard playBoard) {
		super(playBoard);
	}
	public SuperModuleTools(){
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "super";
	}
	@Override
	public int getGold() {
		// TODO Auto-generated method stub
		return 400;
	}
	@Override
	public void affect() {
		System.out.println("Tool SuperModel");
		if (!DataUtils.getInstance().consumeGold(getGold())) {
			return;
		}
		playBoard.setBonus("super", playBoard.getBonus("super")+2.0);

	}

}
