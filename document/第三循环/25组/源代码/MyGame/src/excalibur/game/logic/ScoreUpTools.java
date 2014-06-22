package excalibur.game.logic;

import excalibur.game.presentation.ui.PlayBoard;

public class ScoreUpTools extends Tools{


	@Override
	public void affect() {
		playBoard.setBonus("score", 1.1);
	}

}
