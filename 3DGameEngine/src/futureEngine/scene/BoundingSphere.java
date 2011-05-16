package futureEngine.scene;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;

public class BoundingSphere extends BoundingVolume {
	
	private Point3D center;
	private double radius;
	
	public BoundingSphere(Point3D location, double rad) {
		center = location;
		radius = rad;
	}
	
	public BoundingSphere(Point3D[] points) {
		computeFromPoints(points);
	}
	
	@Override
	public void computeFromPoints(Point3D[] points) {
		double x = 0, y = 0, z = 0;
		int numPoints = points.length;
		for (int i = 0; i < numPoints; ++i) {
			x += points[i].getX();
			y += points[i].getY();
			z += points[i].getZ();
		}
		center = new Point3D(x / numPoints, y / numPoints, z / numPoints);
		radius = 0;
		double distance = 0;
		for (int j = 0; j < numPoints; ++j) {
			distance = center.distanceTo(points[j]);
			if (distance > radius) {
				radius = distance;
			}
		}

	}

	@Override
	public boolean contains(Point3D p) {
		return center.distanceTo(p) <= radius;
	}

	@Override
	public boolean isOnPositiveSide(Plane p) {
		double dist = p.distanceTo(center);
		return dist < -radius;
	}

	@Override
	public BoundingVolume merge(BoundingVolume bv) {
		if (bv == null) return this;
		
		BoundingSphere S2 = (BoundingSphere)bv;
		double L = center.distanceTo(S2.center);
		if (L + S2.radius <= radius) {
			//this contains S2
			return this;
		}
		else if (L + radius <= S2.radius) {
			//S2 contains this
			return S2;
		}
		else {
			//overlap or disjoint case
			double newRadius = (L + radius + S2.radius) / 2;
			Vector3D C1 = new Vector3D(center);
			Vector3D C2 = new Vector3D(S2.center);
			double tCenter = ((L + S2.radius - radius) / 2) * (1 / L);
			Vector3D newC = C1.add(C2.minus(C1).mult(tCenter));
			Point3D newLocation = new Point3D(newC.getX(), newC.getY(), newC.getZ());
			return new BoundingSphere(newLocation, newRadius);
		}
	}

	@Override
	public BoundingVolume transformBy(Matrix3D mat) {
		Point3D newCenter = center.mult(mat);
		Point3D edge = new Point3D(center.getX() + radius, center.getY() + radius, center.getZ() + radius);
		Point3D newEdge = edge.mult(mat);
		double newRadius = newCenter.distanceTo(newEdge);
		return new BoundingSphere(newCenter, newRadius);
	}
}
