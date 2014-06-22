package excalibur.game.presentation.myuicomponent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 
 * @author luck
 * MyJButton 去除填充和边框
 */
public class MyJButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyJButton(ImageIcon icon){
		super(icon);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
	}
}
