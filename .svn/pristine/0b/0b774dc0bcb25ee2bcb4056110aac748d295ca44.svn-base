package excalibur.game.server.listener;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import excalibur.game.server.Launcher;

/**
 * 
 * @author 喵叔
 * 
 */
public class Listener implements Runnable
{
	private Socket socket;
	private int state;
	
	public Listener(Socket socket)
	{
		this.socket=socket;
		// 默认在等待室中
		state=0;
	}
	
	@Override public void run()
	{
		// get input stream
		ObjectInputStream inputStream=null;
		try
		{
			inputStream=new ObjectInputStream(socket.getInputStream());
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get output stream
		ObjectOutputStream outputStream=null;
		try
		{
			outputStream=new ObjectOutputStream(socket.getOutputStream());
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String cmd=null;
		while(true)
		{
			boolean out=false;
			do
			{
				try
				{
					cmd=inputStream.readUTF();
				}
				catch(EOFException e)
				{
					out=true;
				}
				catch(IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}while(cmd==null);
			if(out)
			{
				Launcher.rooms[state].quit(this);
				break;
			}
			Result result=Launcher.rooms[state].command(cmd);
			if(result.changeState)
				Launcher.rooms[result.changeToState].enter(this);
			try
			{
				outputStream.writeObject(result.ret);
			}
			catch(IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
