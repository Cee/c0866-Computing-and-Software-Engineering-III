package excalibur.game.server.staticdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import excalibur.game.server.Constant;

/**
 * This class is responsible for the I/O of statistic data.
 * 
 * @author 喵叔
 * 
 */
public class StaticDataReader
{
	private Hashtable<String,Integer> highScore;
	private File staticFile;
	
	public StaticDataReader()
	{
		staticFile=new File(Constant.DATA_FILE);
		
		try
		{
			ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(staticFile));
			highScore=(Hashtable<String,Integer>)inputStream.readObject();
			inputStream.close();
		}
		//most possibly file not exist
		catch(IOException e)
		{
			try
			{
				staticFile.createNewFile();
			}
			catch(IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally
			{
				highScore=new Hashtable<>();
			}
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		staticFile.delete();
		try
		{
			staticFile.createNewFile();
			ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(staticFile));
			outputStream.writeObject(highScore);
			outputStream.flush();
			outputStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public String getAllStaticData()
	{
		String ret="";
		for(String user:highScore.keySet())
			ret+=user+" "+highScore.get(user)+" ";
		return ret.trim();
	}
	
	public String getScoreByName(String user)
	{
		return highScore.get(user).toString();
	}
	
	public boolean upload(String user,int score)
	{
		boolean ret=false;
		if(highScore.containsKey(user))
		{
			//something unnecessary to record
			if(score<=0||highScore.get(user)<score)
			{
				highScore.put(user,score);
				ret=true;
			}
			else
				ret=false;
		}
		else
		{
			highScore.put(user,score);
			ret=true;
		}
		
		//update file
		staticFile.delete();
		try
		{
			staticFile.createNewFile();
			ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(staticFile));
			outputStream.writeObject(highScore);
			outputStream.flush();
			outputStream.close();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
	@Override protected void finalize() throws Throwable
	{
		super.finalize();
		staticFile.delete();
		staticFile.createNewFile();
		ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(staticFile));
		outputStream.writeObject(highScore);
		outputStream.flush();
		outputStream.close();
	}
}
