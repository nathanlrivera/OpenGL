package commands;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

public class HelpCommand extends LanderGameAbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HelpCommand()
	{
		super("Help");
	}

	public void actionPerformed(ActionEvent e)
	{
		String commandList = "";
		commandList += "Press 'Arrow Down' on your keyboard to fire the down jet on the Lander.\n";
		commandList += "Press 'Arrow Left' on your keyboard to the left jet on the Lander.\n";
		commandList += "Press 'Arrow Right' on your keyboard to right jet on the Lander.\n";
		commandList += "Click with the left mouse button to select an object.\n";
		commandList += "Drag with the right mouse button to select a group of objects.\n";
		commandList += "'New Game' Starts a new game.\n";
		commandList += "'Add Meteor' Adds a new Meteor to the game.\n";
		commandList += "'Delete' Deletes the currently selected objects.\n";
		commandList += "'Play' Begins playing the game.\n";
		commandList += "'Help' Shows this dialog.\n";
		commandList += "'Quit' Quits the game.\n";
		JOptionPane.showMessageDialog(null, commandList);
	}
}
