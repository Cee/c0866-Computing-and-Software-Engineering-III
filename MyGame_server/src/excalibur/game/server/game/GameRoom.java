package excalibur.game.server.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import excalibur.game.server.Launcher;
import excalibur.game.server.listener.Command;
import excalibur.game.server.listener.Command.Operation;
import excalibur.game.server.listener.Listener;

/**
 * Game room of the game. It just simply passes messages to the room master or
 * team leader and broadcasts corresponding results.
 * 
 * @author 喵叔
 * 
 */
public class GameRoom implements Room
{
	private Hashtable<Listener,String> users;
	private Hashtable<Listener,Integer> portraits;
	private Hashtable<Listener,Boolean> sides;
	private static final int ROOM_SIZE=4;
	private Listener roomMaster;
	private Listener teamAMaster;
	private Listener teamBMaster;
	private int roomNumber;
	private boolean gaming;
	private int endTeamCount;
	
	public GameRoom(int roomNumber)
	{
		users=new Hashtable<>();
		sides=new Hashtable<>();
		portraits=new Hashtable<>();
		roomMaster=null;
		teamAMaster=null;
		teamBMaster=null;
		portraits=new Hashtable<>();
		this.roomNumber=roomNumber;
		gaming=false;
		// This for receiving END commands
		endTeamCount=0;
	}
	
