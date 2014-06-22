package excalibur.game.server.listener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;

import excalibur.game.server.Launcher;

/**
 * A thread to receive and deliver different clients' commands.
 * 
 * @author 喵叔
 * 
 */
public class Listener implements Runnable
{
	private Socket socket;
	private int state;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	public Listener(Socket socket)
	{
		this.socket=socket;
		// 默认在等待室中
		state=0;
		// get output stream
		try
		{
			outputStream=new ObjectOutputStream(socket.getOutputStream());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override public void run()
	{
		String cmd=null;
		while(true)
		{
			
			// get input stream
			try
			{
				
				inputStream=new ObjectInputStream(socket.getInputStream());
			}
			catch(StreamCorruptedException e)
			{
				System.err.println("AC");
				e.printStackTrace();
				continue;
			}
			catch(SocketException e)
			{
				e.printStackTrace();
				try
				{
					Launcher.rooms[state].quit(this);
					Launcher.rooms[0].quit(this);
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
				break;
			}
			catch(IOException e)
			{
				e.printStackTrace();
				try
				{
					Launcher.rooms[state].quit(this);
					Launcher.rooms[0].quit(this);
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
				break;
			}
			
			boolean out=false;
			do
			{
				try
				{
					cmd=inputStream.readUTF();
					System.out.println("服务端：接收命令"+cmd);
				}
				catch(StreamCorruptedException e)
				{
					e.printStackTrace();
					continue;
				}
				catch(IOException e)
				{
					e.printStackTrace();
					out=true;
					try
					{
						socket.close();
					}
					catch(IOException e1)
					{
						e1.printStackTrace();
						break;
					}
				}
			}while(cmd==null);
			if(out)
			{
				try
				{
					Launcher.rooms[state].quit(this);
					Launcher.rooms[0].quit(this);
				}
				catch(IOException e)
				{
					try
					{
						Launcher.rooms[state].quit(this);
						Launcher.rooms[0].quit(this);
					}
					catch(IOException e1)
					{
						e1.printStackTrace();
					}
				}
				break;
			}
			Command command=new Command(cmd);
			try
			{
				command.process(this);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				break;
			}
		}
	}
	
	public String read() throws IOException
	{
		String ret=null;
		while(ret!=null)
		{
			ret=inputStream.readUTF();
		}
		return ret;
	}
	
	public void write(String str) throws IOException
	{
		System.out.println("服务器发出指令："+str);
		outputStream.writeUTF(str);
		outputStream.flush();
	}
	
	public int getState()
	{
		return state;
	}
	
	public void setState(int state)
	{
		this.state=state;
	}
}
