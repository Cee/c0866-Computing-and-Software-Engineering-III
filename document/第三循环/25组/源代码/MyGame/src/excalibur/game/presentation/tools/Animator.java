package excalibur.game.presentation.tools;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import excalibur.game.presentation.myuicomponent.MyJButton;

public class Animator {
	ArrayList<ArrayList<ImageIcon>> animations = new ArrayList<>();
	ArrayList<MyJButton> buttons = new ArrayList<>();

	public void setAnimation(String url, MyJButton myJButton) {
		ArrayList<ImageIcon> imageIcons = new ArrayList<>();
		for (int i = 0; i <= 20; i++) {
			ImageIcon icon;
			if (i < 10) {
				icon = new ImageIcon(url + "0" + i + "_0.png");
			} else {
				icon = new ImageIcon(url + i + "_0.png");
			}
			if (icon.getIconHeight() > 0)
				imageIcons.add(icon);
		}
		animations.add(imageIcons);
		buttons.add(myJButton);
	}

	class DrawThread implements Runnable {
		int speed;
		boolean isReverse;

		public DrawThread(int speed, boolean isReverse) {
			this.speed = speed;
			this.isReverse = isReverse;
		}

		@Override
		public void run() {
			if (!isReverse) {
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < buttons.size(); j++) {
						ImageIcon icon = animations.get(j).get(i);
						buttons.get(j).setIcon(icon);
					}
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				for (int i = 19; i >= 0; i--) {
					for (int j = 0; j < buttons.size(); j++) {
						ImageIcon icon = animations.get(j).get(i);
						buttons.get(j).setIcon(icon);
					}
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		}
	}

	public void startAnimation(int speed, boolean isReverse) {
		new Thread(new DrawThread(speed, isReverse)) {
		}.start();
	}

}