	@Override public void execute(Command command,Listener user) throws IOException
	{
		Command newCommand=new Command();
		ArrayList<String> args=new ArrayList<>();
		switch(command.type)
		{
			case ENTER:
			case SCORE:
			case REPLY:
				// Since ENTER, SCORE and REPLY commands are prohibited from
				// sending to client positively, server should return an error
				// reply.
				newCommand.type=Operation.REPLY;
				newCommand.args=new String[]{"error"};
				user.write(newCommand.toString());
				break;
			case JUDGE:
				// returned from team master
				if(command.args[0].equals("1"))
				{
					// move rejected
					if(command.args[1].toUpperCase().equals("FALSE"))
					{
						Command replyCommand=new Command("JUDGE 1 False");
						
						user.write(replyCommand.toString());
						for(Listener listener:users.keySet())
						{
							// user here must be one of the team masters
							if(listener!=user&&sides.get(user)==sides.get(listener))
								listener.write(replyCommand.toString());
						}
					}
					// move accepted, this action need to be broadcast to all
					// team members
					else
					{
						Command commandToAll=new Command();
						commandToAll.type=Operation.JUDGE;
						args.clear();
						for(int i=1;i<command.args.length;i++)
							args.add(command.args[i]);
						commandToAll.args=args.toArray(new String[0]);
						for(Listener listener:users.keySet())
						{
							// user here must be one of the team masters
							if(listener!=user&&sides.get(user)==sides.get(listener))
								listener.write(commandToAll.toString());
						}
					}
				}
				// illegal command
				else
				{
					command=new Command("REPLY error");
					user.write(command.toString());
				}
				break;
			case HINT:
				if(user==teamAMaster||user==teamBMaster)
					for(Listener listener:users.keySet())
					{
						// user here must be one of the team masters
						if(listener!=user&&sides.get(user)==sides.get(listener))
						{
							listener.write(command.toString());
						}
					}
				// illegal command
				else
				{
					command=new Command("REPLY error");
					user.write(command.toString());
				}
				break;
			case EXIT:
				if(command.args[0].equals("0"))
				{
					int potrait=portraits.get(user);
					if(quit(user))
						Launcher.rooms[0].enter(user,command.args[1],potrait);
				}
				// wrong command ignored
				else
				{
					newCommand.type=Operation.REPLY;
					newCommand.args=new String[]{"error"};
					user.write(newCommand.toString());
				}
				break;
			case PREPARE:
				if(command.args[0].equals("0")&&!gaming)
				{
					newCommand.type=Operation.PREPARE;
					args.add("1");
					args.add(users.get(user));
					newCommand.args=args.toArray(new String[0]);
					for(Listener listener:users.keySet())
						listener.write(newCommand.toString());
				}
				// wrong command ignored
				else
				{
					newCommand=new Command("REPLY error");
					user.write(newCommand.toString());
				}
				break;
			case TEAM:
				if(command.args[0].equals("0"))
				{
					// broadcasting
					newCommand.type=Operation.TEAM;
					args.add("1");
					args.add(users.get(user));
					args.add(command.args[2]);
					newCommand.args=args.toArray(new String[0]);
					for(Listener listener:users.keySet())
						listener.write(newCommand.toString());
					sides.put(user,Boolean.parseBoolean(command.args[2]));
					
					// for potential team master change
					if(teamAMaster==null&&!Boolean.parseBoolean(command.args[2]))
					{
						if(teamBMaster==user)
						{
							teamBMaster=null;
							for(Listener i:sides.keySet())
								if(sides.get(i))
									teamBMaster=i;
						}
						teamAMaster=user;
					}
					if(teamBMaster==null&&Boolean.parseBoolean(command.args[2]))
					{
						if(teamAMaster==user)
						{
							teamAMaster=null;
							for(Listener i:sides.keySet())
								if(!sides.get(i))
									teamAMaster=i;
						}
						teamBMaster=user;
					}
				}
				else
				{
					newCommand=new Command("REPLY error");
					user.write(newCommand.toString());
				}
				break;
			case CANCEL_PREPARE:
				if(command.args[0].equals("0"))
				{
					newCommand.type=Operation.CANCEL_PREPARE;
					args.add("1");
					args.add(users.get(user));
					newCommand.args=args.toArray(new String[0]);
					for(Listener listener:users.keySet())
						listener.write(newCommand.toString());
				}
				// wrong command ignored
				else
				{
					newCommand=new Command("REPLY error");
					user.write(newCommand.toString());
				}
				break;
			case START:
				if(user==roomMaster)
				{
					// change game state
					gaming=true;
					for(Listener listener:users.keySet())
						listener.write(command.toString());
				}
				// wrong command ignored
				else
				{
					newCommand=new Command("REPLY error");
					user.write(newCommand.toString());
				}
				break;
			case MOVE:
				if(command.args[0].equals("0"))
				{
					newCommand.type=Operation.JUDGE;
					args.add("0");
					for(int i=1;i<command.args.length;i++)
						args.add(command.args[i]);
					newCommand.args=args.toArray(new String[0]);
					// determine who to send judge request to
					Listener myTeamMaster=sides.get(user)?teamBMaster:teamAMaster;
					myTeamMaster.write(newCommand.toString());
				}
				// wrong command ignored
				else
				{
					newCommand=new Command("REPLY error");
					user.write(newCommand.toString());
				}
				break;
			case DROPDOWN:
				for(Listener listener:sides.keySet())
				{
					// only send command to users in the same team
					if(listener!=user&&sides.get(listener)==sides.get(user))
						listener.write(command.toString());
				}
				break;
			case ITEM:
			{
				boolean processSuccess=false;
				if(command.args[0].equals("0")&&(user==teamAMaster||user==teamBMaster)&&gaming)
				{
					processSuccess=true;
					boolean side=sides.get(user);
					Command broadcastCommand=new Command();
					broadcastCommand.type=Operation.ITEM;
					broadcastCommand.args=command.args;
					for(Listener i:users.keySet())
						if(sides.get(i)!=side)
							i.write(broadcastCommand.toString());
				}
				// The command "ITEM 1" should be sent to every client once
				// after the game starts, so any clients' active sending is
				// illegal.
				if(command.args[0].equals("1")&&user==roomMaster)
				{
					processSuccess=true;
					// start notifying every client item selection is being done
					for(Listener i:users.keySet())
						i.write(command.toString());
				}
				if(command.args[0].equals("2")&&(user==teamAMaster||user==teamBMaster)&&gaming)
				{
					processSuccess=true;
					
					// make notification command
					boolean side=Boolean.parseBoolean(command.args[1]);
					Command broadcastCommand=new Command();
					broadcastCommand.type=Operation.ITEM;
					args.clear();
					for(int i=1;i<command.args.length;i++)
						args.add(command.args[i]);
					broadcastCommand.args=args.toArray(new String[0]);
					for(Listener i:users.keySet())
						if(sides.get(i)==side)
							i.write(broadcastCommand.toString());
				}
				
				if(!processSuccess)
				{
					newCommand=new Command("REPLY error");
					user.write(newCommand.toString());
				}
				break;
			}
			case BONUS:
			{
				boolean processSuccess=false;
				if((user==teamAMaster||user==teamBMaster)&&gaming)
				{
					processSuccess=true;
					
					for(Listener i:users.keySet())
						if(i!=user)
							i.write(command.toString());
					newCommand=new Command("REPLY success");
					user.write(newCommand.toString());
				}
				
				if(!processSuccess)
				{
					newCommand=new Command("REPLY error");
					user.write(newCommand.toString());
				}
				break;
			}
			case SYNC:
				if(command.args[0].equals("0"))
				{
					if(gaming)
					{
						roomMaster.write(command.toString());
						Command syncCommand=new Command(roomMaster.read());
						// for(Listener listener:users.keySet())
						// listener.write(syncCommand.toString());
						user.write(syncCommand.toString());
					}
					else
					{
						// Within all players' information, the first is the
						// room master.
						// All that to be returned are tuples like:
						// username,portrait,side.
						Command syncCommand=new Command();
						syncCommand.type=Operation.SYNC;
						ArrayList<String> usersStrings=new ArrayList<>();
						usersStrings.add(users.get(roomMaster)+","+Integer.toString(portraits.get(roomMaster))+","+Boolean.toString(sides.get(roomMaster)));
						for(Listener i:users.keySet())
							if(i!=roomMaster)
							{
								String playerName=users.get(i);
								int portrait=portraits.get(i);
								boolean side=sides.get(i);
								usersStrings.add(playerName+","+Integer.toString(portrait)+","+Boolean.toString(side));
							}
						syncCommand.args=usersStrings.toArray(new String[0]);
						// who asks for sync, who to send sync details to
						user.write(syncCommand.toString());
					}
				}
				// wrong command ignored
				else
				{
					newCommand=new Command("REPLY error");
					user.write(newCommand.toString());
				}
				break;
			case END:
				if(user==teamAMaster||user==teamBMaster)
				{
					endTeamCount++;
					// co-operation mode
					if(teamAMaster==null||teamBMaster==null)
					{
						gaming=false;
						for(Listener i:users.keySet())
							i.write(command.toString());
					}
					else
						if(endTeamCount==2)
						{
							gaming=false;
							endTeamCount=0;
							for(Listener i:users.keySet())
								i.write(command.toString());
						}
					
				}
				// illegal command
				else
				{
					newCommand=new Command("REPLY error");
					user.write(newCommand.toString());
				}
				break;
			default:
				break;
		}
	}
	
