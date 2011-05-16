package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.GameObject;

/**
 * Implements a View of the map in the game.
 *
 * Represents the View part of the Model View Controller architecture.
 */
public class MapView extends JPanel implements Observer, MouseListener, MouseMotionListener
{
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private GameWorld gameData;
	private boolean selecting;
	private java.awt.Point startPoint;
	private java.awt.Point currentPoint;

	private AffineTransform currentAT;

	private Point startScreenPoint;

	private Point currentScreenPoint;
	
	public MapView()
	{
		selecting = false;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg)
	{
		gameData = (GameWorld)o;
		repaint();
	}
	
	private AffineTransform getVTM()
	{
		AffineTransform VTM = new AffineTransform();
		VTM.scale(1, -1);
		VTM.translate(0, -getHeight());
		return VTM;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (gameData == null) return;
		
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform previousTransform = g2d.getTransform();
		
		g2d.transform(getVTM());
		
		double visibleWorldY = gameData.getLanderPosition().getY().doubleValue() * 1.33;
		double zoomFactor = (double)getHeight() / visibleWorldY;

		if (zoomFactor < 1)
		{
			g2d.translate(-(gameData.getLanderPosition().getX().intValue() * zoomFactor - (getWidth() / 2)), 0);
			g2d.scale(zoomFactor, zoomFactor);
		}
		else
		{
			g2d.translate(-(gameData.getLanderPosition().getX().intValue() - (getWidth() / 2)), 0);
		}
		
		for (GameObject gameObj: gameData.getGameObjects())
		{
			gameObj.draw(g2d);
		}
		
		currentAT = g2d.getTransform();
		g2d.setTransform(previousTransform);
		
		if (selecting)
		{
			Color save = g.getColor();
			g.setColor(Color.yellow);
			g.drawRect(startScreenPoint.x, startScreenPoint.y, currentScreenPoint.x - startScreenPoint.x, currentScreenPoint.y - startScreenPoint.y);
			g.setColor(save);
		}
	}
	
	public void mouseClicked(MouseEvent arg0)
	{
		if (SwingUtilities.isLeftMouseButton(arg0))
		{
			try {
				Point worldPoint = getWorldPoint(arg0.getPoint());
				gameData.selectAt(worldPoint);
				repaint();
			} catch (NoninvertibleTransformException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Point getWorldPoint(Point point) throws NoninvertibleTransformException {
		AffineTransform invVTM;
		invVTM = currentAT.createInverse();
		Point2D temp = invVTM.transform(point, null);
		return new java.awt.Point((int)temp.getX(), (int)temp.getY());
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0)
	{
		if (SwingUtilities.isRightMouseButton(arg0))
		{
			selecting = false;
			startScreenPoint = arg0.getPoint();
			try {
				startPoint = getWorldPoint(arg0.getPoint());
			} catch (NoninvertibleTransformException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void mouseReleased(MouseEvent arg0)
	{
		if (SwingUtilities.isRightMouseButton(arg0))
		{
			if (selecting)
			{
				gameData.unselectAll();
				gameData.selectAt(new Rectangle(startPoint.x, currentPoint.y, currentPoint.x - startPoint.x, startPoint.y - currentPoint.y));
				selecting = false;
				repaint();
			}
		}
	}

	public void mouseDragged(MouseEvent arg0)
	{
		if (SwingUtilities.isRightMouseButton(arg0))
		{
			selecting = true;
			currentScreenPoint = arg0.getPoint();
			try {
				currentPoint = getWorldPoint(arg0.getPoint());
			} catch (NoninvertibleTransformException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}

	public void mouseMoved(MouseEvent arg0)
	{
	}
}
