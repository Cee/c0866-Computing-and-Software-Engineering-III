package excalibur.game.presentation.ui;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import excalibur.game.logic.Tools;
import excalibur.game.logic.networkadapter.GameRoom;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.tools.StageController;
import excalibur.game.presentation.ui.SingleGameBoard.TimeThread;

public class NetGameBoard extends SingleGameBoard{
	GameRoom gameRoom;
	JLabel teamAScoreLabel;
	JLabel teamBScoreLabel;

	public NetGameBoard(ArrayList<Tools> toolList,GameRoom gameRoom){
		super(toolList);
		this.gameRoom = gameRoom;
		ImageIcon teamAIcon = new ImageIcon(Constant.PictureSrc.teamAFlag);
		ImageIcon teamBIcon = new ImageIcon(Constant.PictureSrc.teamBFlag);
		JLabel teamAFlag = new JLabel(teamAIcon);
		teamAFlag.setBounds(160,500 , teamAIcon.getIconWidth(), teamAIcon.getIconHeight());
		add(teamAFlag,0);
		JLabel teamBFlag = new JLabel(teamBIcon);
		teamBFlag.setBounds(160	,540 , teamBIcon.getIconWidth(), teamBIcon.getIconHeight());
		add(teamBFlag,0);
		
		
		teamAScoreLabel = new JLabel();
		teamAScoreLabel.setBounds(200,510, 100, 30);
//		teamAScoreLabel.setText("123456");
		add(teamAScoreLabel,0);
		
		teamBScoreLabel = new JLabel();
		teamBScoreLabel.setBounds(200,550, 100, 30);
//		teamBScoreLabel.setText("123456");
		add(teamBScoreLabel,0);
		
		
		
	}
			
	
	public int[][] getMap(){
		return playBoard.getMap();
	}
	public void setMap(int[][] map) {
		((PlayBoardForNet)playBoard).setMap(map);
	}
	public PlayBoardForNet getPlayBoard() {
		return (PlayBoardForNet)playBoard;
	}
	
	
	
	@Override
	public void initPlayBoard(){
		playBoard =  new PlayBoardForNet(scoreLabel,toolList,gameRoom);
		playBoard.setLocation(150,75);
		playBoard.setDelegate(this);
		startTiming();
		add(playBoard,0);
		playBoard.setTimer(timer);

		((PlayBoardForNet)playBoard).setTeamAScoreLabel(teamAScoreLabel);
		((PlayBoardForNet)playBoard).setTeamBScoreLabel(teamBScoreLabel);

	}
	
	protected void startTiming(){
		timer = new TimeThread();
		new Thread(timer).start();

	}
	
	@Override
	protected void backtomainMethod(){
		super.backtomainMethod();
		Media.playBGM(Media.WAITING);
	}
	
	@Override
	public void backtoMain() {
		// TODO Auto-generated method stub
		super.backtoMain();
		Media.playBGM(Media.WAITING);
	}

}

