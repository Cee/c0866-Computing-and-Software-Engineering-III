package excalibur.game.server.game;

import excalibur.game.server.listener.Listener;
import excalibur.game.server.listener.Result;

public interface Room
{
	public Result command(String cmd);
	
	public Result enter(Listener listener);
	
	public Result quit(Listener listener);
}
