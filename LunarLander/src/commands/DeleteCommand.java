package commands;

import java.awt.event.ActionEvent;

public class DeleteCommand extends LanderGameAbstractAction
{

	public DeleteCommand()
	{
		super("Delete");
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void actionPerformed(ActionEvent arg0)
	{
		gameModel.deleteSelectedObjects();
	}
}
