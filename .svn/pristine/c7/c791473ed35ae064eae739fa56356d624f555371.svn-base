package excalibur.game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import excalibur.game.server.game.GameRoom;
import excalibur.game.server.game.Room;
import excalibur.game.server.game.WaitingRoom;
import excalibur.game.server.listener.Listener;
import excalibur.game.server.staticdata.StaticDataReader;

/**
 * 
 * @author 喵叔
 *
 */
public class Launcher
{
	public static Room rooms[];
	public static StaticDataReader dataReader;
	
	@SuppressWarnings("resource") public static void main(String[] args)
	{
		// initialize zones
		rooms=new Room[Constant.ROOM_COUNT+1];
		rooms[0]=new WaitingRoom();
		for(int i=1;i<=Constant.ROOM_COUNT;i++)
			rooms[i]=new GameRoom(i);
		
		ServerSocket serverSocket=null;
		try
		{
			serverSocket=new ServerSocket(Constant.PORT);
		}
		catch(IOException e)
		{
			System.err.println(e.getLocalizedMessage());
			System.exit(-1);
		}
		
		//load dataReader later may solve EOFException...
		dataReader=new StaticDataReader();
		
		while(true)
		{
			Socket newSocket=null;
			try
			{
				newSocket=serverSocket.accept();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				continue;
			}
			
			//fetch a connection
			System.out.println("client connected, IP:"+newSocket.getInetAddress());
			Runnable jobRunnable=new Listener(newSocket);
			Thread newThread=new Thread(jobRunnable);
			newThread.start();
		}
	}
	
}
