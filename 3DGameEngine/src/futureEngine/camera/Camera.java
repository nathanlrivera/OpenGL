package futureEngine.camera;

import futureEngine.scene.BoundingVolume;
import futureEngine.scene.Plane;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Quaternion;
import graphicslib3D.Vector3D;

public class Camera implements ICamera {

	private double forwardMotion;
	private double strafeMotion;
	private Vector3D location;
	private Quaternion rotation;
	private Plane[] planes;

	public Camera() {
		location = new Vector3D(0, 0, 0);
		rotation = new Quaternion(1, new Vector3D(0, 1, 0));
		rotation.normalize();
		forwardMotion = 0;
		strafeMotion = 0;
	}

	@Override
	public void update(long time) {
		double[] angleAxis = rotation.getAngleAxis();
		Matrix3D mat = new Matrix3D(angleAxis[0], new Vector3D(angleAxis[1], angleAxis[2], angleAxis[3]));

		if (forwardMotion != 0) {
			double distance = time * SPEED * forwardMotion;
			Vector3D forward = mat.getRow(2);
			forward.scale(distance);
			location = location.add(forward);
		}

		if (strafeMotion != 0) {
			double distance = time * SPEED * strafeMotion;
			Vector3D strafe = mat.getRow(0);
			strafe.scale(distance);
			location = location.add(strafe);
		}
	}

	@Override
	public void pitch(double mouseDeltaY) {
		Quaternion nrot = new Quaternion(mouseDeltaY, new Vector3D(1, 0, 0));
		nrot.normalize();
		rotation = nrot.multiplyBy(rotation);
		rotation.normalize();
	}

	@Override
	public void yaw(double mouseDeltaX) {
		Quaternion nrot = new Quaternion(mouseDeltaX, new Vector3D(0, 1, 0));
		nrot.normalize();
		rotation = rotation.multiplyBy(nrot);

		rotation.normalize();
	}

	@Override
	public Point3D getLocation() {
		return new Point3D(location.getX(), location.getY(), location.getZ());
	}

	@Override
	public Vector3D getRightAxis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector3D getUpAxis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector3D getViewDirection() {
		return new Vector3D();
	}

	@Override
	public double[] getOrientation() {
		return rotation.getAngleAxis();
	}

	@Override
	public void lookAt(Point3D pos, Vector3D upDir) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAxes(Vector3D right, Vector3D up, Vector3D viewDir) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLocation(Point3D loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPerspectiveFrustum(double fovY, double aspect, double near,
			double far) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRightAxis(Vector3D right) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUpAxis(Vector3D up) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setViewDirection(Vector3D dir) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param forwardMotion
	 *            the forwardMotion to set
	 */
	public void setForwardMotion(double forwardMotion) {
		this.forwardMotion = forwardMotion;
	}

	/**
	 * @return the forwardMotion
	 */
	public double getForwardMotion() {
		return forwardMotion;
	}

	/**
	 * @param strafeMotion
	 *            the strafeMotion to set
	 */
	public void setStrafeMotion(double strafeMotion) {
		this.strafeMotion = strafeMotion;
	}

	/**
	 * @return the strafeMotion
	 */
	public double getStrafeMotion() {
		return strafeMotion;
	}

	@Override
	public boolean intersects(BoundingVolume bound) {
		for (Plane p : planes) {
			if (bound.isOnPositiveSide(p)) {
				return true;
			}
		}
		return false;
	}

	public void setFrustrumPlanes(Plane[] newPlanes) {
		planes = newPlanes;
	}
}
