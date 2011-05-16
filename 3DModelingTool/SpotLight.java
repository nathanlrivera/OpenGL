package a5;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import mathlib3D.Point3D;
import mathlib3D.Vector3D;

public class SpotLight extends PositionalLight
{
	private static final long serialVersionUID = 1L;

	private Vector3D direction;

	private float cutoffAngle;

	private float exponent;

	public SpotLight()
	{
		direction = new Vector3D(0, 0, -300);
		setPosition(new Point3D(0, 0, 300));
		cutoffAngle = 90;
		exponent = 45;
	}

	public float getCutoffAngle()
	{
		return cutoffAngle;
	}

	public void setCutoffAngle(float cutoffAngle)
	{
		this.cutoffAngle = cutoffAngle;
	}

	public float getExponent()
	{
		return exponent;
	}

	public void setExponent(float exponent)
	{
		this.exponent = exponent;
	}

	@Override
	public void draw(GLAutoDrawable drawable, int glLightNum)
	{
		float[] dir = new float[] { (float) getDirection().getX(), (float) getDirection().getY(), (float) getDirection().getZ() };
		float[] pos = new float[] { (float) getPosition().getX(), (float) getPosition().getY(), (float) getPosition().getZ(), 1.0f };
		GL gl = drawable.getGL();
		drawBaseLight(gl, glLightNum);
		gl.glLightfv(glLightNum, GL.GL_POSITION, pos, 0);
		gl.glLightfv(glLightNum, GL.GL_SPOT_DIRECTION, dir, 0);
		gl.glLightf(glLightNum, GL.GL_SPOT_CUTOFF, getCutoffAngle());
		gl.glLightf(glLightNum, GL.GL_SPOT_EXPONENT, getExponent());
		drawAttenuation(glLightNum, gl);
		gl.glEnable(glLightNum);
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
