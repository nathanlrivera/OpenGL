package domain;

import java.math.BigDecimal;

/**
 * A Point represented as an (X,Y) Cartesian coordinate.
 *
 */
public class Point {

	/**
	 * The horizontal component.
	 */
	private BigDecimal myX;
	/**
	 * The vertical component.
	 */
	private BigDecimal myY;
	
	/**
	 * The default point is at (0,0)
	 */
	public Point()
	{
		myX = new BigDecimal(0);
		myY = new BigDecimal(0);
	}
	
	/**
	 * @param x The initial X position
	 * @param y The initial Y position
	 */
	public Point(double x, double y)
	{
		myX = new BigDecimal(x);
		myY = new BigDecimal(y);
	}
	
	public Point(BigDecimal x, BigDecimal y)
	{
		myX = x;
		myY = y;
	}
	
	/**
	 * @param p The Point object to copy
	 */
	public Point(Point p)
	{
		myX = p.getX();
		myY = p.getY();
	}
	
	/**
	 * @param v The Point to add to this Point.
	 */
	public void add(Point v)
	{
		myX = myX.add(v.getX());
		myY = myY.add(v.getY());
	}

	/**
	 * @return The vertical component
	 */
	public BigDecimal getY() {
		return myY;
	}

	/**
	 * @return The horizontal component
	 */
	public BigDecimal getX() {
		return myX;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return myX + ","  + myY;
	}
}
