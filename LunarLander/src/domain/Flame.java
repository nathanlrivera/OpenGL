package domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Flame
{
	private Color color;
	private AffineTransform myAT;
	private int myHeight, myWidth;
	private Point myPosition;
	
	public Flame(Point position, int width, int height)
	{
		myHeight = height;
		myWidth = width;
		color = Color.orange;
		myAT = new AffineTransform();
		myPosition = position;
	}
	
	public void scale(double tx, double ty)
	{
		myAT.scale(tx, ty);
	}
	
	public void translate(double tx, double ty)
	{
		myAT.translate(tx, ty);
	}
	
	public void resetTransform()
	{
		myAT.setToIdentity();
	}
	
	public void draw(Graphics2D g2d)
	{
		g2d.setColor(color);
		AffineTransform old = (AffineTransform) g2d.getTransform().clone();
		g2d.transform(myAT);
		g2d.fillOval(myPosition.getX().intValue(), myPosition.getY().intValue(), myWidth, myHeight);
		g2d.setTransform(old);
	}
}
