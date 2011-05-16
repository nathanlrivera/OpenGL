package a5;

import java.awt.Color;
import java.io.Serializable;
import java.nio.FloatBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public abstract class Light implements Serializable
{
	private String myName;

	protected Color ambient;

	protected Color diffuse;

	protected Color specular;

	public Light()
	{
		myName = "";
		ambient = Color.black;
		diffuse = Color.white;
		specular = Color.white;
	}

	public String getName()
	{
		return myName;
	}

	public void setName(String newName)
	{
		myName = newName;
	}

	public Color getAmbient()
	{
		return ambient;
	}

	public void setAmbient(Color newAmbient)
	{
		ambient = newAmbient;
	}

	public Color getDiffuse()
	{
		return diffuse;
	}

	public void setDiffuse(Color newDiffuse)
	{
		diffuse = newDiffuse;
	}

	public Color getSpecular()
	{
		return specular;
	}

	public void setSpecular(Color newSpecular)
	{
		specular = newSpecular;
	}

	abstract void draw(GLAutoDrawable drawable, int glLightNum);

	protected void drawBaseLight(GL gl, int glLightNum)
	{
		gl.glLightfv(glLightNum, GL.GL_AMBIENT, FloatBuffer.wrap(ambient.getRGBComponents(null)));
		gl.glLightfv(glLightNum, GL.GL_DIFFUSE, FloatBuffer.wrap(diffuse.getRGBComponents(null)));
		gl.glLightfv(glLightNum, GL.GL_SPECULAR, FloatBuffer.wrap(specular.getRGBComponents(null)));
	}
}
