package commands;

import java.awt.event.ActionEvent;

public class DockCommand extends LanderGameAbstractAction
{
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public DockCommand()
	{
		super("Dock");
	}

	public void actionPerformed(ActionEvent e)
	{
		gameModel.dock();
		gameModel.notifyObservers();
		scoreboard.notifyObservers();
	}
}
