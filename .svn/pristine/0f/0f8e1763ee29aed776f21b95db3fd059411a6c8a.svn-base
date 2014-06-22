package excalibur.game.logic.networkadapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.DefaultListModel;

import excalibur.game.logic.syslogic.PrefferenceLogic;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.RoomButton;
import excalibur.game.presentation.tools.StageController;
import excalibur.game.presentation.ui.GameRoomPanel;
import excalibur.game.presentation.ui.WaitingRoomPanel;

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
 * 		ENTER 0 room_id	username portrait	<---This for request to server
 * 		ENTER 1 username portrait			<---This for broadcasting to other users in the room
 * 		友情提醒：从一个游戏房间直接进入另一个游戏房间是不允许的~
 * 		EXIT 0 username					<---This for request to server
 * 		EXIT 1 username					<---This for broadcasting to other users in the room
 * 		PREPARE 0 username				<---This for request to server
 * 		PREPARE 1 username				<---This for broadcasting to other users in the room
 * 		TEAM 0 username side			<---This for request to server
 * 		TEAM 1 username side			<---This for broadcasting to other users in the room
 * 		CANCEL_PREPARE 0 username		<---This for request to server
 * 		CANCEL_PREPARE 1 username		<---This for broadcasting to other users in the room
 * 		START
 * 		MOVE 0 side pointX pointY directionHorizonal directionVertical
 * 		MOVE 1 side pointX pointY directionHorizonal directionVertical	<---This for replying to all clients
 * 		DROPDOWN 0 side					<---This for request to server
 * 		DROPDOWN 1 side information		<---This for broadcastring to other users in the room
 * 		JUDGE 0 pointX pointY directionHorizonal directionVertical
 * 		JUDGE 1 True/False				<---This for replying to client
 * 		SYNC 0							<---This for request
 * 		SYNC 1 detailed_information		<---This for broadcasting to other users in the room
 * 		SCORE 0							<---This for getting all users high score
 * 		SCORE 1 username				<---This for getting the specified user's high score
 * 		SCORE 2 username score			<---This for uploading a new score(net necessarily the high score)
 * 		SCORE 3 information				<---This for replying to other SCORE commands
 * 		REPLY information				<---Other circumstances of server's reply
 * 
 * @author 喵叔
 * 
 */

public class WaitingRoom implements Room
{
	private String userName;
	private int portrait;
	Listener listener;
	HashMap<String,Integer> users;
	WaitingRoomPanel waitingRoomPanel;
	RoomButton[] rooms;
	GameRoom myGameRoom;
	SYNCThread sync;
	boolean isLoadedUsers;
	// 默认在等待室
	public boolean isIn;
	public int selectedRoomId;
	public String[] iconList=Constant.PictureSrc.getSculpture();
	
	public WaitingRoom(Listener listener)
	{
		// 此处从设置中取得
		this.userName=PrefferenceLogic.getUserName();
		isIn=true;
		this.listener=listener;
		users=new HashMap<>();
		isLoadedUsers=false;
		for(int i=0;i<iconList.length;i++)
		{
			if(iconList[i].equals(PrefferenceLogic.getPictureName()))
			{
				portrait=i;
				break;
			}
		}
	}
	
	public void setWaitingRoomPanel(WaitingRoomPanel waitingRoomPanel)
	{
		this.waitingRoomPanel=waitingRoomPanel;
	}
	
	public void enterRoom(int roomId)
	{
		if(isIn)
		{
			selectedRoomId=roomId;
			String rawCommand="Enter 0 "+roomId+" "+userName+" "+portrait;
			listener.write(rawCommand);
		}
	}
	
	@Override public void execute(Command command)
	{
		if(listener.isLink)
		{
			switch(command.type)
			{
				case REPLY:
					if(command.args[0].equals("IN_WAITING_ROOM"))
					{
						waitingRoomPanel=new WaitingRoomPanel();
						addListener();
						rooms=waitingRoomPanel.getRooms();
						myGameRoom=null;
						StageController.moveToStage(waitingRoomPanel);
						if(sync==null)
						{
							sync=new SYNCThread();
							new Thread(sync).start();
						}
					}
					else
						if(command.args[0].equals("IN_GAMEROOM"))
						{
							GameRoomPanel gameRoomPanel=new GameRoomPanel();
							myGameRoom=((GameRoom)Client.rooms[selectedRoomId]);
							myGameRoom.setMyPanel(gameRoomPanel);
							listener.setState(selectedRoomId);
							StageController.moveToStage(gameRoomPanel);
							listener.write("SYNC 0");
							isLoadedUsers=false;
						}
						else
							if(!isLoadedUsers)
							{
								loadUser(command.args);
								isLoadedUsers=true;
							}
							else
							{
								loadRoom(command.args);
								isLoadedUsers=false;
							}
					break;
				
				default:
					break;
			}
		}
	}
	
	public void loadRoom(String[] rooms)
	{
		for(int i=0;i<rooms.length;i++)
		{
			String[] roomInfo=rooms[i].split(",");
			this.rooms[i].setInfo(roomInfo);
		}
	}
	
	public void loadUser(String[] users)
	{
		DefaultListModel<String> model=(DefaultListModel<String>)waitingRoomPanel.list.getModel();
		model.clear();
		for(String user:users)
		{
			String[] userInfo=user.split(",");
			this.users.put(userInfo[0],Integer.parseInt(userInfo[1]));
			System.out.println("User in WaitingRoom "+userInfo[0]);
			model.addElement(userInfo[0]);
		}
	}
	
	@Override public boolean enter(String username,int portrait,String teamInfo)
	{
		listener.setState(0);
		return true;
	}
	
	@Override public boolean quit(String username)
	{
		return false;
	}
	
	public void addListener()
	{
		waitingRoomPanel.quitWaitingRoomBt.addActionListener(new ReturnListener());
		waitingRoomPanel.createRoomBt.addActionListener(new CreateRoomListener());
	}
	
	class CreateRoomListener implements ActionListener
	{
		@Override public void actionPerformed(ActionEvent e)
		{
			Media.playSound(Media.ENTER);
			GameRoom room=Client.getEmptyRoom();
			room.isRoomMaster=true;
			enterRoom(room.getRoomId());
			Media.playBGM(Media.WAITING);
		}
		
	}
	
	class ReturnListener implements ActionListener
	{
		
		@Override public void actionPerformed(ActionEvent e)
		{
			Media.playBGM(Media.MAIN);
			StageController.returnToMain(waitingRoomPanel);
			listener.disconnect();
		}
	}
	
	class SYNCThread implements Runnable
	{
		@Override public void run()
		{
			while(listener!=null)
			{
				if (!listener.isLink) {
					break;
				}
				if(myGameRoom!=null&&myGameRoom.isGame&&listener.isLink)
				{
					// if (myGameRoom.isRoomMaster()){
					// String command = "SYNC 0 ";
					// command+=myGameRoom.getMapString();
					// listener.write(command);
					// }
				}
				else
				{
					if(!listener.isWriting)
					{
						listener.write("SYNC 0");
					}
				}
				try
				{
					Thread.sleep(10000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
}
