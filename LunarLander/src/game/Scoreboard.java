package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * Keeps track of the lives remaining, points earned, and time elapsed.
 *
 */
public class Scoreboard extends Observable implements ActionListener
{
	/**
	 * The amound of Landers that the Player has left.
	 */
	private int livesRemaining;
	/**
	 * The amount of points that the Player has earned.
	 */
	private int pointsEarned;
	/**
	 * The amount of ticks that have passed since the game started.
	 */
	private int timeElapsed;
	
	/**
	 * The default Scoreboard has 3 Landers left, zero points, and initial time zero.
	 */
	public Scoreboard()
	{
		livesRemaining = 3;
		pointsEarned = 0;
		timeElapsed = 0;
		super.setChanged();
	}

	/**
	 * Signal that a Lander was destroyed.
	 */
	public void loseLife() {
		setLivesRemaining(getLivesRemaining() - 1);
	}

	/**
	 * @return The amount of Landers left in the game
	 */
	public int getLivesRemaining() {
		return livesRemaining;
	}
	
	/**
	 * @param The new value for lives remaining
	 */
	private void setLivesRemaining(int value)
	{
		livesRemaining = value;
		super.setChanged();
	}

	/**
	 * @param pointsEarned The amount of points to add to the score
	 */
	public void earnPoints(int points) {
		setPointsEarned(getPointsEarned() + points);
	}

	/**
	 * @return The current score
	 */
	public int getPointsEarned() {
		return pointsEarned;
	}
	
	/**
	 * @param The new value for points earned
	 */
	private void setPointsEarned(int value)
	{
		pointsEarned = value;
		super.setChanged();
	}

	/**
	 * Signal that a turn has passed.
	 */
	public void addTurn()
	{
		setTimeElapsed(getTimeElapsed() + 1);
	}
	
	/**
	 * @param The new value for amount of turns passed
	 */
	private void setTimeElapsed(int value)
	{
		timeElapsed = value;
		super.setChanged();
	}

	/**
	 * @return The amount of ticks elapsed in the game
	 */
	public int getTimeElapsed() 
	{
		return timeElapsed;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("Score: livesRemaining=%1$d pointsEarned=%2$d timeElapsed=%3$d", livesRemaining, pointsEarned, timeElapsed);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		addTurn();
		notifyObservers();
	}
}
