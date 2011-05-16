package domain;

import java.awt.Color;
import java.math.BigDecimal;

/**
 * Represents a moving object.
 *
 */
public abstract class MovingObject extends GameObject implements IMoveable
{
	protected Point myPath;
	private static final double gravity = -1.622; // Gravity on the Moon is 1.622 N/m
	
	public MovingObject(Color color, Point position, Point path)
	{
		super(color, position);
		myPath = path;
	}
	
	/**
	 * @return The current speed of the object
	 */
	public double getSpeed()
	{
		//The velocity is just the length of the hypotenuse of the triangle formed by the X and Y magnitudes
		return Math.sqrt(myPath.getX().pow(2).add(myPath.getY().pow(2)).doubleValue());
	}
	
	protected final void moveWithGravity(int time)
	{
		BigDecimal timeTaken = new BigDecimal(time).divide(new BigDecimal(1000));
		BigDecimal ySpeed = myPath.getY();
		ySpeed = ySpeed.add(new BigDecimal(gravity).multiply(timeTaken));
		myPath = new Point(myPath.getX(), ySpeed);
		BigDecimal yPos = myPath.getY();
		yPos = yPos.add(ySpeed.multiply(timeTaken));
		yPos = yPos.add(new BigDecimal(gravity / 2.0).multiply(timeTaken).multiply(timeTaken));
		myPosition.add(new Point(myPath.getX(), yPos));
	}
}
