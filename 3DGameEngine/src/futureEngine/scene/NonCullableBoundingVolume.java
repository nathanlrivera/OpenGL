package futureEngine.scene;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;

public class NonCullableBoundingVolume extends BoundingVolume {

	@Override
	public void computeFromPoints(Point3D[] points) {
	}

	@Override
	public boolean contains(Point3D p) {
		return false;
	}

	@Override
	public boolean isOnPositiveSide(Plane p) {
		return false;
	}

	@Override
	public BoundingVolume merge(BoundingVolume bv) {
		return bv;
	}

	@Override
	public BoundingVolume transformBy(Matrix3D mat) {
		return this;
	}

}
