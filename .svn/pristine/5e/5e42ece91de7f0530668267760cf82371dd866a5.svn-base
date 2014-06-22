package excalibur.game.logic;

public class ToolFactory {
	public static Tools getTool(String name){
		switch (name) {
		case "BOMB":
			return new BombTools();
		case "REMIND":
			return new QuickRemindTools();
		case "SUPER":
			return new SuperModuleTools();
		case "TIME":
			return new TimeTool();
		case "SCORE":
			return new ScoreUpTools();
		default:
			return null;
		}
	}
}
