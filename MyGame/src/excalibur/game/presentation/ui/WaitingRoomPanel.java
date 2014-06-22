package excalibur.game.presentation.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.logic.syslogic.PrefferenceLogic;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.MyJButton;
import excalibur.game.presentation.myuicomponent.RoomButton;
import excalibur.game.presentation.tools.FontLoader;

import javax.swing.JList;
/**
 * 
 * @author luck-mac
 *
 */
public class WaitingRoomPanel extends JPanel {
	private JScrollPane scrollPane;
	private RoomButton[] rooms;
	public MyJButton createRoomBt;
	public MyJButton quitWaitingRoomBt;
	public JList list;
	/**
	 * Create the panel.
	 */
	public WaitingRoomPanel() {
		Media.playBGM(Media.LOBBY);
		setBounds(new Rectangle(0, 0, 800, 600));
		setLayout(null);
		setBackground(new Color(228,248,255));
		setOpaque(false);
		RoomsPanel roomsPanel = new RoomsPanel();
        roomsPanel.setPreferredSize(new Dimension(600, 1200));
        rooms = roomsPanel.getRooms();
        
        
        
		scrollPane = new JScrollPane(roomsPanel);
		scrollPane.setOpaque(false);
		scrollPane.setBackground(new Color(228,248,255));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 620, 600);
		add(scrollPane);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 200, 600);
		add(panel);
		
		quitWaitingRoomBt = new MyJButton();
		quitWaitingRoomBt.setBounds(660, 470, 106, 106);
		quitWaitingRoomBt.setIcon(new ImageIcon(Constant.PictureSrc.quitRoom));
		add(quitWaitingRoomBt);
		
		Font font=FontLoader.loadFont(Constant.FontSrc.ZHUNYUAN,20);

		// 个人信息标签
				JLabel headLable=new JLabel(new ImageIcon(Constant.PictureSrc.gameroom_personalinfo_A));
				headLable.setBounds(630,60,162,64);
				headLable.setFont(font);
				headLable.setForeground(Color.WHITE);
				headLable.setOpaque(false);
				add(headLable,0);
				
				JLabel nickNameLabel=new JLabel(PrefferenceLogic.getUserName(),JLabel.CENTER);
				nickNameLabel.setBounds(690,60,83,29);
				nickNameLabel.setFont(font);
				nickNameLabel.setForeground(Color.WHITE);
				nickNameLabel.setOpaque(false);
				add(nickNameLabel,0);
				
				JLabel levelLabel=new JLabel("Lv: "+DataUtils.getInstance().getLevel(),JLabel.CENTER);
				levelLabel.setBounds(690,97,83,29);
				levelLabel.setFont(font);
				levelLabel.setForeground(Color.WHITE);
				levelLabel.setOpaque(false);
				add(levelLabel,0);

		
		createRoomBt = new MyJButton();
		createRoomBt.setBounds(660, 350, 106, 106);
		createRoomBt.setIcon(new ImageIcon(Constant.PictureSrc.createRoom));
		add(createRoomBt);
		
		JLabel listBgLabel = new JLabel();
		listBgLabel.setBounds(632,140,160,190);
		listBgLabel.setIcon(new ImageIcon(Constant.PictureSrc.listBackground));
	    list = new JList();
		list.setBounds(632, 150, 160, 190);
		list.setOpaque(false);
//		list.setEnabled(false);
		list.setBackground(new Color(0,0,0,0));
		DefaultListModel<String> model = new DefaultListModel<>();
		model.addElement("luck");
		list.setModel(model);
		add(list);
		add(listBgLabel);

	}
	
	public RoomButton[] getRooms() {
		return rooms;
	}
}
