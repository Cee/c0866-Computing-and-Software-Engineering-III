package excalibur.game.logic;

import excalibur.game.presentation.ui.PlayBoard;


public abstract class Tools {
	PlayBoard playBoard;
	String imageName;
	public Tools(PlayBoard playBoard){
		this.playBoard = playBoard;
	}
	public Tools(){
		
	}
	public void setPlayBoard(PlayBoard playBoard) {
		this.playBoard = playBoard;
	}
	public abstract int getGold();
	public abstract String getName();
	public abstract void affect();
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageName() {
		return imageName;
	}
}
