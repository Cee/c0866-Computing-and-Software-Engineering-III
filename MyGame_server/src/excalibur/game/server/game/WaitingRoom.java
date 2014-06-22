package excalibur.game.server.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import excalibur.game.server.Constant;
import excalibur.game.server.Launcher;
import excalibur.game.server.listener.Command;
import excalibur.game.server.listener.Command.Operation;
import excalibur.game.server.listener.Listener;

/**
 * Waiting room of the game. Since there is no operation available for clients
 * to operate in the waiting room, no additional functions need to be
 * implemented.
 * 
 * @author 喵叔
 * 
 */
public class WaitingRoom implements Room
{
	private Hashtable<Listener,String> users;
	private Hashtable<Listener,Integer> portraits;
	private int roomNumber;
	
	public WaitingRoom()
	{
		users=new Hashtable<>();
		portraits=new Hashtable<>();
		roomNumber=0;
	}
	
	@Override public void execute(Command command,Listener user) throws IOException
	{
		boolean flag=false;
		
		//In waiting room, server accepts only ENTER and SYNC commands.
		if(command.type==Operation.ENTER)
		{
			if(command.args[0].equals("0"))
			// wrong command ignored
			{
				int roomID=Integer.parseInt(command.args[1]);
				String username=command.args[2];
				int portrait=Integer.parseInt(command.args[3]);
				if(roomID<=Constant.ROOM_COUNT)
					if(Launcher.rooms[roomID].enter(user,username,portrait))
					{
						flag=true;
					}
			}
		}
		
		if(command.type==Operation.SYNC)
		{
			if(command.args[0].equals("0"))
			{
				flag=true;
				// Players' list and room state are passed through 2 Commands.
				// Players' list are passed first, then room state.
				Command players=new Command();
				players.type=Operation.REPLY;
				ArrayList<String> playersStrings=new ArrayList<>();
				System.out.println(users.keySet());
				for(Listener i:users.keySet())
				{
					String iName=users.get(i);
					int iPortrait=portraits.get(i);
					playersStrings.add(iName+","+Integer.toString(iPortrait));
				}
				players.args=playersStrings.toArray(new String[0]);
				user.write(players.toString());
				
				Command roomState=new Command();
				roomState.type=Operation.REPLY;
				ArrayList<String> roomsSpecification=new ArrayList<>();
				for(int i=1;i<=Constant.ROOM_COUNT;i++)
				{
					GameRoom room=(GameRoom)Launcher.rooms[i];
					int playerCount=room.getPlayerCount();
					boolean gaming=room.getGaming();
					roomsSpecification.add(Integer.toString(playerCount)+","+Boolean.toString(gaming));
				}
				roomState.args=roomsSpecification.toArray(new String[0]);
				user.write(roomState.toString());
			}
		}
		if(!flag)
		{
			Command newCommand=new Command("REPLY error");
			user.write(newCommand.toString());
		}
	}
	
	@Override public boolean quit(Listener listener) throws IOException
	{
		boolean ret=false;
		if(users.containsKey(listener))
		{
			String username=users.get(listener);
			
			users.remove(listener);
			portraits.remove(listener);
			ret=true;
			//broadcasting
			Command newCommand=new Command("EXIT 1 "+username);
			for(Listener i:users.keySet())
				i.write(newCommand.toString());
		}
		return ret;
	}
	
	/**
	 * Players' list and room state are passed through 2 Commands. Players' list
	 * are passed first, then room state.
	 * @throws IOException When there's something wrong in the socket.
	 */
	@Override public boolean enter(Listener listener,String username,int portrait) throws IOException
	{
		boolean ret=false;
		
		ArrayList<String> allUserName=new ArrayList<>(users.values());
		for(int i=1;i<=Constant.ROOM_COUNT;i++)
			allUserName.addAll(((GameRoom)Launcher.rooms[i]).getUsers());
		if(!allUserName.contains(username))
		{
			users.put(listener,username);
			portraits.put(listener,portrait);
			
			Command newCommand=new Command("REPLY IN_WAITING_ROOM");
			listener.write(newCommand.toString());
			
			ret=true;
			listener.setState(roomNumber);
			
			//broadcasting
			Command commandToAll=new Command("ENTER 1 "+username+" "+Integer.toString(portrait));
			for(Listener i:users.keySet())
				if(i!=listener)
					i.write(commandToAll.toString());
		}
		else
		{
			Command newCommand=new Command("REPLY error");
			listener.write(newCommand.toString());
		}
		return ret;
	}
	
}
