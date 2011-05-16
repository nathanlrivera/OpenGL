package a5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import mathlib3D.Point3D;

/**
 * ActionListener to handle the timer events for SLERP
 */
public class SlerpAction implements ActionListener
{
	private double maxIterations,
			currentIterations;

	private Timer theTimer;

	private Point3D startPoint,
			endPoint;

	private double xDist,
			yDist,
			zDist;

	private Quaternion startQuaternion,
			endQuaternion;

	private Camera myCamera;

	public SlerpAction(Camera camera, Point3D start, Quaternion startQ, Point3D end, Quaternion endQ, double time)
	{
		myCamera = camera;
		maxIterations = time * 60.0;
		currentIterations = 0;
		startPoint = start;
		endPoint = end;
		xDist = (endPoint.getX() - startPoint.getX()) / maxIterations;
		yDist = (endPoint.getY() - startPoint.getY()) / maxIterations;
		zDist = (endPoint.getZ() - startPoint.getZ()) / maxIterations;
		startQuaternion = startQ;
		endQuaternion = endQ;
		startQuaternion.normalize();
		endQuaternion.normalize();
	}

	public void setTimer(Timer timer)
	{
		theTimer = timer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		if(currentIterations < maxIterations)
		{
			myCamera.changeX(xDist);
			myCamera.changeY(yDist);
			myCamera.changeZ(zDist);
			Quaternion slerp = Quaternion.Slerp(startQuaternion, endQuaternion, currentIterations / maxIterations);
			myCamera.setOrientation(slerp);
			++currentIterations;
		}
		else
			theTimer.stop();
	}
}
