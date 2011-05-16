package commands;

import java.awt.event.ActionEvent;

public class DownJetCommand extends LanderGameAbstractAction
{
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public DownJetCommand()
	{
		super("Down Jet");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		gameModel.fireBottomJet();
		gameModel.notifyObservers();
		scoreboard.notifyObservers();
	}
}
