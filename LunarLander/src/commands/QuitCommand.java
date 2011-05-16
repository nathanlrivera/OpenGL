package commands;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

public class QuitCommand extends LanderGameAbstractAction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuitCommand()
	{
		super("Quit");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		int result = JOptionPane.showConfirmDialog(null, "Abort mission?", "Confirm", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION)
			quit();
	}
}
