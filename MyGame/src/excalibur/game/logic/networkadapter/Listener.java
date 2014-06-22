package excalibur.game.logic.networkadapter;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import excalibur.game.presentation.myuicomponent.DialogCreator;
import excalibur.game.presentation.tools.StageController;


public class Listener implements Runnable{
	Socket socket;
	ObjectInputStream inputStream;
	ObjectOutputStream outputStream;
	int state ;
	boolean isLink;
	boolean isWriting;
	public boolean isLink() {
		return isLink;
	}
	public Listener(Socket socket){
		isLink = true;
		this.socket = socket ;
		this.state = 0;
		try {
			inputStream  = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			DialogCreator.oneButtonDialog("错误", "网络连接中断，请尝试重连");
			disconnect();
		}
		isWriting=false;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	public void run(){		
		String cmd=null;
		while(true)
		{
			boolean out=false;
			do
			{
				try
				{
					cmd=inputStream.readUTF();
					System.out.println(cmd);
				}
				catch(EOFException e)
				{
					out=true;
				}
				catch(IOException e)
				{
					//e.printStackTrace();
				}
			}while(cmd==null);
			if(out)
			{
				break;
			}
			Command command=new Command(cmd);
			command.process(this);
		}
	}
	
	public String read()
	{
		String ret=null;
		while(ret!=null)
		{
			try
			{
				ret=inputStream.readUTF();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				DialogCreator.oneButtonDialog("错误", "网络连接中断，请尝试重连");
				disconnect();

			}
		}
		return ret;
	}
	
	public void write(String str)
	{
		isWriting=true;
		try
		{
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			if (!str.startsWith("SYNC")) {
				System.out.println("发送信息"+str);
			}
			outputStream.writeUTF(str);
			outputStream.flush();
		}
		catch(IOException e)
		{
			DialogCreator.oneButtonDialog("错误", "网络连接中断，请尝试重连");
			disconnect();
			e.printStackTrace();
		}
		isWriting=false;
	}
	
	public void disconnect()
	{
		try {
			isLink=false;
			socket.close();
			socket=null;
			System.out.println("close");
			Client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StageController.returnToMain(100000);
	}
	
	
}
