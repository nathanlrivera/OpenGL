package commands;

import game.GameController;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class StartGameCommand extends LanderGameAbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton myButton;
	private GameController controller;

	public StartGameCommand(JButton button, GameController gc)
	{
		super("Play");
		myButton = button;
		controller = gc;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		controller.toggleEditButtons();
		if (myButton.getText() == "Play")
		{
			gameModel.startAnimation();
			myButton.setText("Stop");
		}
		else
		{
			gameModel.stopAnimation();
			myButton.setText("Play");
		}
	}
}
