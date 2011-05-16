package commands;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

public class AboutCommand extends LanderGameAbstractAction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AboutCommand()
	{
		super("About");
	}

	public void actionPerformed(ActionEvent e)
	{
		String commandList = "";
		commandList += "Author: Nathan Rivera\n\n";
		commandList += "Class: CSC 133\n\n";
		commandList += "Section: 1";
		JOptionPane.showMessageDialog(null, commandList);
	}

}
