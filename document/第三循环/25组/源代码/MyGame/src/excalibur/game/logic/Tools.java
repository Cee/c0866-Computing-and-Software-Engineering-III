package excalibur.game.logic;

import excalibur.game.presentation.ui.PlayBoard;


public abstract class Tools {
	PlayBoard playBoard;
	public void setPlayBoard(PlayBoard playBoard) {
		this.playBoard = playBoard;
	}
	public abstract void affect();
}
