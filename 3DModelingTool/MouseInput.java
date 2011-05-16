package a5;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.event.MouseInputAdapter;

/**
 * Handles mouse interaction GLCanvas.
 * 
 * @author Nathan Rivera
 */
public class MouseInput extends MouseInputAdapter
{
	private Camera myCamera;

	Point prevPoint = null;

	/**
	 * @param canvas The GLCanvas that the user will click/drag on.
	 */
	public MouseInput(Camera camera)
	{
		myCamera = camera;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.MouseInputAdapter#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(prevPoint != null)
		{
			Point currentPoint = e.getPoint();
			myCamera.changeYaw(currentPoint.getX() - prevPoint.getX());
			myCamera.changePitch(currentPoint.getY() - prevPoint.getY());
			prevPoint = currentPoint;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.MouseInputAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		prevPoint = e.getPoint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.MouseInputAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		prevPoint = null;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		myCamera.changeZ(e.getWheelRotation() * 5.0);
	}
}