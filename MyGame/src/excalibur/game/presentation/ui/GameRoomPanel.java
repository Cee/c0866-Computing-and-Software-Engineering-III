package excalibur.game.presentation.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.logic.syslogic.PrefferenceLogic;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.MyJButton;
import excalibur.game.presentation.myuicomponent.PlayerPanel;
import excalibur.game.presentation.tools.FontLoader;

public class GameRoomPanel extends JPanel
{
	public PlayerPanel players[];
	public MyJButton prepareButton;
	public MyJButton exitRoomButton;
	public MyJButton selectAButton;
	public MyJButton selectBButton;
	public JLabel teamASelected;
	public JLabel teamBSelected;
	public JLabel headLable;
	
	/**
	 * Create the panel.
	 */
	public GameRoomPanel()
	{
		players=new PlayerPanel[4];
		setBounds(new Rectangle(0,0,800,600));
		setLayout(null);
		Font font=FontLoader.loadFont(Constant.FontSrc.ZHUNYUAN,20);
		
		setBackground(new Color(228,248,255));
		
		// 右侧的玩家信息和队伍选择、开始游戏等按钮
		JPanel panel=new JPanel();
		panel.setBounds(560,0,240,600);
		panel.setOpaque(false);
		panel.setLayout(null);
		add(panel,0);
		
		// 个人信息标签
		headLable=new JLabel(new ImageIcon(Constant.PictureSrc.gameroom_personalinfo_A));
		headLable.setBounds(18,75,162,64);
		headLable.setFont(font);
		headLable.setForeground(Color.WHITE);
		headLable.setOpaque(false);
		panel.add(headLable,0);
		
		JLabel nickNameLabel=new JLabel(PrefferenceLogic.getUserName(),JLabel.CENTER);
		nickNameLabel.setBounds(80,75,83,29);
		nickNameLabel.setFont(font);
		nickNameLabel.setForeground(Color.WHITE);
		nickNameLabel.setOpaque(false);
		panel.add(nickNameLabel,0);
		
		JLabel levelLabel=new JLabel("Lv: "+DataUtils.getInstance().getLevel(),JLabel.CENTER);
		levelLabel.setBounds(80,112,83,29);
		levelLabel.setFont(font);
		levelLabel.setForeground(Color.WHITE);
		levelLabel.setOpaque(false);
		panel.add(levelLabel,0);
		
		// 开始按钮
		prepareButton=new MyJButton(new ImageIcon(Constant.PictureSrc.gameroom_prepare));
		prepareButton.setBounds(57,366,106,106);
		panel.add(prepareButton,0);
		
		// 返回按钮
		exitRoomButton=new MyJButton(new ImageIcon(Constant.PictureSrc.gameroom_back));
		exitRoomButton.setBounds(146,492,75,75);
		panel.add(exitRoomButton,0);
		
		// 队伍选择模块
		JLabel teamLabel=new JLabel(new ImageIcon(Constant.PictureSrc.gameroom_chooseteam));
		teamLabel.setBounds(0,227,212,42);
		panel.add(teamLabel,0);
		
		selectAButton=new MyJButton(new ImageIcon(Constant.PictureSrc.gameroom_teamA));
		selectAButton.setBounds(33,278,62,62);
		panel.add(selectAButton,0);
		teamASelected=new JLabel(new ImageIcon(Constant.PictureSrc.gameroom_selected));
		teamASelected.setBounds(51,298,44,42);
		panel.add(teamASelected,0);
		
		selectBButton=new MyJButton(new ImageIcon(Constant.PictureSrc.gameroom_teamB));
		selectBButton.setBounds(120,278,62,62);
		panel.add(selectBButton,0);
		teamBSelected=new JLabel();
		teamBSelected.setBounds(138,298,44,42);
		panel.add(teamBSelected,0);
		
		// 四个玩家位置
		for(int i=0;i<2;i++)
		{
			for(int j=0;j<2;j++)
			{
				PlayerPanel playerPanel=new PlayerPanel();
				players[i*2+j]=playerPanel;
				players[i*2+j].setLocation(i*2+j);
				playerPanel.setLocation(57+i*(196+70),30+j*(246+40));
				playerPanel.setOpaque(false);
				add(playerPanel,0);
			}
		}
	}
	
	public void updatePlayer(int playerId,String playerName,int portrait,int state,String team)
	{
		players[playerId].update(playerName,portrait,state);
		players[playerId].setTeam(team);
	}
	
	public void setPrepare(boolean isPrepared)
	{
		prepareButton.setIcon(new ImageIcon(isPrepared?Constant.PictureSrc.gameroom_cancelprepare:Constant.PictureSrc.gameroom_prepare));
	}
	
	public void changeTeam(int i,String teamId)
	{
		players[i].setTeam(teamId);
	}
	
}
