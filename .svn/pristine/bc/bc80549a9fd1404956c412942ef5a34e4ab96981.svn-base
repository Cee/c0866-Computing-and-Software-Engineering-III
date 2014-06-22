package excalibur.game.server.listener;

import java.io.IOException;
import java.util.ArrayList;

import excalibur.game.server.Launcher;
import excalibur.game.server.game.Room;

/**
 * A data class to present the command. Since it has some tool-like functions,
 * all of its methods are public. All functions that need to cooperate with
 * interface Room are implemented in WaitingRoom and GameRoom.
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
 * 		EXIT 0 username						<---This for request to server
 * 		EXIT 1 username						<---This for broadcasting to other users in the room
 * 		PREPARE 0 username					<---This for request to server
 * 		PREPARE 1 username					<---This for broadcasting to other users in the room
 * 		TEAM 0 username side(True/False)	<---This for request to server
 * 		TEAM 1 username side(True/False)	<---This for broadcasting to other users in the room
 * 		CANCEL_PREPARE 0 username			<---This for request to server
 * 		CANCEL_PREPARE 1 username			<---This for broadcasting to other users in the room
 * 		START
 * 		MOVE 0 side pointX pointY directionHorizonal directionVertical
 * 		MOVE 1 side pointX pointY directionHorizonal directionVertical	<---This for replying to all clients
 * 		DROPDOWN 0 side						<---This for request to server
 * 		DROPDOWN 1 side information			<---This for broadcasting to other users in the room
 * 		ITEM 0 A/B							<---This for team masters' notification
 * 		ITEM 1								<---This for changing to item selection mode
 * 		JUDGE 0 pointX pointY directionHorizonal directionVertical
 * 		JUDGE 1 True/False					<---This for replying to client
 * 		BONUS 0 side(True/False) score		<---This for request to server
 * 		BONUS 1 score						<---This for broadcasting to opposite side, implying opposite's current score
 * 		BONUS 2 teamAscore teamBscore		<---This for informing every client of final result
 * 		SYNC 0								<---This for request
 * 		SYNC 1 detailed_information			<---This for broadcasting to other users in the room
 * 		SCORE 0								<---This for getting all users high score
 * 		SCORE 1 username					<---This for getting the specified user's high score
 * 		SCORE 2 username score				<---This for uploading a new score(net necessarily the high score)
 * 		SCORE 3 information					<---This for replying to other SCORE commands
 * 		END information						<---This for room master notifying other players
 * 		REPLY information					<---Other circumstances of server's reply
 * 		END 0								<---The signal for teamMaster to send for end the game
 * 		HINT content						<---This is for sending the hint information to users who are not teamMaster
 * 
 * @author 喵叔
 * 
 */
public class Command
{
	public enum Operation
	{
		ENTER,EXIT,PREPARE,CANCEL_PREPARE,TEAM,START,MOVE,DROPDOWN,ITEM,JUDGE,BONUS,SYNC,SCORE,END,REPLY,HINT;
	}
	
	public Operation type;
	public String[] args;
	
	public Command()
	{
	}
	
	public Command(String rawCommand)
	{
		String[] raw=null;
		try
		{
			raw=rawCommand.toUpperCase().split(" ",2);
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			return;
		}
		switch(raw[0])
		{
			case "ENTER":
				type=Operation.ENTER;
				break;
			case "EXIT":
				type=Operation.EXIT;
				break;
			case "PREPARE":
				type=Operation.PREPARE;
				break;
			case "CANCEL_PREPARE":
				type=Operation.CANCEL_PREPARE;
				break;
			case "TEAM":
				type=Operation.TEAM;
				break;
			case "START":
				type=Operation.START;
				break;
			case "MOVE":
				type=Operation.MOVE;
				break;
			case "DROPDOWN":
				type=Operation.DROPDOWN;
				break;
			case "ITEM":
				type=Operation.ITEM;
				break;
			case "JUDGE":
				type=Operation.JUDGE;
				break;
			case "BONUS":
				type=Operation.BONUS;
				break;
			case "SYNC":
				type=Operation.SYNC;
				break;
			case "SCORE":
				type=Operation.SCORE;
				break;
			case "END":
				type=Operation.END;
				break;
			case "REPLY":
				type=Operation.REPLY;
				break;
			case "HINT":
				type=Operation.HINT;
		}
		if(raw.length==2)
		{
			args=raw[1].split(" ");
		}
	}
	
	public void process(Listener listener) throws IOException
	{
		if(type==Operation.SCORE)
		{
			Command newCommand=new Command();
			ArrayList<String> args=new ArrayList<>();
			switch(this.args[0])
			{
				case "0":
					newCommand.type=Operation.SCORE;
					args.add("3");
					args.add(Launcher.dataReader.getAllStaticData());
					newCommand.args=args.toArray(new String[0]);
					listener.write(newCommand.toString());
					break;
				case "1":
					newCommand.type=Operation.SCORE;
					args.add("3");
					args.add(Launcher.dataReader.getScoreByName(this.args[1]));
					newCommand.args=args.toArray(new String[0]);
					listener.write(newCommand.toString());
					break;
				case "2":
					newCommand.type=Operation.SCORE;
					args.add(Boolean.toString(Launcher.dataReader.upload(this.args[1],Integer.parseInt(this.args[2]))));
					newCommand.args=args.toArray(new String[0]);
					listener.write(newCommand.toString());
					break;
				default:
					// do nothing here
					break;
			}
		}
		else
		{
			Room currentRoom=Launcher.rooms[listener.getState()];
			currentRoom.execute(this,listener);
		}
	}
	
	@Override public String toString()
	{
		String ret=new String();
		ret+=type.name()+" ";
		if(args!=null)
		{
			for(int i=0;i<args.length;i++)
				ret+=args[i]+" ";
		}
		return ret.trim();
	}
}
