package a5;

import java.math.BigDecimal;
import mathlib3D.*;

/**
 * Quaternion operations and algorithms for camera orientations
 */
public class Quaternion
{
	private Vector3D axis;

	private double angle;

	/**
	 * Creates a Quaternion from an angle/axis representation
	 * 
	 * @param theAxis The axis of rotation, x,y,z
	 * @param theAngle The angle of rotation, w
	 * @return The Quaternion representing the angle/axis
	 */
	public static Quaternion FromAngleAxis(Vector3D theAxis, double theAngle)
	{
		double x, y, z, w;
		double halfAngleInRadians = Math.toRadians(theAngle * 0.5);
		double sinPart = Math.sin(halfAngleInRadians);
		w = Math.cos(halfAngleInRadians);
		x = sinPart * theAxis.getX();
		y = sinPart * theAxis.getY();
		z = sinPart * theAxis.getZ();
		return new Quaternion(x, y, z, w);
	}

	/**
	 * Constructs a default (unit) Quaternion
	 */
	public Quaternion()
	{
		this(0, 0, 0, 1);
	}

	/**
	 * Constructs a Quaternion based on x,y,z, and w values
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public Quaternion(double x, double y, double z, double w)
	{
		axis = new Vector3D(x, y, z);
		angle = w;
	}

	/**
	 * Constructs a Quaternion from an axis Vector3D and angle of rotation about
	 * that axis.
	 * 
	 * @param v The Vector3D of the axis
	 * @param w The rotation about the axis
	 */
	public Quaternion(Vector3D v, double w)
	{
		axis = v;
		angle = w;
	}

	/**
	 * Multiples two Quaternions
	 * 
	 * @param q The left-hand Quaternion to multiply by.
	 * @return The Quaternion that represents the reult of the multiplication
	 */
	public Quaternion multiply(Quaternion q)
	{
		double qT, qX, qY, qZ;
		double aT, aX, aY, aZ;
		double bT, bX, bY, bZ;
		aT = angle;
		aX = axis.getX();
		aY = axis.getY();
		aZ = axis.getZ();
		bT = q.getW();
		bX = q.getX();
		bY = q.getY();
		bZ = q.getZ();
		qT = (aT * bT) - (aX * bX) - (aY * bY) - (aZ * bZ);
		qX = (aT * bX) + (aX * bT) + (aY * bZ) - (aZ * bY);
		qY = (aT * bY) - (aX * bZ) + (aY * bT) + (aZ * bX);
		qZ = (aT * bZ) + (aX * bY) - (aY * bX) + (aZ * bT);
		return new Quaternion(qX, qY, qZ, qT);
	}

	/**
	 * Gets the angle/axis representation of the Quaternion
	 * 
	 * @return The angle/axis representation stored in a Vector3D object.
	 */
	public Vector3D getAngleAxis()
	{
		BigDecimal w = new BigDecimal(Math.acos(angle));
		BigDecimal sinW = new BigDecimal(Math.sin(w.doubleValue()));
		if(sinW.compareTo(new BigDecimal(0)) != 0)
		{
			BigDecimal x = new BigDecimal(axis.getX()).divide(sinW, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal y = new BigDecimal(axis.getY()).divide(sinW, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal z = new BigDecimal(axis.getZ()).divide(sinW, BigDecimal.ROUND_HALF_EVEN);
			return new Vector3D(x.doubleValue(), y.doubleValue(), z.doubleValue(), w.multiply(new BigDecimal(2)).doubleValue());
		}
		else
			return new Vector3D(0, 0, 0, w.multiply(new BigDecimal(2)).doubleValue());
	}

	/**
	 * Normalizes the Quaternion into a unit Quaternion.
	 */
	public void normalize()
	{
		double x = axis.getX() * axis.getX();
		double y = axis.getY() * axis.getY();
		double z = axis.getZ() * axis.getZ();
		double w = angle * angle;
		double magnitude = Math.sqrt(x + y + z + w);
		if(magnitude != 0)
		{
			axis.setX(axis.getX() / magnitude);
			axis.setY(axis.getY() / magnitude);
			axis.setZ(axis.getZ() / magnitude);
			angle = angle / magnitude;
		}
		else
		// magnitude was zero, set to identity to avoid divide by zero
		{
			axis.setX(0);
			axis.setY(0);
			axis.setZ(0);
			// Preserve the sign of the angle
			if(angle > 0)
				angle = 1;
			else
				angle = -1;
		}
	}

	public double getW()
	{
		return angle;
	}

	public double getX()
	{
		return axis.getX();
	}

	public double getY()
	{
		return axis.getY();
	}

	public double getZ()
	{
		return axis.getZ();
	}

	/**
	 * Slerp Algorithm (Largely modeled after the implementation in the Generic
	 * Graphics Toolkit see http://ggt.sourceforge.net/html/group__Interp.html
	 * 
	 * @param q0
	 * @param q1
	 * @param t
	 * @return
	 */
	public static Quaternion Slerp(Quaternion q0, Quaternion q1, double t)
	{
		double cosom = q0.getX() * q1.getX() + q0.getY() * q1.getY() + q0.getZ() * q1.getZ() + q0.getW() * q1.getW();
		double tmp0, tmp1, tmp2, tmp3;
		if(cosom < 0.0)
		{
			cosom = -cosom;
			tmp0 = -q1.getX();
			tmp1 = -q1.getY();
			tmp2 = -q1.getZ();
			tmp3 = -q1.getW();
		}
		else
		{
			tmp0 = q1.getX();
			tmp1 = q1.getY();
			tmp2 = q1.getZ();
			tmp3 = q1.getW();
		}
		/* calc coeffs */
		double scale0, scale1;
		if((1.0 - cosom) > 0.0001)
		{
			// standard case (slerp)
			double omega = Math.acos(cosom);
			double sinom = Math.sin(omega);
			scale0 = Math.sin((1.0 - t) * omega) / sinom;
			scale1 = Math.sin(t * omega) / sinom;
		}
		else
		{
			/* just lerp */
			scale0 = 1.0 - t;
			scale1 = t;
		}
		return new Quaternion(scale0 * q0.getX() + scale1 * tmp0, scale0 * q0.getY() + scale1 * tmp1, scale0 * q0.getZ() + scale1 * tmp2, scale0 * q0.getW() + scale1 * tmp3);
	}
}
