package domain;

import java.awt.Color;

public abstract class MovingTarget extends Target implements IMoveable
{
	protected Point myPath;
	
	public MovingTarget(Color color, Point position, int points, Point path)
	{
		super(color, position, points);
		myPath = path;
	}

	public void move(int timeTaken)
	{
		//Displace the MovingTarget by the amount of the trajectory.
		Point currPosition = getPosition();
		currPosition.add(myPath);
		setPosition(currPosition);
	}
	
	/**
	 * @return The current speed of the object
	 */
	public double getSpeed()
	{
		//The velocity is just the length of the hypotenuse of the triangle formed by the X and Y magnitudes
		return Math.sqrt(myPath.getX().pow(2).add(myPath.getY().pow(2)).doubleValue());
	}
}
