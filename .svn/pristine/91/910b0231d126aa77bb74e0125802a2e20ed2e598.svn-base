package excalibur.game.presentation.myuicomponent;

import javax.swing.*;

/**
 * @author luck
 *         MyJButton 去除填充和边框
 */
public class MyJButton extends JButton {
	private boolean isSure;
	public boolean isSure() {
		return isSure;
	}
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MyJButton(){
    	isSure = false;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }
    public MyJButton(ImageIcon icon) {

        super(icon);
        isSure = false;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }public MyJButton(Icon icon) {

        super(icon);
        isSure = false;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    public MyJButton(String iconLocation,int i) {
        this(new ImageIcon(iconLocation));
    }
	public MyJButton(String label){
//		setContentAreaFilled(false);
//		setBorderPainted(false);
//		setFocusPainted(false);
		super(label);
	}
	public void setSure(boolean isSure) {
		this.isSure = isSure;
		if (isSure)
			setBorder(BorderFactory.createLoweredBevelBorder());
		else 
			setBorder(BorderFactory.createRaisedBevelBorder());
	}
	
	
	
	public void setSelectedByMouse(boolean isSelected){
		setBorderPainted(isSelected);
		
	}
	
	public void setSelectedByKeyBoard(boolean isSelected){
		if (isSelected){
			setBorderPainted(true);
			setBorder(BorderFactory.createRaisedBevelBorder());
		} else {
			isSure=false;
			setBorderPainted(false);
			setBorder(null);
		}
	}
}
