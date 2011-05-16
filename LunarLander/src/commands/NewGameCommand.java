package commands;

import game.LanderGame;

import java.awt.event.ActionEvent;

public class NewGameCommand extends LanderGameAbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LanderGame game;

	public NewGameCommand(LanderGame l)
	{
		super("New Game");
		game = l;
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		game.newGame();
	}
}
