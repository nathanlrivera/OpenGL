package domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


/**
 * The Space Station is a fixed target that the Lander can dock with.
 * It protects the Lander from Meteors.
 */
public class SpaceStation extends FixedTarget
{
	/**
	 * The diameter of the SpaceStation
	 */
	private int myDiameter;
	
	/**
	 * @param color The color of the Space Station
	 * @param position The (fixed) position of the Space Station
	 * @param points The point value for the Space Station
	 */
	public SpaceStation(Color color, Point position, int points, int diameter)
	{
		super(color, position, points);
		myDiameter = diameter;
	}
	
	/**
	 * @return The diameter of the SpaceStation
	 */
	public int getDiameter()
	{
		return myDiameter;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("SpaceStation: pos=%1$s color=%2$s ptVal=%3$d diameter=%4$d", getPosition(), myColor, pointValue, myDiameter);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(getColor());
		g.drawOval(getPosition().getX().intValue(), getPosition().getY().intValue(), getDiameter(), getDiameter());
	}

	public Rectangle getBounds()
	{
		return new Rectangle(myDiameter, myDiameter);
	}

	public void handleCollision(ICollider otherObject)
	{
		// TODO Auto-generated method stub
		
	}
}
