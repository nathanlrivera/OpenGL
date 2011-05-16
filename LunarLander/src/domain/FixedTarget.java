package domain;

import java.awt.Color;


/**
 * Represents a fixed object in the game, such as a Landing Pad.
 *
 */
public abstract class FixedTarget extends Target
{
	/**
	 * @param color The color of the Target
	 * @param position The initial position of the Target
	 * @param points The point value of the Target
	 */
	public FixedTarget(Color color, Point position, int points)
	{
		super(color, position, points);
	}
	
	/*
	 * Do nothing because fixed targets cannot be moved
	 */
	public void setPosition(Point p)
	{
	}
}
