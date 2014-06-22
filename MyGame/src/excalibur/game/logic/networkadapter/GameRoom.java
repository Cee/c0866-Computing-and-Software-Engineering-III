package excalibur.game.logic.networkadapter;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import excalibur.game.logic.ToolFactory;
import excalibur.game.logic.Tools;
import excalibur.game.logic.gamelogic.Map.ACTION;
import excalibur.game.logic.syslogic.PrefferenceLogic;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.DialogCreator;
import excalibur.game.presentation.tools.StageController;
import excalibur.game.presentation.ui.GameRoomPanel;
import excalibur.game.presentation.ui.ItemChoosePanelForNet;
import excalibur.game.presentation.ui.NetGameBoard;
import excalibur.game.presentation.ui.PlayBoardForNet;

/**
 * A data class to present the command. Since it has some tool-like functions,
 * all of its methods are public.
 * 
 * It is recommended that in C/S transferring, we use String; when processing
 * commands locally, we use this class.
 * 
 * 提示：此文件勿任意Format，按下Ctrl+Shift+F下面格式求全乱了T_T
 * 
 * In string, command format are as follows:
 * 		ENTER 0 room_id	username	<---This for request to server
 * 		ENTER 1 username			<---This for broadcasting to other users in the room
 * 		友情提醒：从一个游戏房间直接进入另一个游戏房间是不允许的~
 * 		EXIT 0 username				<---This for request to server
 * 		EXIT 1 username				<---This for broadcasting to other users in the room
 * 		PREPARE 0 username			<---This for request to server
 * 		PREPARE 1 username			<---This for broadcasting to other users in the room
 * 		TEAM 0 username side		<---This for request to server
 * 		TEAM 1 username side		<---This for broadcasting to other users in the room
 * 		CANCEL_PREPARE 0 username	<---This for request to server
 * 		CANCEL_PREPARE 1 username	<---This for broadcasting to other users in the room
 * 		START
 * 		MOVE 0 pointX pointY directionHorizonal directionVertical
 * 		MOVE 1 pointX pointY directionHorizonal directionVertical	<---This for replying to all clients
 * 		JUDGE 0 pointX pointY directionHorizonal directionVertical
 * 		JUDGE 1 True/False			<---This for replying to client
 * 		SYNC 0						<---This for request
 * 		SYNC 1 detailed_information	<---This for broadcasting to other users in the room
 * 		SCORE 0						<---This for getting all users high score
 * 		SCORE 1 username			<---This for getting the specified user's high score
 * 		SCORE 2 username score		<---This for uploading a new score(net necessarily the high score)
 * 		SCORE 3 information			<---This for replying to other SCORE commands
 * 		REPLY information			<---Other circumstances of server's reply
 * 
 * @author 喵叔
 * 
 */
public class GameRoom implements Room
{
	int roomId;
	boolean isPrepare;
	Listener listener;
	String username;
	int portrait;
	public String myTeam;
	GameRoomPanel myPanel;
	String[] players;
	int[] portrates;
	String[] teamId;
	int[] states;
	
	int playerNum;
	boolean isRoomMaster;
	boolean isTeamMaster;
	public boolean isGame;
	NetGameBoard netGameBoard;
	PlayBoardForNet playBoardForNet;
	boolean isWaitingResult;
	
	public boolean isRoomMaster()
	{
		return isRoomMaster;
	}
	
	public boolean isTeamMaster()
	{
		return isTeamMaster;
	}
	
	public void addListener()
	{
		myPanel.prepareButton.addActionListener(new PrepareListener());
		;
		myPanel.exitRoomButton.addActionListener(new ExitListener());
		ChooseTeamListener chooseTeamListener=new ChooseTeamListener();
		myPanel.selectAButton.addActionListener(chooseTeamListener);
		myPanel.selectBButton.addActionListener(chooseTeamListener);
	}
	
