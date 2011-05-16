package game;


import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import commands.NewGameCommand;
import commands.StartGameCommand;

import domain.Bomb;
import domain.GameObject;
import domain.IMoveable;
import domain.Lander;
import domain.LandingPad;
import domain.Meteor;
import domain.Mountain;
import domain.Point;
import domain.SpaceStation;


/**
 * Data and behavior for the Lander Game.
 * 
 * Represents the Model part of the Model View Controller architecture.
 */
public class GameWorld extends Observable implements ActionListener
{
	private static final int _landArea = 50000;

	/**
	 * The collection of all the GameObjects in the game.
	 */
	private LanderGameCollection<GameObject> gameObjects;

	/**
	 * The current score and other game statistics.
	 */
	private Scoreboard theScore;

	/**
	 * The Lander that the Player controls.
	 */
	private Lander theLander;
	/**
	 * The randomizer for generating random numbers.
	 */
	private LanderGameRandom rnd;
	/**
	 * The Space Station that the Lander can dock with for safety.
	 */
	private SpaceStation theSpaceStation;
	/**
	 * The collection of Mountains.
	 */
	private LanderGameCollection<Mountain> mountains;
	
	private Timer timer;
	private Timer scoreTimer;
	private long lastUpdate;
	
	/**
	 * Creates an instance of GameWorld.
	 */
	public GameWorld()
	{
		gameObjects = new LanderGameCollection<GameObject>();
		mountains = new LanderGameCollection<Mountain>();
		rnd = LanderGameRandom.getLanderGameRandom();
		timer = new Timer(1000 / 60, this);
		super.setChanged();
	}
	
	/**
	 * Create all the world objects.
	 * 
	 * @param sb A Scorebaord used to track the score during the game
	 * @param dimension 
	 */
	public void initializeWorld(Scoreboard sb)
	{
		gameObjects.clear();
		createLander();
		createMeteors();
		createGroundObjects();
		createSpaceStation();
		theScore = sb;
		scoreTimer = new Timer(1000, sb);
	}
	
	public void startAnimation()
	{
		lastUpdate = System.currentTimeMillis();
		timer.start();
		scoreTimer.start();
	}
	public void stopAnimation()
	{
		timer.stop();
		scoreTimer.stop();
	}

	public void actionPerformed(ActionEvent e)
	{
		int timeTaken = (int) (System.currentTimeMillis() - lastUpdate);
		tick(timeTaken);
		lastUpdate = System.currentTimeMillis();
		notifyObservers();
	}
	
	public LanderGameCollection<GameObject> getGameObjects()
	{
		return gameObjects;
	}
	
	/**
	 * Destroy the Lander, and creates a new one. If the are no more lives left, ends the game.
	 * @throws EndOfGameException 
	 */
	private void destroyLander() throws EndOfGameException {
		theScore.loseLife();

		if (theScore.getLivesRemaining() > 0)
		{
			gameObjects.remove(theLander);
			createLander();
		}
		else
		{
			endGame();
		}
	}
	
	private void createGroundObjects()
	{
		int xPos = 0;
		int width, left, right;
		Mountain m;
		LandingPad l;
		width = rnd.nextInt(2000) + 1000;
		left = getMountainSide();
		right = getMountainSide();
		
		while (xPos <= _landArea)
		{
			m = new Mountain(Color.lightGray, new Point(xPos, 0), -40, width, left, right);
			gameObjects.add(m);
			mountains.add(m);
			xPos += width;
			width = rnd.nextInt(2000) + 1000;
			left = right;
			right = getMountainSide();
			m = new Mountain(Color.lightGray, new Point(xPos, 0), -40, width, left, right);
			gameObjects.add(m);
			mountains.add(m);
			xPos += width;
			width = rnd.nextInt(100) + 150;
			l = new LandingPad(Color.blue, new Point(xPos, 0), 50, right, width, 5);
			trackLandingPad(l);
			xPos += width;
			width = rnd.nextInt(2000) + 1000;
			left = right;
			right = getMountainSide();
		}
	}

	private int getMountainSide() {
		return rnd.nextInt(400) + 100;
	}

	private int createLandingPad(int xPos, int height, int width)
	{
		LandingPad l = new LandingPad(Color.blue, new Point(xPos, 0), 100, height, width, 5);
		trackLandingPad(l);
		return width;
	}

	private void trackLandingPad(LandingPad l)
	{
		gameObjects.add(l);
		gameObjects.add(l.getBomb());
	}

	/**
	 * Create the initial Meteors.
	 */
	private void createMeteors()
	{
		for(int i = 0; i < _landArea / 250; ++i)
		{
			createMeteor();
		}
	}

