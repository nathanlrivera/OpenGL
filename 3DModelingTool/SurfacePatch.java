package a5;

import java.util.Vector;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import mathlib3D.Matrix3D;
import mathlib3D.Point3D;
import graphicslib3D.ControlMesh;
import graphicslib3D.ISurfacePatch;
import graphicslib3D.Shape3D;

/**
 * Implements a Bezier surface patch
 * 
 */
public class SurfacePatch extends Shape3D implements ISurfacePatch
{
	private static final long serialVersionUID = -3117618629156125250L;

	private ControlMesh myControlMesh;

	private int myMaxRecursionLevel;

	public SurfacePatch()
	{
		super();
		myControlMesh = new ControlMesh();
		myMaxRecursionLevel = 3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphicslib3D.Shape3D#draw(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void draw(GLAutoDrawable drawable)
	{
		double[] controlPoints = new double[48];
		for(int i = 0; i < 16; ++i)
		{
			Point3D p = myControlMesh.elementAt(i / 4, i % 4);
			int baseIndex = i * 3;
			controlPoints[baseIndex] = p.getX();
			controlPoints[baseIndex + 1] = p.getY();
			controlPoints[baseIndex + 2] = p.getZ();
		}
		double uMin = 0.0, uMax = 1.0, vMin = 0.0, vMax = 1.0;
		int uStride = 3, vStride = 12, nSteps = 20, curveDegree = 3;
		GL gl = drawable.getGL();
		gl.glMap2d(GL.GL_MAP2_VERTEX_3, uMin, uMax, uStride, curveDegree + 1, vMin, vMax, vStride, curveDegree + 1, controlPoints, 0);
		gl.glEnable(GL.GL_MAP2_VERTEX_3);
		gl.glMapGrid2d(nSteps, uMin, uMax, nSteps, vMin, vMax);
		gl.glEvalMesh2(GL.GL_FILL, 0, 20, 0, 20);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphicslib3D.Shape3D#getPolygons(mathlib3D.Matrix3D)
	 */
	@Override
	public Vector getPolygons(Matrix3D transMatrix)
	{
		// TODO
		return new Vector();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphicslib3D.ISurfacePatch#getControlMesh()
	 */
	public ControlMesh getControlMesh()
	{
		return myControlMesh;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphicslib3D.ISurfacePatch#getMaxRecursionLevel()
	 */
	public int getMaxRecursionLevel()
	{
		return myMaxRecursionLevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphicslib3D.ISurfacePatch#getPatchName()
	 */
	public String getPatchName()
	{
		return getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphicslib3D.ISurfacePatch#setControlMesh(graphicslib3D.ControlMesh)
	 */
	public void setControlMesh(ControlMesh newMesh)
	{
		myControlMesh = newMesh;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphicslib3D.ISurfacePatch#setMaxRecursionLevel(int)
	 */
	public void setMaxRecursionLevel(int newLevel)
	{
		myMaxRecursionLevel = newLevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphicslib3D.ISurfacePatch#setPatchName(java.lang.String)
	 */
	public void setPatchName(String newName)
	{
		setName(newName);
	}
}