	public void startGame(int[][] map,ArrayList<Tools> toolList)
	{
		isGame=true;
		netGameBoard=new NetGameBoard(toolList,this);
		netGameBoard.initPlayBoard();
		netGameBoard.setMap(map);
		playBoardForNet=netGameBoard.getPlayBoard();
		StageController.moveToStage(netGameBoard);
	}
	private void startChooseItem() {
		for(int i=1;i<playerNum;i++)
		{
			if(states[i]==0)
			{
				DialogCreator.oneButtonDialog("抱歉", "玩家"+(i+1)+"未准备");
				System.out.println("玩家"+i+1+"为准备");
				return;
			}
		}
		listener.write("ITEM 1");
		ItemChoosePanelForNet itemChoosePanelForNet = new ItemChoosePanelForNet(this);
		StageController.moveToStage(itemChoosePanelForNet);
	}
	private void waitingChooseItem(){
		System.out.println("正在选择道具");
	}
	public void start(ItemChoosePanelForNet itemChoosePanelForNet, ArrayList<Tools> toolList)
	{
		isGame=true;
		netGameBoard=new NetGameBoard(toolList,this);
		netGameBoard.initPlayBoard();
		String command="START ";
		int[][] map=netGameBoard.getMap();
		for(int i=0;i<Constant.Config.MAP_SIZE;i++)
		{
			for(int j=0;j<Constant.Config.MAP_SIZE;j++)
			{
				command+=map[i][j]+" ";
			}
		}
		for (Tools tool:toolList){
			command+=tool.getName()+" ";
		}
		listener.write(command);
		StageController.moveToStage(netGameBoard,itemChoosePanelForNet);
		playBoardForNet=netGameBoard.getPlayBoard();
	}
	
	class ExitListener implements ActionListener
	{
		
		@Override public void actionPerformed(ActionEvent e)
		{
			Media.playSound(Media.ENTER);
			listener.write("EXIT 0 "+username);
			Media.playBGM(Media.LOBBY);
		}
		
	}
	
	class ChooseTeamListener implements ActionListener
	{
		@Override public void actionPerformed(ActionEvent e)
		{
			Media.playSound(Media.OPTION);
			if(e.getSource()==myPanel.selectAButton)
			{
				listener.write("TEAM 0 "+username+" FALSE");
				myTeam="False";
				myPanel.teamASelected.setIcon(new ImageIcon(Constant.PictureSrc.gameroom_selected));
				myPanel.teamBSelected.setIcon(null);
				myPanel.headLable.setIcon(new ImageIcon(Constant.PictureSrc.gameroom_personalinfo_A));
			}
			else
			{
				listener.write("TEAM 0 "+username+" TRUE");
				myTeam="True";
				myPanel.teamASelected.setIcon(null);
				myPanel.teamBSelected.setIcon(new ImageIcon(Constant.PictureSrc.gameroom_selected));
				myPanel.headLable.setIcon(new ImageIcon(Constant.PictureSrc.gameroom_personalinfo_B));
			}
		}
	}
	
	class PrepareListener implements ActionListener
	{
		
		@Override public void actionPerformed(ActionEvent e)
		{
			Media.playSound(Media.SAVE);
			if(isRoomMaster)
			{
				startChooseItem();
			}
			else
				if(!isPrepare)
				{
					prepare();
				}
				else
				{
					cancelPrepare();
				}
		}

		
		
	}
	
	public void setMyPanel(GameRoomPanel myPanel)
	{
		this.myPanel=myPanel;
		addListener();
	}
	
	public GameRoom(int roomId,Listener listener)
	{
		this.roomId=roomId;
		this.listener=listener;
		isPrepare=false;
		username=PrefferenceLogic.getUserName().toUpperCase();
		myTeam="False";
		players=new String[4];
		portrates=new int[4];
		teamId=new String[4];
		states=new int[4];
		
	}
	
	public int getRoomId()
	{
		return roomId;
	}
	
	public void prepare()
	{
		if(!isPrepare)
		{
			listener.write("PREPARE 0 "+username);
		}
	}
	
	public void cancelPrepare()
	{
		if(isPrepare)
		{
			listener.write("CANCEL_PREPARE 0 "+username);
		}
	}
	
	public void quitRoom()
	{
		
	}
	
