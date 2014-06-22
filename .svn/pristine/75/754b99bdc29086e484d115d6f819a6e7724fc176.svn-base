package excalibur.game.logic.networkadapter;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.logic.syslogic.PrefferenceLogic;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.DialogCreator;


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

/**
 * 以上注释来自服务器
 * @author luck-mac
 *
 */
public class Client {
	public static Room rooms[];
	public static WaitingRoom waitingRoom;
	public static Listener listener;
	public static Socket socket;
	public Client(){
		//默认在等待室
		launchConnection();
		
	}
	
	public static GameRoom getEmptyRoom(){
		for (int i = 1; i <= Constant.NetWork.ROOMCOUNT; i++){
			GameRoom gameRoom = (GameRoom)rooms[i];
			if (gameRoom.playerNum==0){
				return gameRoom;
			}
		}
		return null;
	}
	public static WaitingRoom getWaitingRoom() {
		return waitingRoom;
	}
	
	
	public static void main(String[] args){
		Client client = new Client();
		waitingRoom.enterRoom(1);
	}
	
	
	public static void launchConnection(){
		if (socket!=null) {
			return;
		}
		try {
		   socket = new Socket(Constant.NetWork.SERVERIP, Constant.NetWork.PORT);
		   listener = new Listener(socket);
		   new Thread(listener).start();
		   rooms=new Room[Constant.NetWork.ROOMCOUNT+1];
		   rooms[0]=new WaitingRoom(listener);
			waitingRoom=(WaitingRoom) rooms[0];
			for(int i=1;i<=Constant.NetWork.ROOMCOUNT;i++)
				rooms[i]=new GameRoom(i,listener);	
		} catch (UnknownHostException e) {
			//界面层等待完善
			System.err.println("连接不到服务器");
			DialogCreator.oneButtonDialog("错误100", "未知主机");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			DialogCreator.oneButtonDialog("错误101", "服务器连接超时，本地连接限制或服务器正在维护");
			e.printStackTrace();
			return;
		}
	}
	
	public static void getHighestScores(){
		listener.write("score 0");
	}
	public static void writeMyScore(){
		listener.write("score 2 "+PrefferenceLogic.getUserName()+" "+DataUtils.getInstance().getMaxScore());
	}
	public static void close(){
		if (socket!=null) {
			try {
				socket.close();
				socket=null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
