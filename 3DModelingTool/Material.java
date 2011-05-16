package a5;

import java.awt.Color;
import java.io.Serializable;
import javax.media.opengl.GL;

public class Material implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Color ambient;

	private Color diffuse;

	private Color specular;

	private Color emission;

	private float shininess;

	private String name;

	public Material()
	{
		ambient = Color.blue;
		diffuse = Color.green;
		specular = Color.white;
		emission = Color.black;
		shininess = 32.0f;
	}

	public void makeCurrent(GL gl)
	{
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, getAmbient().getRGBComponents(null), 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, getDiffuse().getRGBComponents(null), 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, getSpecular().getRGBComponents(null), 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, getEmission().getRGBComponents(null), 0);
		gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, getShininess());
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

	public Color getEmission()
	{
		return emission;
	}

	public void setEmission(Color newEmission)
	{
		emission = newEmission;
	}

	public float getShininess()
	{
		return shininess;
	}

	public void setShininess(float newShininess)
	{
		shininess = newShininess;
	}

	public Color getSpecular()
	{
		return specular;
	}

	public void setSpecular(Color newSpecular)
	{
		specular = newSpecular;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String newName)
	{
		name = newName;
	}
}
