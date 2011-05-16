package a5;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import mathlib3D.Vector3D;

public class DistantLight extends Light
{
	private static final long serialVersionUID = 1L;

	private Vector3D direction;

	public DistantLight()
	{
		direction = new Vector3D(-400, -400, -400);
	}

	@Override
	public void draw(GLAutoDrawable drawable, int glLightNum)
	{
		GL gl = drawable.getGL();
		float[] dir = new float[] { -(float) direction.getX(), -(float) direction.getY(), -(float) direction.getZ(), 0.0f };
		gl.glLightfv(glLightNum, GL.GL_POSITION, dir, 0);
		drawBaseLight(gl, glLightNum);
	}

	public Vector3D getDirection()
	{
		return direction;
	}

	public void setDirection(Vector3D newDirection)
	{
		direction = newDirection;
	}
}
