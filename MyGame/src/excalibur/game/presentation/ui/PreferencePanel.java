package excalibur.game.presentation.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import excalibur.game.logic.syslogic.PrefferenceLogic;
import excalibur.game.logic.syslogic.setVO;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.DialogCreator;
import excalibur.game.presentation.myuicomponent.MyJButton;
import excalibur.game.presentation.tools.StageController;

public class PreferencePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3678807799854135686L;
	
	JTextField nameField;
	JLabel bm;
	ButtonGroup backMusicBg;
	JLabel se;
	ButtonGroup soundEffectBg;
	MyJButton ensure;
	MyJButton back;
	
	boolean isbmOpen;
	boolean isseOpen;
	boolean isvertical;
	String imagepath;
	ImageIcon chooseIcon;
	JLabel bgLabel;
	JLabel bgmChoose;
	JLabel seChoose;
	JLabel dropChoose;
	JLabel sculptureChoose;
	public int shift = Constant.Config.HEIGHT;
	String[] iconStrings = Constant.PictureSrc.getSculpture();
	public void init(){
		setVO sv=PrefferenceLogic.getSettings();
		nameField.setText(sv.getUsrName());
		if (!sv.getIsBackgroundMusicOpen()) {
			bgmChoose.setBounds(470,240,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
			isbmOpen=false;
			System.out.println("背景关");
		} else {
			isbmOpen=true;
		}
		if (!sv.getIsVertical()){
			dropChoose.setBounds(470,400,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
			isvertical=false;
		} else {
			isvertical=true;
		}
		if (!sv.getIsSoundEffectOpen()){
			seChoose.setBounds(470,320,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
			isseOpen=false;
			System.out.println("音效关");
		} else {
			isseOpen = true;
		}
		System.out.println("背景图："+sv.getPictureName());
		for (int i = 0; i < iconStrings.length; i++) {
			if ((iconStrings[i]).equals(sv.getPictureName())) {
				sculptureChoose.setBounds(310+60*i,150,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
				break;
			}
		}
		
						
		
	}
	public PreferencePanel(){
		setLayout(null);
		setBounds(0, 0, Constant.Config.WIDTH, Constant.Config.HEIGHT);
		nameField=new JTextField();
		
		
		
		
		chooseIcon = new ImageIcon(Constant.PictureSrc.chooseIcon);
		
		bm=new JLabel();
		ImageIcon bmIcon = new ImageIcon(Constant.PictureSrc.bmBt);
		bm.setIcon(bmIcon);
		bm.setBounds(215,210,bmIcon.getIconWidth(),bmIcon.getIconHeight());
		backMusicBg=new ButtonGroup();
		
		bgmChoose = new JLabel();
		bgmChoose.setIcon(chooseIcon);
		bgmChoose.setBounds(340,240,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
		add(bgmChoose);

		se=new JLabel();
		ImageIcon seIcon = new ImageIcon(Constant.PictureSrc.seIcon);
		se.setIcon(seIcon);
		se.setBounds(217,290,seIcon.getIconWidth(),seIcon.getIconHeight());
		soundEffectBg=new ButtonGroup();
		
		seChoose = new JLabel();
		seChoose.setIcon(chooseIcon);
		seChoose.setBounds(340,320,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
		add(seChoose);
//设置初始状
//		if(sv.getIsSoundEffectOpen()){
//			soundEffect.setSelected(true);
//		}else{
//			nosoundEffect.setSelected(true);
//		}
		
		ensure=new MyJButton();
		ImageIcon saveIcon = new ImageIcon(Constant.PictureSrc.saveIcon);
		ensure.setIcon(saveIcon);
		ensure.setBorder(null);
		ensure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("存的是"+imagepath);
				setVO sv=new setVO(imagepath,isbmOpen,isseOpen,isvertical,nameField.getText());
				
				
//				setVO sv=new setVO(imagepath.getText(),backMusic.isSelected(),soundEffect.isSelected(),nameField.getText());
				int t=PrefferenceLogic.setPreference(sv);
				if(t==1){
//					alert io error
//					...
//					JOptionPane.showMessageDialog(PreferencePanel.this, "fail to save the settings", "error", JOptionPane.ERROR_MESSAGE);
					DialogCreator.oneButtonDialog("错误013", "存取用户信息失败！");
				}else if(t==2){
					DialogCreator.oneButtonDialog("错误014", "无法找到图片！");
//					JOptionPane.showMessageDialog(PreferencePanel.this, "cannot find the image file", "error", JOptionPane.ERROR_MESSAGE);
				}else{
//					alert succeed
					if(sv.getIsBackgroundMusicOpen()){
						Media.playBGM(Media.MAIN);												
					}else{
						Media.stopBGM(Media.MAIN);						
					}
				
					DialogCreator.oneButtonDialog("", "保存成功！");
//					JOptionPane.showMessageDialog(PreferencePanel.this, "success", "success", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		back=new MyJButton();
		ImageIcon returnIcon = new ImageIcon(Constant.PictureSrc.returnIcon);
		back.setIcon(returnIcon);
		back.setBorder(null);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StageController.returnToMain(PreferencePanel.this);
			}		
		});
	
		nameField.setBounds(315, 66, 179, 30);
		ensure.setBounds(280,470,saveIcon.getIconWidth(),saveIcon.getIconHeight());
		back.setBounds(420,471,returnIcon.getIconWidth(),returnIcon.getIconHeight());
		
		ImageIcon bgIcon = new ImageIcon(Constant.PictureSrc.preferenceBg);
		JLabel bgLabel = new JLabel(bgIcon);
		bgLabel.setBounds(0,0,bgIcon.getIconWidth(),bgIcon.getIconHeight());
		
		add(nameField);
		add(bm);
		add(se);
		add(ensure);
		add(back);
		
		JLabel drop = new JLabel();
		ImageIcon dropIcon = new ImageIcon(Constant.PictureSrc.dropIcon);
		drop.setBounds(217, 370, dropIcon.getIconWidth(), dropIcon.getIconHeight());
		drop.setIcon(dropIcon);
		add(drop);
		
		dropChoose = new JLabel();
		dropChoose.setIcon(chooseIcon);
		dropChoose.setBounds(340,400,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
		add(dropChoose);
		
		JLabel userName = new JLabel();
		ImageIcon userNameIcon = new ImageIcon(Constant.PictureSrc.userNameIcon);
		userName.setIcon(userNameIcon);
		userName.setBounds(212, 40, userNameIcon.getIconWidth(), userNameIcon.getIconHeight());
		add(userName);
		
		JLabel userSculpture = new JLabel();
		ImageIcon userSculptureIcon = new ImageIcon(Constant.PictureSrc.userSculptureIcon);
		userSculpture.setBounds(212, 125, userSculptureIcon.getIconWidth(), userSculptureIcon.getIconHeight());
		userSculpture.setIcon(userSculptureIcon);
		add(userSculpture);
		
		sculptureChoose = new JLabel();
		sculptureChoose.setIcon(chooseIcon);
		sculptureChoose.setBounds(300,150,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
		add(sculptureChoose);
		
		
		for (int i = 0; i < iconStrings.length; i++) {
			final MyJButton sculpture = new MyJButton();
			ImageIcon icon = new ImageIcon(iconStrings[i]);
			sculpture.setIcon(icon);
			sculpture.setBounds(300+i*(icon.getIconWidth()+10), 130, icon.getIconWidth(), icon.getIconHeight());
			add(sculpture);
			final int index = i ; 
			sculpture.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					sculptureChoose.setBounds(sculpture.getX()+10,sculpture.getY()+20,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
					imagepath=iconStrings[index];					
				}
			});
		}
		
		
		MyJButton seOn = new MyJButton();
		ImageIcon onIcon = new ImageIcon(Constant.PictureSrc.onIcon);
		seOn.setIcon(onIcon);
		seOn.setBounds(315, 314, onIcon.getIconWidth(), onIcon.getIconHeight());
		seOn.setBorder(null);
		add(seOn);
		seOn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				seChoose.setBounds(340,320,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
				isseOpen=true;
			}
		});
		
		
		MyJButton seOff = new MyJButton();
		ImageIcon offIcon = new ImageIcon(Constant.PictureSrc.offIcon);
		seOff.setIcon(offIcon);
		seOff.setBorder(null);
		seOff.setBounds(444, 314, offIcon.getIconWidth(), offIcon.getIconHeight());
		add(seOff);
		seOff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				seChoose.setBounds(470,320,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
				isseOpen=false;
			}
		});
		
		
		MyJButton dropFromTop = new MyJButton();
		ImageIcon dropTop = new ImageIcon(Constant.PictureSrc.dropTopIcon);
		dropFromTop.setIcon(dropTop);
		dropFromTop.setBounds(340, 375, dropTop.getIconWidth(), dropTop.getIconHeight());
		dropFromTop.setBorder(null);
		add(dropFromTop);
		dropFromTop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dropChoose.setBounds(340,400,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
				isvertical=true;
			}
		});
		
		
		MyJButton dropFromSide = new MyJButton();
		ImageIcon dropSide = new ImageIcon(Constant.PictureSrc.dropSideIcon);
		dropFromSide.setIcon(dropSide);
		dropFromSide.setBounds(450, 395, dropSide.getIconWidth(), dropSide.getIconHeight());
		dropFromSide.setBorder(null);
		add(dropFromSide);
		dropFromSide.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dropChoose.setBounds(470,400,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
				isvertical=false;
			}
		});		
		
		MyJButton bgmOn = new MyJButton();
		bgmOn.setIcon(onIcon);
		bgmOn.setBounds(315, 230, onIcon.getIconWidth(), onIcon.getIconHeight());
		bgmOn.setBorder(null);
		add(bgmOn);
		bgmOn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bgmChoose.setBounds(340,240,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
				isbmOpen=true;
			}
		});
		
		
		MyJButton bgmOff = new MyJButton();
		bgmOff.setIcon(offIcon);
		bgmOff.setBounds(444, 230, offIcon.getIconWidth(), offIcon.getIconHeight());
		bgmOff.setBorder(null);
		add(bgmOff);
		bgmOff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bgmChoose.setBounds(470,240,chooseIcon.getIconWidth(),chooseIcon.getIconHeight());
				isbmOpen=false;
			}
		});
		add(bgLabel);
		init();
	}
}
