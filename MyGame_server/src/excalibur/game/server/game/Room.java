package excalibur.game.server.game;

import java.io.IOException;

import excalibur.game.server.listener.Command;
import excalibur.game.server.listener.Listener;

/**
 * The abstraction of GameRoom and WaitingRoom.
 * 
 * @author 喵叔
 *
 */
public abstract interface Room
{
	public abstract void execute(Command command, Listener user) throws IOException;
	
	public abstract boolean enter(Listener listener, String username, int portrait) throws IOException;
	
	public abstract boolean quit(Listener listener) throws IOException;
}
