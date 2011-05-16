package a5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.FloatBuffer;
import java.util.Hashtable;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.Timer;
import mathlib3D.Point3D;
import mathlib3D.Vector3D;

public class GLCanvasListener implements GLEventListener, ActionListener
{
	private GLU glu;

	private Camera myCamera;

	private Hashtable<String, Visual> worldObjects;

	private Hashtable<String, Light> lights;

	private JCheckBoxMenuItem enableLight;

	private JCheckBoxMenuItem smoothShading;
	
	private float elapsedTime;
	
	private float timeIncrement; 

	public GLCanvasListener(Camera camera, Hashtable<String, Visual> world, JCheckBoxMenuItem enableLightItem, JCheckBoxMenuItem shadingMenuitem, Hashtable<String, Light> theLights)
	{
		myCamera = camera;
		worldObjects = world;
		enableLight = enableLightItem;
		smoothShading = shadingMenuitem;
		lights = theLights;
		Timer timer = new Timer(20, this);
		elapsedTime = 0.0f;
		timeIncrement = 2.0f;
		timer.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
	 */
	public void display(GLAutoDrawable drawable)
	{
		GL gl = drawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		for(int i = 0; i < 8; ++i)
			gl.glDisable(getGLLightNum(i));
		if(enableLight.isSelected())
		{
			gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(AmbientLight.getInstance().getIntensity().getRGBComponents(null)));
			int i = 0;
			for(Light l : lights.values())
			{
				l.draw(drawable, getGLLightNum(i));
				gl.glEnable(getGLLightNum(i));
				++i;
			}
			gl.glEnable(GL.GL_LIGHTING);
		}
		else
			gl.glDisable(GL.GL_LIGHTING);
		if(smoothShading.isSelected())
			gl.glShadeModel(GL.GL_SMOOTH);
		else
			gl.glShadeModel(GL.GL_FLAT);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(60.0f, (float) drawable.getWidth() / (float) drawable.getHeight(), 0.01f, 5000.0f);
		Point3D cameraLocation = myCamera.getLocation();
		if(myCamera.getRelativeMode())
		{
			Vector3D angleAxis = myCamera.getOrientation();
			gl.glRotated(Math.toDegrees(angleAxis.getW()), angleAxis.getX(), angleAxis.getY(), angleAxis.getZ());
			gl.glTranslated(-cameraLocation.getX(), -cameraLocation.getY(), -cameraLocation.getZ());
		}
		else
		{
			Point3D lookAtPoint = myCamera.getLookAtPoint();
			Point3D upDirection = myCamera.getUpDirection();
			glu.gluLookAt(cameraLocation.getX(), cameraLocation.getY(), cameraLocation.getZ(), lookAtPoint.getX(), lookAtPoint.getY(), lookAtPoint.getZ(), upDirection.getX(), upDirection.getY(), upDirection.getZ());
		}
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		for(Visual v : worldObjects.values())
		{
			Shader s = v.getShader();
			if(s != null && s.isParameterized())
				s.setParameterValue(elapsedTime);
			v.draw(drawable, enableLight.isSelected());
		}
		checkForErrors(gl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.media.opengl.GLEventListener#displayChanged(javax.media.opengl.GLAutoDrawable,
	 *      boolean, boolean)
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
	 */
	public void init(GLAutoDrawable drawable)
	{
		System.out.println("OpenGL Version " + GLU.getCurrentGL().glGetString(GL.GL_VERSION));
		glu = new GLU();
		GL gl = drawable.getGL();
		gl.glEnable(GL.GL_AUTO_NORMAL);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable,
	 *      int, int, int, int)
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
	{
	}

	/**
	 * Checks for errors that may have occurred.
	 * 
	 * @param gl The GL object to be used for drawing.
	 */
	private void checkForErrors(GL gl)
	{
		int errorCode = gl.glGetError();
		if(errorCode != GL.GL_NO_ERROR)
		{
			String errorString = glu.gluErrorString(errorCode);
			if(errorString == null)
				System.out.println("GLU returned null error string (invalid error code?)");
			else
				System.out.println(errorString);
		}
	}

	private int getGLLightNum(int index)
	{
		switch(index)
		{
			case 0:
				return GL.GL_LIGHT0;
			case 1:
				return GL.GL_LIGHT1;
			case 2:
				return GL.GL_LIGHT2;
			case 3:
				return GL.GL_LIGHT3;
			case 4:
				return GL.GL_LIGHT4;
			case 5:
				return GL.GL_LIGHT5;
			case 6:
				return GL.GL_LIGHT6;
			case 7:
				return GL.GL_LIGHT7;
			default:
				return GL.GL_LIGHT0;
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		elapsedTime = (elapsedTime += timeIncrement) % 1024;
		
	}
}
