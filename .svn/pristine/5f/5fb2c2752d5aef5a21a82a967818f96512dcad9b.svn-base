package excalibur.game.presentation.myuicomponent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JLabel;

import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.tools.FontLoader;

public class PlayerPanel extends JPanel
{
	JLabel teamLabel;
	JLabel portraitLabel;
	JLabel headLabel;
	JLabel smallPortraitLabel;
	JLabel nickNameLabel;
	JLabel stateLabel;
	String[] portrates = Constant.PictureSrc.getBigScuplture();
	int location;
	
	/**
	 * Create the panel.
	 */
	public PlayerPanel()
	{
		setSize(196,246);
		setLayout(null);
		Font font=FontLoader.loadFont(Constant.FontSrc.ZHUNYUAN,20);
		
		teamLabel=new JLabel(new ImageIcon(Constant.PictureSrc.playerPanelbackground_teamA));
		teamLabel.setBounds(0,0,196,246);
		add(teamLabel,-1);
		
		portraitLabel=new JLabel();
		portraitLabel.setBounds(40,40,153,202);
		add(portraitLabel,0);
		
		headLabel=new JLabel("",JLabel.CENTER);
		headLabel.setBounds(3,77,40,135);
		headLabel.setFont(font);
		headLabel.setForeground(Color.WHITE);
		add(headLabel,0);
		
		smallPortraitLabel=new JLabel(new ImageIcon(Constant.PictureSrc.playerPanel_sculptureA));
		smallPortraitLabel.setBounds(0,4,73,73);
		add(smallPortraitLabel,0);
		
		nickNameLabel=new JLabel("",JLabel.CENTER);
		nickNameLabel.setBounds(62,0,98,40);
		nickNameLabel.setFont(font);
		nickNameLabel.setForeground(Color.WHITE);
		add(nickNameLabel,0);
		
		stateLabel=new JLabel();
		stateLabel.setBounds(72,163,124,83);
		stateLabel.setFont(font);
		stateLabel.setForeground(Color.WHITE);
		add(stateLabel,0);
		
	}
	
	public void setLocation(int location)
	{
		this.location=location;
	}
	
	public void setTeam(String teamId)
	{
		if(teamId.toUpperCase().equals("FALSE"))
		{
			teamLabel.setIcon(new ImageIcon(Constant.PictureSrc.playerPanelbackground_teamA));
			smallPortraitLabel.setIcon(new ImageIcon(Constant.PictureSrc.playerPanel_sculptureA));
		}
		else
		{
			teamLabel.setIcon(new ImageIcon(Constant.PictureSrc.playerPanelbackground_teamB));
			smallPortraitLabel.setIcon(new ImageIcon(Constant.PictureSrc.playerPanel_sculptureB));
		}
	}
	
	public void update(String playerName,int portrait,int state)
	{
		// 存在玩家
		if(playerName!=null)
		{
			switch(state)
			{
				case 0:
					stateLabel.setIcon(null);
					break;
				case 1:
					stateLabel.setIcon(new ImageIcon(Constant.PictureSrc.gameroom_prepared));
					break;
				case 2:
					headLabel.setIcon(new ImageIcon(Constant.PictureSrc.playerPanel_roomMaster));
					break;
				default:
					break;
			}
			nickNameLabel.setText(playerName);
			portraitLabel.setIcon(new ImageIcon(portrates[portrait]));
			// 头像
			// 头像在哪里？
		}
		else
		{
			nickNameLabel.setText("");
			stateLabel.setIcon(null);
		}
	}
}
