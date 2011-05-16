package commands;

import java.awt.event.ActionEvent;

/**
 * Command class for firing the left jet on the Lander
 */
public class LeftJetCommand extends LanderGameAbstractAction
{
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public LeftJetCommand()
	{
		super("Left Jet");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		gameModel.fireLeftJet();
		gameModel.notifyObservers();
		scoreboard.notifyObservers();
	}
}
