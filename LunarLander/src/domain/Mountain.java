package domain;

import java.awt.*;
import java.awt.geom.*;


/**
 * A Mountain is a fixed target which stays on the ground.
 * When the lander runs into a Mountain, the Lander will be destroyed. 
 *
 */
public class Mountain extends FixedTarget
{
	/**
	 * The height of the Mountain.
	 */
	private int leftHeight;
	/**
	 * The width of the Mountain.
	 */
	private int myWidth;
	private int rightHeight;
	
	/**
	 * @param color The color of the Mountain
	 * @param position The position of the Mountain
	 * @param points The point value for the Mountain
	 */
	public Mountain(Color color, Point position, int points, int width, int left, int right)
	{
		super(color, position, points);
		myWidth = width;
		leftHeight = left;
		rightHeight = right;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("Mountain: pos=%1$s color=%2$s ptVal=%3$d", getPosition(), myColor, pointValue);
	}
	
	/**
	 * @return The width of the LandingPad
	 */
	public int getWidth()
	{
		return myWidth;
	}
	
	private Polygon getPolygon()
	{
		int x = getX().intValue();
		int y = getY().intValue();
		int xPoints[] = {x, x + getWidth(), x + getWidth(), x};
		int yPoints[] = {y, y, rightHeight, leftHeight};
		return new Polygon(xPoints, yPoints, 4);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(getColor());

		g.fillPolygon(getPolygon());
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(myWidth, Math.max(rightHeight, leftHeight));
	}

	public void handleCollision(ICollider otherObject)
	{
	}	
}
