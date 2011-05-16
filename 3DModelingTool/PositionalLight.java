package a5;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import mathlib3D.Point3D;

public class PositionalLight extends Light
{
	private static final long serialVersionUID = 1L;

	private Point3D position;

	private float constantAttenuation;

	private float linearAttenuation;

	private float quadraticAttenuation;

	public PositionalLight()
	{
		position = new Point3D(-300, 300, 100);
		constantAttenuation = 1;
		linearAttenuation = 0;
		quadraticAttenuation = 0;
	}

	@Override
	public void draw(GLAutoDrawable drawable, int glLightNum)
	{
		GL gl = drawable.getGL();
		float[] pos = new float[] { (float) position.getX(), (float) position.getY(), (float) position.getZ(), 1.0f };
		gl.glLightfv(glLightNum, GL.GL_POSITION, pos, 0);
		drawAttenuation(glLightNum, gl);
		drawBaseLight(gl, glLightNum);
	}

	protected void drawAttenuation(int glLightNum, GL gl)
	{
		gl.glLightf(glLightNum, GL.GL_CONSTANT_ATTENUATION, getConstantAttenuation());
		gl.glLightf(glLightNum, GL.GL_LINEAR_ATTENUATION, getLinearAttenuation());
		gl.glLightf(glLightNum, GL.GL_QUADRATIC_ATTENUATION, getQuadraticAttenuation());
	}

	public Point3D getPosition()
	{
		return position;
	}

	public void setPosition(Point3D newPosition)
	{
		position = newPosition;
	}

	public float getConstantAttenuation()
	{
		return constantAttenuation;
	}

	public void setConstantAttenuation(float constantAttenuation)
	{
		this.constantAttenuation = constantAttenuation;
	}

	public float getLinearAttenuation()
	{
		return linearAttenuation;
	}

	public void setLinearAttenuation(float linearAttenuation)
	{
		this.linearAttenuation = linearAttenuation;
	}

	public float getQuadraticAttenuation()
	{
		return quadraticAttenuation;
	}

	public void setQuadraticAttenuation(float quadraticAttenuation)
	{
		this.quadraticAttenuation = quadraticAttenuation;
	}
}
