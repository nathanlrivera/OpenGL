package a5;

import java.util.Vector;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import mathlib3D.*;
import graphicslib3D.*;

/**
 * @author n8
 * 
 */
public class QuarterPatch extends SurfacePatch implements IQuarterPatch
{
	private static final long serialVersionUID = -1251013713498008203L;

	private Vector myControlPoints;

	@SuppressWarnings("unchecked")
	public QuarterPatch()
	{
		myControlPoints = new Vector();
		myControlPoints.add(new Point3D(0, 50, 0));
		myControlPoints.add(new Point3D(0, 25, 0));
		myControlPoints.add(new Point3D(0, -25, 0));
		myControlPoints.add(new Point3D(0, -50, 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphicslib3D.IQuarterPatch#getControlPointVector()
	 */
	public Vector getControlPointVector()
	{
		return myControlPoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphicslib3D.IQuarterPatch#setControlPointVector(java.util.Vector)
	 */
	public void setControlPointVector(Vector newVector)
	{
		myControlPoints = newVector;
		ControlMesh mesh = getControlMesh();
		for(int i = 0; i < 4; ++i)
		{
			Point3D currentPoint = (Point3D) myControlPoints.get(i);
			double x = currentPoint.getX();
			double y = currentPoint.getY();
			mesh.addElement((Point3D) currentPoint.clone(), i, 0);
			mesh.addElement(new Point3D(x, y, x * -0.5523), i, 1);
			mesh.addElement(new Point3D(x * 0.5523, y, -x), i, 2);
			mesh.addElement(new Point3D(0, y, -x), i, 3);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see a3.SurfacePatch#draw(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void draw(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		for(int i = 0; i < 4; ++i)
		{
			gl.glPushMatrix();
			gl.glRotated(i * 90.0, 0.0, 1.0, 0.0);
			super.draw(drawable);
			gl.glPopMatrix();
		}
	}
}
