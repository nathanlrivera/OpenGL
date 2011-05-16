package commands;

import java.awt.event.ActionEvent;

public class AddMeteorCommand extends LanderGameAbstractAction
{

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public AddMeteorCommand()
	{
		super("Add Meteor");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		gameModel.addMeteor();
		gameModel.notifyObservers();
		scoreboard.notifyObservers();
	}
}
