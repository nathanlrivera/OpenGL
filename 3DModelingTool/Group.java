package a5;

import java.util.Vector;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 * @author n8
 * 
 */
public class Group extends WorldObject
{
	private static final long serialVersionUID = -4331296825864772714L;

	private Vector<WorldObject> myObjects;

	public Group()
	{
		super();
		myObjects = new Vector<WorldObject>();
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
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glMultMatrixd(getTransform().getValues(), 0);
		for(WorldObject obj : myObjects)
			obj.draw(drawable, lighting);
		gl.glPopMatrix();
	}

	/**
	 * @param obj
	 */
	public void add(WorldObject obj)
	{
		myObjects.add(obj);
	}
}
