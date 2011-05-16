package a5;

import java.io.Serializable;
import javax.media.opengl.GLAutoDrawable;
import mathlib3D.Matrix3D;
import mathlib3D.Vector3D;

public abstract class WorldObject implements Serializable
{
	public abstract void draw(GLAutoDrawable drawable, boolean lighting);

	private Vector3D myScale;

	private Vector3D myRotation;

	private Vector3D myTranslation;

	public WorldObject()
	{
		myScale = new Vector3D(1, 1, 1);
		myRotation = new Vector3D(0, 0, 0);
		myTranslation = new Vector3D(0, 0, 0);
	}

	public Matrix3D getTransform()
	{
		Matrix3D m = new Matrix3D();
		m.setToIdentity();
		m.translate(myTranslation.getX(), myTranslation.getY(), myTranslation.getZ());
		m.rotate(myRotation.getX(), myRotation.getY(), myRotation.getZ());
		m.scale(myScale.getX(), myScale.getY(), myScale.getZ());
		return m;
	}

	public Vector3D getRotation()
	{
		return myRotation;
	}

	public void setRotation(Vector3D newRotation)
	{
		myRotation = newRotation;
	}

	public Vector3D getScale()
	{
		return myScale;
	}

	public void setScale(Vector3D newScale)
	{
		myScale = newScale;
	}

	public Vector3D getTranslation()
	{
		return myTranslation;
	}

	public void setTranslation(Vector3D newTranslation)
	{
		myTranslation = newTranslation;
	}
}
