package domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.math.BigDecimal;


/**
 * Represents a Landing Pad that the Player can land the Lander on.
 *
 */
public class LandingPad extends FixedTarget
{
	/**
	 * The height of the LandingPad.
	 */
	private int myHeight;
	/**
	 * The width of the LandingPad.
	 */
	private int myWidth;
	/**
	 * The strength of the LandingPad.
	 */
	private int myStrength;
	/**
	 * The amount of fuel stored in the LandingPad.
	 */
	private int myStorage;
	/**
	 * The bomb that moves up the LandingPad.
	 */
	private Bomb myBomb;
	
	/**
	 * @param color The color of the LandingPad
	 * @param position The (fixed) position of the LandingPad
	 * @param points The amount of points the LandingPad is worth
	 * @param height The height of the LandingPad
	 * @param width The width of the LandingPad
	 * @param strength The strength of the LandingPad
	 */
	public LandingPad(Color color, Point position, int points, int height, int width, int strength)
	{
		super(color, position, points);
		myHeight = height;
		myWidth = width;
		myStrength = strength;
		myStorage = 30;
		myBomb = new Bomb(Color.orange, new Point(getPosition().getX(), new BigDecimal(0)), -30, new Point(0,0));
	}
	
	/**
	 * Explode the Bomb that travels up the LandingPad.
	 */
	public void explodeBomb()
	{
		myBomb = new Bomb(Color.yellow, new Point(getPosition().getX(), new BigDecimal(0)), -30, new Point(0,0));
	}
	
	/**
	 * @return The Bomb that is on this LandingPad
	 */
	public Bomb getBomb()
	{
		return myBomb;
	}
	
	/**
	 * @return The height of the LandingPad
	 */
	public int getHeight()
	{
		return myHeight;
	}
	
	/**
	 * @return The width of the LandingPad
	 */
	public int getWidth()
	{
		return myWidth;
	}

	/**
	 * Refuels a Lander until the Lander is full, or the stored fuel on the LandingPad runs out.
	 * @param theLander The Lander to refuel
	 */
	public void fuelLander(Lander theLander)
	{
		while(theLander.needsFuel() && myStorage > 0)
		{
			--myStorage;
			theLander.refuel(1);
		}
	}
	
	/**
	 * @param l The Lander that landed on the pad
	 * @return True if the Lander didn't destroy the pad
	 */
	public boolean landedSuccessfully(Lander l)
	{
		return l.getSpeed() <= myStrength;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("LandingPad: pos=%1$s color=%2$s ptVal=%3$d size=%4$dx%5$d strength=%6$d storage=%7$d", getPosition(), myColor, pointValue, myHeight, myWidth, myStrength, myStorage);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(getColor());
		g.drawRect(getPosition().getX().intValue(), getPosition().getY().intValue(), getWidth(), getHeight());
	}

	public Rectangle getBounds()
	{
		return new Rectangle(myWidth, myHeight);
	}

	public void handleCollision(ICollider otherObject)
	{
		
	}
}
