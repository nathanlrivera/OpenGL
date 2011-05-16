package futureEngine.scene;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;

public abstract class BoundingVolume {
	public abstract void computeFromPoints(Point3D[] points);
	public abstract boolean contains(Point3D p);
	public abstract BoundingVolume transformBy(Matrix3D mat);
	public abstract boolean isOnPositiveSide(Plane p);
	public abstract BoundingVolume merge(BoundingVolume bv);
}
