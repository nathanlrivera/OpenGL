package a5;

import mathlib3D.*;

/**
 * Camera class for representing camera location/orientation
 */
public class Camera
{
	private Point3D cameraLocation;

	private Point3D lookAtPoint;

	private Point3D upDirection;

	private boolean relativeMode;

	private Quaternion orientation;

	/**
	 * Constructs the defaul camera location and orientation
	 */
	public Camera()
	{
		cameraLocation = new Point3D(0, 0, 500);
		lookAtPoint = new Point3D(0, 0, 0);
		upDirection = new Point3D(0, 1, 0);
		relativeMode = true;
		orientation = new Quaternion(0, 0, 0, 1);
	}

	/**
	 * @return True if the camera is in relative mode.
	 */
	public boolean getRelativeMode()
	{
		return relativeMode;
	}

	/**
	 * @param value Sets the camera relative mode.
	 */
	public void setRelativeMode(boolean value)
	{
		relativeMode = value;
	}

	/**
	 * @param amt Change the camera location in the X axis.
	 */
	public void changeX(double amt)
	{
		cameraLocation.setX(cameraLocation.getX() + amt);
	}

	/**
	 * @param amt Change the camera location in the Y axis.
	 */
	public void changeY(double amt)
	{
		cameraLocation.setY(cameraLocation.getY() + amt);
	}

	/**
	 * @param amt Change the camera location in the Z axis.
	 */
	public void changeZ(double amt)
	{
		cameraLocation.setZ(cameraLocation.getZ() + amt);
	}

	/**
	 * @param amt Change the pitch of the camera.
	 */
	public void changePitch(double amt)
	{
		Quaternion q = Quaternion.FromAngleAxis(new Vector3D(1, 0, 0), amt);
		orientation = q.multiply(orientation);
		orientation.normalize();
	}

	/**
	 * @param amt Change the yaw of the camera.
	 */
	public void changeYaw(double amt)
	{
		Quaternion q = Quaternion.FromAngleAxis(new Vector3D(0, 1, 0), amt);
		orientation = q.multiply(orientation);
		orientation.normalize();
	}

	/**
	 * @param amt Change the roll of the camera.
	 */
	public void changeRoll(double amt)
	{
		Quaternion q = Quaternion.FromAngleAxis(new Vector3D(0, 0, 1), amt);
		orientation = q.multiply(orientation);
		orientation.normalize();
	}

	/**
	 * @return The point that is the center of the camera
	 */
	public Point3D getLocation()
	{
		return (Point3D) cameraLocation.clone();
	}

	/**
	 * @return The point that the camera is looking at in absolute mode
	 */
	public Point3D getLookAtPoint()
	{
		return (Point3D) lookAtPoint.clone();
	}

	/**
	 * @return The up vecotr for absolute mode
	 */
	public Point3D getUpDirection()
	{
		return (Point3D) upDirection.clone();
	}

	/**
	 * @param newValue Changes the camera's location
	 */
	public void setLocation(Point3D newValue)
	{
		cameraLocation = (Point3D) newValue.clone();
	}

	/**
	 * @param newValue Changes the camera's look-at point
	 */
	public void setLookAtPoint(Point3D newValue)
	{
		lookAtPoint = (Point3D) newValue.clone();
	}

	/**
	 * @param newValue Changes the camera's up vector
	 */
	public void setUpDirection(Point3D newValue)
	{
		upDirection = (Point3D) newValue.clone();
	}

	/**
	 * @return The angle/axis representation of the camera orientation
	 */
	public Vector3D getOrientation()
	{
		return orientation.getAngleAxis();
	}

	/**
	 * @param q Sets the internal Quaternion to a new value
	 */
	public void setOrientation(Quaternion q)
	{
		orientation = q;
	}
}
