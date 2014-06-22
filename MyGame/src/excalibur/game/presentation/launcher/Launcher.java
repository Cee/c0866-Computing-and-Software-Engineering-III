package excalibur.game.presentation.launcher;

import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.ui.StartUI;

public class Launcher {
    public static void main(String[] args) {
    	new StartUI();
    	Media.playBGM(Media.MAIN);
    }

}
