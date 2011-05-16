package domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


/**
 * A Bomb is a moving target which moves from the bottom of a landing pad toward the top 
 * each time the clock ticks. When a bomb reaches the top of the landing pad, it explodes and will 
 * destroy any lander that rests on the landing pad at that time.  Each time a bomb explodes a 
 * new bomb appears at the bottom of the landing pad and begins moving upward again (thus, 
 * every landing pad always has exactly one bomb somewhere on it). Bombs only move 
 * vertically, and do not harm the landing pads – only a lander currently on the landing pad. 
 *
 */
public class Bomb extends MovingTarget
{
	/**
	 * @param color The color of the Bomb
	 * @param position The initial position of the Bomb
	 * @param points The point value of the Bomb
	 */
	public Bomb(Color color, Point position, int points, Point path)
	{
		super(color, position, points, path);
	}
	
	/* (non-Javadoc)
	 * @see game.MovingObject#getSpeed()
	 */
	public double getSpeed()
	{
		return 1;
	}

	/* (non-Javadoc)
	 * @see game.MovingObject#move()
	 */
	public void move(int timeTaken)
	{
		//Move the Bomb up one unit.
		Point currPosition = getPosition();
		currPosition.add(new Point(0, 1));
		setPosition(currPosition);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("Bomb: pos=%1$s color=%2$s ptVal=%3$d", getPosition(), myColor, pointValue);
	}

	public void draw(Graphics g)
	{
		g.setColor(getColor());
		g.drawOval(getPosition().getX().intValue(), getPosition().getY().intValue(), 20, 20);
	}

	public Rectangle getBounds()
	{
		return new Rectangle(20, 20);
	}

	public void handleCollision(ICollider otherObject)
	{
		// TODO Auto-generated method stub
		
	}
}
