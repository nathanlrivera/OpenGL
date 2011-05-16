package commands;

import java.awt.event.ActionEvent;

public class RightJetCommand extends LanderGameAbstractAction
{
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public RightJetCommand()
	{
		super("Right Jet");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		gameModel.fireRightJet();
		gameModel.notifyObservers();
		scoreboard.notifyObservers();
	}
}