	/**
	 * Create one Meteor.
	 */
	private void createMeteor()
	{
		int randomDiameter = rnd.nextInt(100) + 30;
		Point randomPath = new Point(rnd.nextInt(4) - 2, rnd.nextInt(4) - 2);
		
		Meteor m = new Meteor(Color.red, new Point(getRandomMidPoint(), 500 + rnd.nextInt(5000)), -50, randomDiameter, randomPath);
		
		gameObjects.add(m);
		super.setChanged();
	}

	/**
	 * Create a Lander.
	 */
	private void createLander()
	{
		theLander = new Lander(Color.green, getMidairPoint(), this);
		gameObjects.add(theLander);
	}
	
	private int getRandomMidPoint()
	{
		return rnd.nextInt(_landArea);
	}
	
	private Point getMidairPoint()
	{
		return new Point(getRandomMidPoint(), 1500 + rnd.nextInt(2000));
	}
	
	/**
	 * Create a Space Station.
	 */
	private void createSpaceStation() {
		theSpaceStation = new SpaceStation(Color.pink, getMidairPoint(), 75, 200);
		gameObjects.add(theSpaceStation);
	}
	
	/**
	 * Fire the right jet on the Lander.
	 * 
	 * @return A status message
	 */
	public String fireRightJet()
	{
		if (theLander.fireRightJet())
		{
			super.setChanged();
			return "Firing starboard (right) jet.";
		}
		else
			return "Lander is out of fuel!";
	}
	
	/**
	 * Fire the left jet on the Lander.
	 * 
	 * @return A status message
	 */
	public String fireLeftJet()
	{
		if (theLander.fireLeftJet())
		{
			super.setChanged();
			return "Firing port (left) jet.";
		}
		else
			return "Lander is out of fuel!";
	}
	
	/**
	 * Fire the bottom jet on the Lander.
	 * 
	 * @return A status message
	 */
	public String fireBottomJet()
	{
		if (theLander.fireBottomJet())
		{
			super.setChanged();
			return "Firing hull (down) jet.";
		}
		else
			return "Lander is out of fuel!";
	}
	
	/**
	 * Hit a meteor! If there are no meteors left, do nothing.
	 * @param struckMeteor 
	 * 
	 * @return A status message
	 * @throws EndOfGameException 
	 */
	public String hitMeteor(Meteor struckMeteor) throws EndOfGameException
	{
		if (gameObjects.meteors().size() > 0)
		{
			removeMeteor(struckMeteor);
			//Hitting a meteor destroys the Lander and the Meteor
			theLander.setPosition(struckMeteor.getPosition());
			theScore.earnPoints(struckMeteor.getPointValue());
			String message = String.format("You hit a meteor! You lose %1$d points.", struckMeteor.getPointValue() * -1);
			try
			{
				destroyLander();
			}
			catch (EndOfGameException e)
			{
				throw new EndOfGameException(message, e);
			}
			return message;
		}
		else
			return "No meteors left!";
	}

	private void removeMeteor(Meteor struckMeteor)
	{
		gameObjects.remove(struckMeteor);
	}
	
	/**
	 * Arrive at a LandingPad.
	 * @param landedPad 
	 * 
	 * @return A status message
	 * @throws EndOfGameException 
	 */
	public String arrive(LandingPad landedPad) throws EndOfGameException
	{
		if (theLander.isStopped()) return "";
		super.setChanged();
		
		//If the Lander didn't crash into the pad, reward the player and refuel
		if (landedPad.landedSuccessfully(theLander))
		{
			theLander.stop();
			landedPad.fuelLander(theLander);
			theScore.earnPoints(landedPad.getPointValue());
			return String.format("You landed! You earn %1$d points.", landedPad.getPointValue());
		}
		else
		{
			//If the Lander crashed into the pad, punish the Player and recreate the pad & Lander.
			final int pointsLost = -1 * landedPad.getPointValue();
			theScore.earnPoints(pointsLost);
			gameObjects.remove(landedPad.getBomb());
			gameObjects.remove(landedPad);
			createLandingPad(landedPad.getPosition().getX().intValue(), landedPad.getHeight(), landedPad.getWidth());
			String message = String.format("You crashed into the landing pad! You lose %1$d points.", landedPad.getPointValue());
			try
			{
				destroyLander();
			}
			catch (EndOfGameException e)
			{
				throw new EndOfGameException(message, e);
			}
			return message;
		}
	}
	
	/**
	 * Add a meteor to the game world.
	 * 
	 * @return A status message
	 */
	public String addMeteor()
	{
		createMeteor();
		super.setChanged();
		return "Adding a new meteor.";
	}
	
