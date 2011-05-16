package game;

import javax.swing.*;
import commands.*;

/**
 * Controls input from the Player and calls the appropriate commands on the Game Model.
 *
 * Represents the Controller part of the Model View Controller architecture.
 */
public class GameController extends JPanel
{
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private NewGameCommand ng;
	private AddMeteorCommand addMeteor;
	private DeleteCommand delete; 

	public GameController(GameWorld gameModel, Scoreboard scoreboard, HelpCommand help, QuitCommand quit, NewGameCommand newGame)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		buildButton(newGame, gameModel, scoreboard);
		addMeteor = new AddMeteorCommand();
		buildButton(addMeteor, gameModel, scoreboard);
		delete = new DeleteCommand();
		buildButton(delete, gameModel, scoreboard);
		JButton button = new JButton();
		StartGameCommand startGame = new StartGameCommand(button, this);
		startGame.setGameWorld(gameModel);
		startGame.setScoreboard(scoreboard);
		button.setAction(startGame);
		add(button);
		gameModel.setStartCommand(startGame);
		buildButton(help, gameModel, scoreboard);
		buildButton(quit, gameModel, scoreboard);
		ng = newGame;
		gameModel.setNewGameCommand(newGame);
	}
	
	/**
	 * Builds a JButton and adds it to this JPanel.
	 * 
	 * @param action The LanderGameAbstractAction to bind to the button
	 * @param gameModel The GameWorld that the command will operate on
	 */
	private void buildButton(LanderGameAbstractAction action, GameWorld gameModel, Scoreboard scoreboard)
	{
		action.setGameWorld(gameModel);
		action.setScoreboard(scoreboard);
		JButton button = new JButton();
		button.setAction(action);
		add(button);
	}
	
	public void toggleEditButtons()
	{
		ng.setEnabled(!ng.isEnabled());
		addMeteor.setEnabled(!addMeteor.isEnabled());
		delete.setEnabled(!delete.isEnabled());
	}
}