	@Override public void execute(Command command)
	{
		if (!command.toString().startsWith("SYNC")) {
			System.out.println("Execute:inGameRoom"+roomId+command.toString());
		}
		switch(command.type)
		{
			case DROPDOWN:
				int[][] fillMap=new int[Constant.Config.MAP_SIZE][Constant.Config.MAP_SIZE];
				String[] mapInfo=command.args;
				for(int i=0;i<Constant.Config.MAP_SIZE;i++)
				{
					for(int j=0;j<Constant.Config.MAP_SIZE;j++)
					{
						fillMap[i][j]=Integer.parseInt(mapInfo[i*Constant.Config.MAP_SIZE+j]);
					}
				}
				final int[][] myMap=fillMap;
				final ArrayList<Point> clearListForThread=new ArrayList<>();
				for (int i = 81; i < mapInfo.length; i++) {
					String[] pointInfo = mapInfo[i].split(";");
					Point point = new Point(Integer.parseInt(pointInfo[0]), Integer.parseInt(pointInfo[1]));
					clearListForThread.add(point);
				}
				ThreadController.addThread(new Thread()
				{
					public void run()
					{
						playBoardForNet.dropDown(clearListForThread,myMap);
						if(!ThreadController.nextThread())
						{
							playBoardForNet.unLock();
						}
						;
					}
				});
				break;
			case ENTER:
				if(command.args[0].equals("1"))
				{
					enter(command.args[1],Integer.parseInt(command.args[2]),"False");
				}
				break;
			case SCORE:
			case JUDGE:
				
				String[] move=command.args;
				if(move[1].toUpperCase().equals("FALSE"))
				{
					if (isWaitingResult) {
						isWaitingResult=false;
						playBoardForNet.unLock();
						playBoardForNet.waiting=false;
					}
					break;
				}
				else
				{
					int actionId=Integer.parseInt(move[3]);
					ACTION action=ACTION.NOACTION;
					switch(actionId)
					{
						case -1:
							action=ACTION.LEFT;
							break;
						case 1:
							action=ACTION.RIGHT;
							
							break;
						case 2:
							action=ACTION.UP;
							break;
						case -2:
							action=ACTION.DOWN;
							break;
						
						case 3:
							action=ACTION.TRIGGER_A;
							
							break;
						case 4:
							action=ACTION.TRIGGER_B;
							
							break;
						case 0:
							action=action.NOACTION;
							break;
						default:
							break;
					}
					Point selectedPoint=new Point(Integer.parseInt(move[1]),Integer.parseInt(move[2]));
					ArrayList<Point> clearList=new ArrayList<>();
					if(move[0].equals("0"))
					{
						
						clearList=playBoardForNet.trySwap(action,selectedPoint);
						if(clearList.isEmpty())
						{
							listener.write("JUDGE 1 False");
						}
						else
						{
							swapComment=(int)selectedPoint.getX()+" "+(int)selectedPoint.getY()+" "+action.getTag();
							String newCommand="JUDGE 1 True "+swapComment+" ";
							for(Point point:clearList)
							{
								newCommand=newCommand+point.x+","+point.y+" ";
							}
							listener.write(newCommand);
						}
					}
					else
					{
						for(int i=4;i<move.length;i++)
						{
							String[] clearPoint=move[i].split(",");
							Point point=new Point(Integer.parseInt(clearPoint[0]),Integer.parseInt(clearPoint[1]));
							clearList.add(point);
						}
//						playBoardForNet.setClearList(clearList);
						playBoardForNet.swap(clearList,action,selectedPoint);
					}
				}
				break;
			case EXIT:
				if(command.args[0].equals("1"))
				{
					quit(command.args[1]);
					if(!isGame)
					{
						listener.write("SYNC 0");
					}
				}
				break;
			case PREPARE:
				if(command.args[1].equals(username.toUpperCase()))
				{
					isPrepare=true;
					myPanel.setPrepare(true);
				}
				prepare(command.args[1].toUpperCase());
				break;
			case TEAM:
				if(command.args[0].equals("1"))
				{
					changeTeam(command.args[1],command.args[2]);
				}
				listener.write("SYNC 0");
				break;
			case CANCEL_PREPARE:
				if(command.args[1].equals(username.toUpperCase()))
				{
					isPrepare=false;
					myPanel.setPrepare(false);
				}
				cancelPrepare(command.args[1]);
				break;
			case HINT:
				playBoardForNet.hint(command.args);
				break;
			case START:
				if(!isRoomMaster)
				{
					String[] initMap=command.args;
					int[][] map=new int[Constant.Config.MAP_SIZE][Constant.Config.MAP_SIZE];
					for(int i=0;i<Constant.Config.MAP_SIZE;i++)
					{
						for(int j=0;j<Constant.Config.MAP_SIZE;j++)
						{
							map[i][j]=Integer.parseInt(initMap[i*Constant.Config.MAP_SIZE+j]);
						}
					}
					ArrayList<Tools> toolList = new ArrayList<>();
					for (int i = 81; i < initMap.length;i++){
						toolList.add(ToolFactory.getTool(initMap[i]));
					}
					startGame(map,toolList);
				}
				break;
			case ITEM:
				if (command.args[0].equals("1")) {
					waitingChooseItem();
				}
				else if (command.args[0].equals("0")){
					if (command.args[1].equals("B")) {
						playBoardForNet.triggerB();
					} else if (command.args[1].equals("A")) {
						playBoardForNet.triggerSuper();
					}
				}
				break;
			case BONUS:
				if (command.args[0].equals(myTeam.toUpperCase())) {
					playBoardForNet.setScore(Integer.parseInt(command.args[1]));
				} else {
					playBoardForNet.updateScore(Integer.parseInt(command.args[1]));
				}
				break;
			case MOVE:
				if(!isRoomMaster)
				{
					System.out.println(command.toString());
				}
				break;
			case SYNC:
				if(!isGame)
				{
					clearData();
					for(String user:command.args)
					{
						String[] infos=user.split(",");
						enter(infos[0],Integer.parseInt(infos[1]),infos[2]);
					}
				}
				
				break;
			case REPLY:
				if(command.args[0].equals("IN_WAITING_ROOM"))
				{
					quit(username);
					Client.waitingRoom.enter(username,portrait,"false");
				}
				break;
			case END:
				isGame=false;
				StageController.returnToMain(netGameBoard);
				int myScore = playBoardForNet.getScore(myTeam);
				String anotherTeam = myTeam.equals("False")?"True":"False";
				int anotherScore = playBoardForNet.getScore(anotherTeam);
				String result = "";
				if (anotherScore!=0){
					if (myScore>anotherScore) {
						result = "恭喜您，您的团队取得对战胜利";
					} else {
						result = "很遗憾，您输掉了本场对战";

					}
					DialogCreator.oneButtonDialog("对战结果", result);
				}
				if(!isRoomMaster)
				{
					cancelPrepare();
				}
				break;
				
		}
	}
	