	@Override public boolean quit(Listener listener) throws IOException
	{
		boolean ret=false;
		if(users.containsKey(listener))
		{
			String username=users.get(listener);
			ret=true;
			users.remove(listener);
			sides.remove(listener);
			portraits.remove(listener);
			if(roomMaster==listener)
			{
				// choose a new room master at random
				ArrayList<Listener> allUsers=new ArrayList<>(users.keySet());
				if(allUsers.size()!=0)
					roomMaster=allUsers.get((int)(Math.random()*allUsers.size()));
				else
					roomMaster=null;
			}
			// broadcasting
			Command newCommand=new Command("EXIT 1 "+username);
			for(Listener i:users.keySet())
				i.write(newCommand.toString());
		}
		if(users.isEmpty())
			gaming=false;
		return ret;
	}
	
	@Override public boolean enter(Listener listener,String username,int portrait) throws IOException
	{
		boolean ret=false;
		// has a vacancy
		if(users.size()<ROOM_SIZE)
		{
			// user not in this room currently
			if(!users.containsKey(listener)&&Launcher.rooms[0].quit(listener))
			{
				ret=true;
				if(roomMaster==null)
				{
					// The first to enter an empty room is the default room
					// master
					roomMaster=listener;
				}
				if(teamAMaster==null)
				{
					teamAMaster=listener;
				}
				listener.setState(roomNumber);
				users.put(listener,username);
				// in team A by default
				sides.put(listener,false);
				portraits.put(listener,portrait);
				
				// sending confirm to new user
				Command newCommand=new Command("REPLY IN_GAMEROOM");
				listener.write(newCommand.toString());
				
				// broadcasting
				newCommand=new Command("ENTER 1 "+username+" "+Integer.toString(portrait));
				for(Listener i:users.keySet())
					if(i!=listener)
						i.write(newCommand.toString());
			}
		}
		return ret;
	}
	
	public int getPlayerCount()
	{
		return users.size();
	}
	
	public boolean getGaming()
	{
		return gaming;
	}
	
	public List<String> getUsers()
	{
		return new ArrayList<String>(users.values());
	}
}
