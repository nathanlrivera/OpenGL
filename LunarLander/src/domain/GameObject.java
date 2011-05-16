package domain;
import java.awt.Color;
import java.math.BigDecimal;

/**
 * Represents an object in the game world.
 *
 */
public abstract class GameObject implements IDrawable, ICollider
{
	/**
	 * The position of the GameObject, represented as an (x,y) Cartesian coordinate in the first quadrant.
	 */
	protected Point myPosition;
	/**
	 * The color of the GameObject.
	 */
	protected Color myColor;
	
	/**
	 * @param color The color of the GameObject
	 * @param position The initial position of the GameObject
	 */
	public GameObject(Color color, Point position)
	{
		myColor = color;
		myPosition = new Point(position);
	}
	
	
	/**
	 * @return The color of the GameObject
	 */
	public java.awt.Color getColor()
	{
		return myColor;
	}
	
	/**
	 * @return The current position of the GameObject
	 */
	public Point getPosition()
	{
		return new Point(myPosition);
	}
	
	/**
	 * @param p The position to place the GameObject
	 */
	public void setPosition(Point p)
	{
		myPosition = new Point(p);
	}
	
	public boolean collidesWith(ICollider obj)
	{
		double myXMax = getX().doubleValue() + getBounds().width;
		double myYMax = getY().doubleValue() + getBounds().height;
		double otherXMax = obj.getX().doubleValue() + obj.getBounds().width;
		double otherYMax = obj.getY().doubleValue() + obj.getBounds().height;
		
		return ((getX().doubleValue() <= otherXMax) && (myXMax >= obj.getX().doubleValue())	&& (getY().doubleValue() <= otherYMax) && (myYMax >= obj.getY().doubleValue()));
	}
	
	public BigDecimal getX()
	{
		return myPosition.getX();
	}
	
	public BigDecimal getY()
	{
		return myPosition.getY();
	}
	
	public boolean contains (int x, int y)
	{
		return getBounds().contains(x - myPosition.getX().intValue(), y - myPosition.getY().intValue());
	}
}
