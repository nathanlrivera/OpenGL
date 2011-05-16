package domain;
import java.awt.*;


/**
 * Base class for all Targets in the game world.
 *
 */
public abstract class Target extends GameObject
{
	/**
	 * How much the Target is worth.
	 */
	protected int pointValue;
	
	/**
	 * @param color The color of the Target
	 * @param position The initial position of the Target
	 * @param points The point value of the Target
	 */
	public Target(Color color, Point position, int points)
	{
		super(color, position);
		pointValue = points;
	}

	/**
	 * @return The value of the target if the Lander interacts with it
	 */
	public int getPointValue()
	{
		return pointValue;
	}
	
}