	/**
	 * Runs the Lander into the side of a Mountain.
	 * @return A status message
	 * @throws EndOfGameException
	 */
	public String hitMountain() throws EndOfGameException
	{
		Mountain mtn = (Mountain) mountains.toArray()[0];
		theScore.earnPoints(mtn.getPointValue());
		super.setChanged();
		String message = String.format("You hit a mountain! You lose %1$d points.", mtn.getPointValue() * -1);
		try
		{
			destroyLander();
		}
		catch (EndOfGameException e)
		{
			throw new EndOfGameException(message, e);
		}
		return message;
	}
	
	/**
	 * Blows up the Lander with a Bomb from one of the LandingPads.
	 * @return A status message
	 * @throws EndOfGameException
	 */
	public String explode() throws EndOfGameException
	{
		super.setChanged();
		LandingPad bombPad = (LandingPad) gameObjects.landingPads().toArray()[0];
		return bombCollision(bombPad);
	}

	/**
	 * Handles the collision between the Lander and a Bomb.
	 * 
	 * @param bombPad The LandingPad that the Lander was sitting on
	 * @return A status message
	 * @throws EndOfGameException
	 */
	private String bombCollision(LandingPad bombPad) throws EndOfGameException
	{
		Bomb bomb = bombPad.getBomb();
		theScore.earnPoints(bomb.getPointValue());
		destroyBomb(bombPad, bomb);
		String message = String.format("A bomb destroyed the Lander! You lose %1$d points.", bombPad.getBomb().getPointValue() * -1);
		try
		{
			destroyLander();
		}
		catch (EndOfGameException e)
		{
			throw new EndOfGameException(message, e);
		}
		return message;
	}
	
	/**
	 * Docks the Lander with the Space Station.
	 * 
	 * @return A status message
	 */
	public String dock()
	{
		super.setChanged();
		theLander.dock(theSpaceStation.getPosition());
		return "Docked with Space Station.";
	}
	
	/**
	 * Advance the game clock and call move() on all the moving objects in the game world.
	 * @param timeTaken 
	 * 
	 * @return A status message
	 * @throws EndOfGameException 
	 */
	public void tick(int timeTaken)
	{
		for (IMoveable mo: gameObjects.movingObjects())
		{
			mo.move(timeTaken);
		}
		try
		{
			checkCollisions();
		}
		catch (EndOfGameException e)
		{
			stopAnimation();
			startCommand.setEnabled(false);
			newGameCommand.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Game over! Press 'New Game' to start a new game.");
		}
		for (LandingPad l: gameObjects.landingPads())
		{
			Bomb bomb = l.getBomb();
			if(bomb.getPosition().getY().intValue() == l.getHeight())
			{
				destroyBomb(l, bomb);
			}
		}
		
		super.setChanged();
	}

	/**
	 * Destroys a bomb.
	 * 
	 * @param l The LandingPad containing the Bomb
	 * @param bomb The Bomb to destroy
	 */
	private void destroyBomb(LandingPad l, Bomb bomb)
	{
		gameObjects.remove(bomb);
		l.explodeBomb();
		Bomb newBomb = l.getBomb();
		gameObjects.add(newBomb);
	}
	
	/**
	 * End the game.
	 * @throws EndOfGameException 
	 */
	private void endGame() throws EndOfGameException
	{
		throw new EndOfGameException("Game over!");
	}
	
	private void checkCollisions() throws EndOfGameException 
	{
		for (GameObject mo: gameObjects)
		{
			if (mo != theLander && mo.collidesWith(theLander))
			{
				theLander.handleCollision(mo);
			}
		}
	}

	public void selectAt(java.awt.Point point)
	{
		for (Meteor mo: gameObjects.meteors())
		{
			if (mo.contains(point.x, point.y))
				mo.setSelected(true);
			else
				mo.setSelected(false);
		}
	}

	public void unselectAll()
	{
		for (Meteor go : gameObjects.meteors())
		{
			go.setSelected(false);
		}
		
	}

	public void selectAt(Rectangle rectangle)
	{
		for (Meteor mo: gameObjects.meteors())
		{
			if (rectangle.intersects(new Rectangle(mo.getPosition().getX().intValue(), mo.getPosition().getY().intValue(), mo.getBounds().width, mo.getBounds().height)))
				mo.setSelected(true);
			else
				mo.setSelected(false);
		}
	}

	public void deleteSelectedObjects()
	{
		for (Meteor m: gameObjects.meteors())
		{
			if (m.isSelected())
			{
				removeMeteor(m);
				super.setChanged();
				notifyObservers();
			}
		}
	}
	
	public Point getLanderPosition()
	{
		return new Point(theLander.getPosition());
	}

	private StartGameCommand startCommand;
	public void setStartCommand(StartGameCommand startGame)
	{
		startCommand = startGame;
	}

	private NewGameCommand newGameCommand;
	public void setNewGameCommand(NewGameCommand newGame)
	{
		newGameCommand = newGame;
	}
}
