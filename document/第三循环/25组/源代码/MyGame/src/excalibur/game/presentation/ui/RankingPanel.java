package excalibur.game.presentation.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.MyJButton;

public class RankingPanel extends JPanel {
	public RankingPanel() {
		setLayout(null);
		setBounds(0, 0, Constant.Config.WIDTH, Constant.Config.HEIGHT);

		ImageIcon bgIcon = new ImageIcon(Constant.PictureSrc.rankBg);
		JLabel bgLabel = new JLabel(bgIcon);
		bgLabel.setBounds(0, 0, bgIcon.getIconWidth(), bgIcon.getIconHeight());
		add(bgLabel);

		// 添加返回按钮
		ImageIcon returnIcon = new ImageIcon(Constant.PictureSrc.backBt);
		MyJButton returnButton = new MyJButton(returnIcon);
		returnButton.setBounds(Constant.UILocation.returnX,
				Constant.UILocation.returnY, returnIcon.getIconWidth(),
				returnIcon.getIconHeight());
		add(returnButton, 0);
		returnButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				StageController.returnToMain(RankingPanel.this);
			}

		});
	}

}