	public void cancelPrepare(String username)
	{
		for(int i=0;i<4;i++)
		{
			if(players[i]!=null&&players[i].equals(username.toUpperCase()))
			{
				myPanel.updatePlayer(i,username,portrates[i],0,teamId[i]);
				states[i]=0;
			}
		}
	}
	
	public void prepare(String username)
	{
		for(int i=0;i<4;i++)
		{
			if(players[i]!=null&&players[i].equals(username.toUpperCase()))
			{
				myPanel.updatePlayer(i,username,portrates[i],1,teamId[i]);
				states[i]=1;
			}
		}
	}
	
	public void changeTeam(String username,String teamId)
	{
		for(int i=0;i<4;i++)
		{
			if(players[i]!=null&&players[i].equals(username))
			{
				this.teamId[i]=teamId.toUpperCase();
				myPanel.changeTeam(i,teamId);
				break;
			}
		}
	}
	
	public boolean hasTeamMaster(String team)
	{
		for(String string:teamId)
		{
			if(team.equals(string))
				return true;
		}
		return false;
	}
	
	@Override public boolean enter(String username,int portrait,String team)
	{
		if(playerNum<4)
		{
			for(int i=0;i<4;i++)
			{
				if(players[i]==null)
				{
					players[i]=username;
					portrates[i]=portrait;
					if(!hasTeamMaster(team.toUpperCase())&&username.equals(this.username))
					{
						isTeamMaster=true;
					}
					teamId[i]=team.toUpperCase();
					if(playerNum==0)
					{
						myPanel.updatePlayer(i,username,portrait,2,team);
						if(username.equals(this.username))
						{
							isRoomMaster=true;
							states[i]=2;
							myPanel.prepareButton.setIcon(new ImageIcon(Constant.PictureSrc.gameroom_startgame));
						}
					}
					else
					{
						myPanel.updatePlayer(i,username,portrait,states[i],team);
					}
					playerNum++;
					return true;
				}
			}
		}
		return false;
	}
	
