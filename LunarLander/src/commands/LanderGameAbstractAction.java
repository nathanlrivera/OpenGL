package commands;

import javax.swing.AbstractAction;
import game.GameWorld;
import game.EndOfGameException;
import game.Scoreboard;

public abstract class LanderGameAbstractAction extends AbstractAction
{

	
	public LanderGameAbstractAction(String str)
	{
		super(str);
	}
	
	protected GameWorld gameModel;
	public void setGameWorld(GameWorld gw)
	{
		gameModel = gw;
	}
	
	protected Scoreboard scoreboard;
	public void setScoreboard(Scoreboard s)
	{
		scoreboard = s;
	}
	
	protected void printEndOfGame(EndOfGameException e)
	{
		System.out.println(e.getMessage());
		Throwable cause = e.getCause();
		if (cause != null)
			System.out.println(cause.getMessage());
		quit();
	}
	
	protected void quit()
	{
		System.exit(0);
	}
}
