package a5;

import java.nio.FloatBuffer;
import graphicslib3D.Shape3D;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 * Represents a visible/drawable world object.
 * 
 */
public class Visual extends WorldObject
{
	private static final long serialVersionUID = 1779477872770420365L;

	private Shape3D myShape;

	private Material myMaterial;
	
	private Shader myShader;

	/**
	 * @param shape The Shape3D to encapsulate
	 */
	public Visual(Shape3D shape)
	{
		super();
		setShape(shape);
		setMaterial(new Material());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see a3.WorldObject#draw(javax.media.opengl.GLAutoDrawable)
	 */
	@Override
	public void draw(GLAutoDrawable drawable, boolean lighting)
	{
		GL gl = drawable.getGL();
		
		Shader s = getShader();
		if (s != null)
		{
			gl.glUseProgramObjectARB(s.getProgramID(gl));
			if(s.isParameterized())
				gl.glUniform1fARB(s.getParameterID(gl), s.getParameterValue());
		}
		else
			gl.glUseProgramObjectARB(0);
		
		if(lighting)
			getMaterial().makeCurrent(gl);
		else
			gl.glColor3fv(FloatBuffer.wrap(getMaterial().getDiffuse().getRGBColorComponents(null)));
		
		gl.glPushMatrix();
		gl.glMultMatrixd(getTransform().getValues(), 0);
		getShape().draw(drawable);
		gl.glPopMatrix();
		gl.glDisable(GL.GL_TEXTURE_2D);
	}

	/**
	 * Gets the name of this Visual
	 * 
	 * @return The name of this Visual
	 */
	public String getName()
	{
		return getShape().getName();
	}

	/**
	 * @param myShape the new Shape3D to set
	 */
	public void setShape(Shape3D newShape)
	{
		myShape = newShape;
	}

	/**
	 * @return the Shape3D contained in this object
	 */
	public Shape3D getShape()
	{
		return myShape;
	}

	public Material getMaterial()
	{
		return myMaterial;
	}

	public void setMaterial(Material newMaterial)
	{
		myMaterial = newMaterial;
	}

	public Shader getShader()
	{
		return myShader;
	}

	public void setShader(Shader newShader)
	{
		myShader = newShader;
	}
}
