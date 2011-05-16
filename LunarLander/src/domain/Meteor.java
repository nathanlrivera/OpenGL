package domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


/**
 * Represents a circular Meteor.
 *
 */
public class Meteor extends MovingTarget implements ISelectable
{
	/**
	 * The diameter of the Meteor
	 */
	private int myDiameter;
		
	/**
	 * @param color The color of the Meteor
	 * @param position The intial position of the Meteor
	 * @param points The point value of the Meteor
	 * @param diameter The diameter of the Meteor
	 * @param path The trajectory of the Meteor
	 */
	public Meteor(Color color, Point position, int points, int diameter, Point path)
	{
		super(color, position, points, path);
		myDiameter = diameter;
	}

	public void handleCollision(ICollider otherObject)
	{
		
	}

	/**
	 * @return The diameter of the Meteor
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
		return String.format("Meteor: pos=%1$s color=%2$s ptVal=%3$d diameter=%4$d speed=%5$f direction=%6$s", getPosition(), myColor, pointValue, myDiameter, getSpeed(), myPath);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(getColor());
		if(selected)
		{
			Color save = g.getColor();
			g.setColor(Color.yellow);
			g.fillOval(getPosition().getX().intValue(), getPosition().getY().intValue(), getDiameter() + 5, getDiameter() + 5);
			g.setColor(save);
			g.fillOval(getPosition().getX().intValue() + 5, getPosition().getY().intValue() + 5, getDiameter() - 5, getDiameter() - 5);
		}
		else
		{
			g.fillOval(getPosition().getX().intValue(), getPosition().getY().intValue(), getDiameter(), getDiameter());
		}
	}

	public Rectangle getBounds()
	{
		return new Rectangle(getDiameter(), getDiameter());
	}

	public void setSelected (boolean newVal)
	{
		selected = newVal;
	}
	
	protected boolean selected = false;
	public boolean isSelected()
	{
		return selected;
	}
}
