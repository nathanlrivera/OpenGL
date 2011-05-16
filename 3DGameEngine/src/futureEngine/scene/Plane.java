package futureEngine.scene;

import graphicslib3D.Point3D;

public class Plane {
	private double a, b, c, d;
	
	public Plane(double aValue, double bValue, double cValue, double dValue) {
		a = aValue;
		b = bValue;
		c = cValue;
		d = dValue;
	}

	/**
	 * @param a the a to set
	 */
	public void setA(double a) {
		this.a = a;
	}

	/**
	 * @return the a
	 */
	public double getA() {
		return a;
	}

	/**
	 * @param b the b to set
	 */
	public void setB(double b) {
		this.b = b;
	}

	/**
	 * @return the b
	 */
	public double getB() {
		return b;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(double c) {
		this.c = c;
	}

	/**
	 * @return the c
	 */
	public double getC() {
		return c;
	}

	/**
	 * @param d the d to set
	 */
	public void setD(double d) {
		this.d = d;
	}

	/**
	 * @return the d
	 */
	public double getD() {
		return d;
	}
	
	public double distanceTo(Point3D p) {
		return (p.getX() * a) + (p.getY() * b) + (p.getZ() * c) + d;
	}
}
