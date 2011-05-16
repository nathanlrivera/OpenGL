package domain;

import game.EndOfGameException;
import game.GameWorld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.math.BigDecimal;

/**
 * Represents a Lander that the Player maneuvers.
 *
 */
public class Lander extends GameObject implements IMoveable
{
	protected Point myPath;
	private static final double gravity = -1.622; // Gravity on the Moon is 1.622 N/m
	
	/**
	 * The maximum amount of fuel the Lander can hold.
	 */
	private static final int fuelCapacity = 1000;
	/**
	 * The current amount of Fuel in the Lander.
	 */
	private int fuelLevel;
	/**
	 * Indicates whether the Lander is docked in the Space Station.
	 */
	private boolean docked;
	/**
	 * Indicates whether the Lander is stopped.
	 */
	private boolean stopped;
	private GameWorld gameWorld;
	
	private Point top, bottomLeft, bottomRight;
	private AffineTransform myAT;
	private Flame myFlame;
	private int flameOffset;
	private int flameincrement;
	
	/**
	 * @param color The color of the Lander
	 * @param position The initial position of the Lander
	 */
	public Lander(Color color, Point position, GameWorld gw)
	{
		super(color, position);
		fuelLevel = fuelCapacity; //The Lander starts out with a full fuel tank.
		docked = false; //The Lander starts out in the middle of space.
		stopped = false; //The Lander starts out in free fall.
		gameWorld = gw;
		myPath = new Point(0,0);
		int base = 65, height = 100;
		top = new Point(0, height / 2);
		bottomLeft = new Point(-base / 2.0, -height / 2.0);
		bottomRight = new Point(base / 2.0, -height / 2.0);
		
		color = Color.green;
		myAT = new AffineTransform();
		myFlame = new Flame(new Point(-base / 6.0, -height * 1.33), base / 3, height / 2);
		flameincrement = 1;
		flameOffset = 0;
	}
	
	/**
	 * Fire the left jet, expending one unit of fuel and accelerating the Lander one unit to the right.
	 */
	public boolean fireLeftJet()
	{
		return fireJet(new Point(0.3, 0));
	}
	
	/**
	 * Fire the right jet, expending one unit of fuel and accelerating the Lander one unit to the left.
	 */
	public boolean fireRightJet()
	{
		return fireJet(new Point(-0.3, 0));
	}
	
	/**
	 * Fire the down jet, expending one unit of fuel and accelerating the Lander one unit upward.
	 */
	public boolean fireBottomJet()
	{
		return fireJet(new Point(0, 0.3));
	}
	
	/**
	 * Fires a jet on the Lander, expending one unit of fuel and moving the Lander in the specified direction.
	 * Undocks the Lander if it is currently docked.
	 * 
	 * @param direction Direction to fire the jet
	 * @return false if the Lander ran out of fuel and cannot fire the jet
	 */
	private boolean fireJet(Point direction)
	{
		if (fuelLevel > 0)
		{
			docked = false;
			stopped = false;
			--fuelLevel;
			myPath.add(direction);
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see game.MovingObject#move()
	 */
	public void move(int timeTaken)
	{
		if (stopped) return;
		
		moveWithGravity(timeTaken);
	}
	
	/**
	 * @return True if the Lander does not have the maximum amount of fuel.
	 */
	public boolean needsFuel()
	{
		return fuelLevel < fuelCapacity;
	}
	
	/**
	 * @param amount Adds fuel to the Lander.
	 */
	public void refuel(int amount)
	{
		if (amount + fuelLevel > fuelCapacity)
			throw new IllegalArgumentException("Trying to refuel Lander with too much fuel.");
		
		fuelLevel += amount;
	}
	
	/**
	 * Immobilize the Lander.
	 */
	public void stop()
	{
		myPath = new Point(0,0);
		stopped = true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("Lander: pos=%1$s color=%2$s speed=%3$f direction=%4$s fuel=%5$d fuelCap=%6$d", getPosition(), myColor, getSpeed(), myPath, fuelLevel, fuelCapacity);
	}
	
	/**
	 * @return true if the Lander is docked in the Space Station.
	 */
	public boolean isDocked()
	{
		return docked;
	}
	
	/**
	 * @return true if the Lander is not moving.
	 */
	public boolean isStopped()
	{
		return stopped;
	}
	
	/**
	 * Tell the Lander to dock with the Space Station.
	 * 
	 * @param stationPosition The location of the Space Station
	 */
	public void dock(Point stationPosition)
	{
		docked = true;
		setPosition(stationPosition);
		stop();
	}
	
	public void draw(Graphics g)
	{
		resetTransform();
		myAT.translate(getX().doubleValue(), getY().doubleValue());
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.green);
		AffineTransform old = (AffineTransform) g2d.getTransform().clone();
		g2d.transform(myAT);
		g2d.drawLine(top.getX().intValue(), top.getY().intValue(), bottomLeft.getX().intValue(), bottomLeft.getY().intValue());
		g2d.drawLine(bottomLeft.getX().intValue(), bottomLeft.getY().intValue(), bottomRight.getX().intValue(), bottomRight.getY().intValue());
		g2d.drawLine(bottomRight.getX().intValue(), bottomRight.getY().intValue(), top.getX().intValue(), top.getY().intValue());
		if (!stopped)
			myFlame.draw(g2d);
		g2d.setTransform(old);
	}
	
	public void resetTransform()
	{
		myAT.setToIdentity();
		myAT.translate(bottomRight.getX().intValue() / 2, top.getY().intValue());
	}

	public Rectangle getBounds()
	{
		return new Rectangle(20, 40);
	}

	public void handleCollision(ICollider otherObject) throws EndOfGameException
	{
		if (isDocked()) return;
		
		if (otherObject instanceof Mountain)
		{
			gameWorld.hitMountain();
		}
		else if (otherObject instanceof LandingPad)
		{
			gameWorld.arrive((LandingPad)otherObject);
		}
		else if (otherObject instanceof SpaceStation)
		{
			gameWorld.dock();
		}
		else if (otherObject instanceof Meteor)
		{
			gameWorld.hitMeteor((Meteor)otherObject);
		}
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
		flameOffset += flameincrement;
		myFlame.translate(0, flameOffset);
		if (Math.abs(flameOffset) == 5)
		{
			flameincrement *= -1;
		}
	}
}