	@Override public boolean quit(String username)
	{
		for(int i=0;i<4;i++)
		{
			if(players[i]!=null&&players[i].equals(username))
			{
				players[i]=null;
				portrates[i]=-1;
				myPanel.updatePlayer(i,null,-1,0,teamId[i]);
				if(username.equals(this.username))
				{
					clearData();
					StageController.returnToMain(myPanel);
					isRoomMaster=false;
					isTeamMaster=false;
				}
				else
				{
					playerNum--;
				}
				return true;
			}
		}
		return false;
	}
	
	public String getMapString()
	{
		String mapString="";
		int[][] myMap=playBoardForNet.getMap();
		for(int i=0;i<Constant.Config.MAP_SIZE;i++)
		{
			for(int j=0;j<Constant.Config.MAP_SIZE;j++)
			{
				mapString+=myMap[i][j]+" ";
			}
		}
		return mapString;
	}
	
	public void clearData()
	{
		for(int i=0;i<4;i++)
		{
			players[i]=null;
			portrates[i]=-1;
			teamId[i]=null;
			myPanel.updatePlayer(i,null,-1,0,"false");
		}
		isTeamMaster=false;
		isRoomMaster=false;
		playerNum=0;
	}
	
	public void swap(ACTION action,Point point)
	{
		isWaitingResult=true;
		if (point==null) {
			isWaitingResult=false;
			playBoardForNet.unLock();
			playBoardForNet.waiting =false;
			return;
		}
		swapComment=(int)point.getX()+" "+(int)point.getY()+" "+action.getTag();
		String command="MOVE 0 "+swapComment;
		listener.write(command);
	}
	
	public String swapComment=null;
	
	public void sendDropDown(int[][] fillMap, ArrayList<Point> points)
	{
		// DROPDOWN 1 side information
		String rawCommand="DROPDOWN";
		for(int i=0;i<Constant.Config.MAP_SIZE;i++)
		{
			for(int j=0;j<Constant.Config.MAP_SIZE;j++)
			{
				rawCommand+=" "+fillMap[i][j];
			}
		}
		for (int i = 0; i < points.size(); i++) {
			rawCommand+=" "+points.get(i).x+";"+points.get(i).y;
		}
		listener.write(rawCommand);
	}
	
	public void end(boolean isFalse)
	{
//		isGame=false;
		if(!isFalse)
		{
			if(isTeamMaster)
			{
				listener.write("END 0");
//				StageController.returnToMain(netGameBoard);
			}
		}
		else
		{
			listener.write("EXIT 0 "+username);
			Media.playBGM(Media.MAIN);
			StageController.returnToMain(1);
		}
	}
	
	public void writeClear(ArrayList<Point> clearList,Point selectedPoint,ACTION action){
		String swapComment=(int)selectedPoint.getX()+" "+(int)selectedPoint.getY()+" "+action.getTag();
		String newCommand="JUDGE 1 True "+swapComment+" ";
		for(Point point:clearList)
		{
			newCommand=newCommand+point.x+","+point.y+" ";
		}
		listener.write(newCommand);
	}

	public void triggerB() {
		listener.write("ITEM 0 B");
	}


	public void superModel() {
		listener.write("ITEM 0 A");
	}
	
	public void sendScore(int score) {
		listener.write("BONUS "+myTeam+" "+score);
	}

	public void sendHint(int[] hint) {
		String command = "HINT ";
		for (int i = 0; i < hint.length; i++) {
			command = command+hint[i]+" ";
		}
		listener.write(command);
	}

	public boolean isLink() {
		return listener.isLink;
	}
}
