package commands;

import game.EndOfGameException;

import java.awt.event.ActionEvent;

public class ExplodeBombCommand extends LanderGameAbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExplodeBombCommand()
	{
		super("Explode Bomb");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			gameModel.explode();
		}
		catch (EndOfGameException ex)
		{
			printEndOfGame(ex);
		}
		gameModel.notifyObservers();
		scoreboard.notifyObservers();
	}
}
