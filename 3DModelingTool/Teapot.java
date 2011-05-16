package a5;

import java.util.Vector;
import javax.media.opengl.GLAutoDrawable;
import com.sun.opengl.util.GLUT;
import mathlib3D.Matrix3D;
import graphicslib3D.Shape3D;

public class Teapot extends Shape3D
{
	private static final long serialVersionUID = 1L;

	private double scale;

	public Teapot()
	{
		scale = 100.0;
	}

	@Override
	public void draw(GLAutoDrawable drawable)
	{
		GLUT glut = new GLUT();
		glut.glutSolidTeapot(scale);
	}

	@Override
	public Vector getPolygons(Matrix3D arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public double getScale()
	{
		return scale;
	}

	public void setScale(double newScale)
	{
		scale = newScale;
	}
}
