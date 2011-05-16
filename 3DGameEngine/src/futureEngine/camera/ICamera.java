package futureEngine.camera;

import futureEngine.scene.BoundingVolume;
import futureEngine.scene.Plane;
import graphicslib3D.*;

public interface ICamera {
	Vector3D getRightAxis();
	Vector3D getUpAxis();
	Vector3D getViewDirection();
	Point3D getLocation();

	void setRightAxis(Vector3D right);
	void setUpAxis(Vector3D up);
	void setViewDirection(Vector3D dir);
	void setLocation(Point3D loc);
	void setAxes(Vector3D right, Vector3D up, Vector3D viewDir);

	void lookAt(Point3D pos, Vector3D upDir);
	void setPerspectiveFrustum(double fovY, double aspect, double near, double far);

	void update(long time);

	void yaw(double mouseDeltaX);
	void pitch(double mouseDeltaY);
	void setStrafeMotion(double strafeMotion);
	void setForwardMotion(double forwardMotion);

	double[] getOrientation();

	public final double SPEED = .00000002;
	
	// returns true if bound intersects current frustum
	public boolean intersects(BoundingVolume bound);
	public void setFrustrumPlanes(Plane[] newPlanes);
}
