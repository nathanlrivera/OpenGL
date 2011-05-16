package futureEngine.renderer.joglRenderer;

import java.awt.Canvas;
import java.nio.DoubleBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import apps.games.treasureHunt.Box;
import apps.games.treasureHunt.Crystal;
import apps.games.treasureHunt.Score;

import com.sun.opengl.util.GLUT;

import futureEngine.camera.ICamera;
import futureEngine.renderer.Renderer;
import futureEngine.scene.MS3DGroup;
import futureEngine.scene.MS3DModel;
import futureEngine.scene.MS3DTriangle;
import futureEngine.scene.MS3DVertex;
import futureEngine.scene.Plane;
import futureEngine.scene.SceneNode;
import futureEngine.scene.SkyBox;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;

public class JOGLRenderer extends Renderer implements GLEventListener {

	private GLU glu;
	private GLUT glut;
	private GLCanvas myCanvas;
	private long primitiveCount = 0;
	private JOGLSkyBox mySkyBox;

	private boolean orthoMode;
	
	public long getPrimitiveCount() {
		return primitiveCount;
	}

	public JOGLRenderer() {
		// initialize an (empty) renderqueue
		opaqueQueue = new ArrayList<SceneNode>();
		transparentQueue = new ArrayList<SceneNode>();
		orthoQueue = new ArrayList<SceneNode>();

		// create a canvas on which to draw
		GLCapabilities caps = new GLCapabilities();
		caps.setDoubleBuffered(true);
		myCanvas = new GLCanvas(caps);
		// specify 'this' (JFrame) object as a GLEventListener which will
		// get called when the canvas needs repainting
		myCanvas.addGLEventListener(this);
		orthoMode = false;
	}

	@Override
	public void clearRenderQueue() {
		opaqueQueue.clear();
		transparentQueue.clear();
		orthoQueue.clear();
	}

	@Override
	public void draw(Score score) {
		GL gl = getGL();
		gl.glPushAttrib(GL.GL_CURRENT_BIT);
		gl.glRasterPos2i(10, 10);
		gl.glColor3f(1, 1, 1);
		glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, score.getScore());
		gl.glPopAttrib();
	}

	private GL getGL() {
		GLContext context = GLContext.getCurrent();
		context.makeCurrent();
		return context.getGL();
	}

	@Override
	public void enableOrthoMode(boolean enable) {
		GLContext context = GLContext.getCurrent();
		context.makeCurrent();
		GLDrawable drawable = context.getGLDrawable();
		GL gl = context.getGL();
		if (enable) {
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glPushMatrix(); // save projection settings
			gl.glLoadIdentity();
			glu.gluOrtho2D(0.0, drawable.getWidth(), 0.0, drawable.getHeight());
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glPushMatrix(); // save model view settings
			gl.glLoadIdentity();
			gl.glDisable(GL.GL_DEPTH_TEST);
		} else {
			gl.glEnable(GL.GL_DEPTH_TEST);
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glPopMatrix(); // restore previous projection settings
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glPopMatrix(); // restore previous model view settings
		}
		orthoMode = enable;
	}

	@Override
	public ICamera getCamera() {
		return myCamera;
	}

	@Override
	public Canvas getCanvas() {
		return myCanvas;
	}

	@Override
	public boolean isInOrthoMode() {
		return orthoMode;
	}

	@Override
	public void processRenderQueue() {
		myCanvas.display();
	}

	public void display(GLAutoDrawable drawable) {
		final GL gl = drawable.getGL();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		primitiveCount = 0;

		double[] angleAxis = myCamera.getOrientation();
		Matrix3D mat = new Matrix3D(angleAxis[0], new Vector3D(angleAxis[1], angleAxis[2], angleAxis[3]));
		Point3D loc = myCamera.getLocation();
		gl.glMultMatrixd(mat.getValues(), 0);
		gl.glTranslated(-loc.getX(), -loc.getY(), -loc.getZ());

		getFrustumPlanes(gl);

		mySkyBox.draw(gl);

		renderOpaques();
		renderTransparents();
		renderOrthographics();

		gl.glFlush();
		checkForErrors(gl);
	}

	/**
	 * Checks for errors that may have occurred.
	 * 
	 * @param gl
	 *            The GL object to be used for drawing.
	 */
	private void checkForErrors(GL gl) {
		int errorCode = gl.glGetError();
		if (errorCode != GL.GL_NO_ERROR) {
			String errorString = glu.gluErrorString(errorCode);
			if (errorString == null)
				System.out.println("GLU returned null error string (invalid error code?)");
			else
				System.out.println(errorString);
		}
	}

	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		glu = new GLU();
		glut = new GLUT();
		GL gl = drawable.getGL(); // get the "Graphics" objects
		gl.glShadeModel(GL.GL_SMOOTH); // Enable Smooth Shading
		// gl.glClearColor(0.0f, 0.0f, 0.3f, 0.5f); // Black Background
		// gl.glClearDepth(1.0f); // Depth Buffer Setup
		// gl.glEnable(GL.GL_DEPTH_TEST); // Enables Depth Testing
		// gl.glDepthFunc(GL.GL_LEQUAL); // The Type Of Depth Testing To Do
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glCullFace(GL.GL_BACK);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		double aspect = (double) drawable.getWidth() / (double) drawable.getHeight();
		glu.gluPerspective(60.0f, aspect, 0.1f, 5000.0f);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	private void getFrustumPlanes(GL gl) {
		DoubleBuffer proj = DoubleBuffer.allocate(16);
		gl.glGetDoublev(GL.GL_PROJECTION_MATRIX, proj);
		DoubleBuffer modelView = DoubleBuffer.allocate(16);
		gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, modelView);
		Matrix3D p = new Matrix3D(proj.array());
		Matrix3D mv = new Matrix3D(modelView.array());
		p.concatenate(mv);
		double[] pm = p.getValues();
		Plane[] planes = new Plane[6];
		planes[0] = new Plane(pm[3] + pm[0], pm[7] + pm[4], pm[11] + pm[8], pm[15] + pm[12]);
		planes[1] = new Plane(pm[3] - pm[0], pm[7] - pm[4], pm[11] - pm[8], pm[15] - pm[12]);
		planes[2] = new Plane(pm[3] + pm[1], pm[7] + pm[5], pm[11] + pm[9], pm[15] + pm[13]);
		planes[3] = new Plane(pm[3] - pm[1], pm[7] - pm[5], pm[11] - pm[9], pm[15] - pm[13]);
		planes[4] = new Plane(pm[3] + pm[2], pm[7] + pm[6], pm[11] + pm[10], pm[15] + pm[14]);
		planes[5] = new Plane(pm[3] - pm[2], pm[7] - pm[6], pm[11] - pm[10], pm[15] - pm[14]);
		myCamera.setFrustrumPlanes(planes);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Crystal crystal) {
		GL gl = getGL();
		gl.glPushMatrix();
		gl.glMultMatrixd(crystal.getWorldTranslation().getValues(), 0);
		gl.glMultMatrixd(crystal.getWorldRotation().getValues(), 0);
		gl.glMultMatrixd(crystal.getWorldScale().getValues(), 0);
		byte[] color = crystal.getColor();
		gl.glPushAttrib(GL.GL_CURRENT_BIT);
		gl.glColor3b(color[0], color[1], color[2]);
		glut.glutSolidOctahedron();
		gl.glPopAttrib();
		gl.glPopMatrix();
		primitiveCount += 8;
	}

	@Override
	public void draw(Box box) {
		GL gl = getGL();
		gl.glPushMatrix();
		gl.glMultMatrixd(box.getWorldTranslation().getValues(), 0);
		gl.glMultMatrixd(box.getWorldRotation().getValues(), 0);
		gl.glMultMatrixd(box.getWorldScale().getValues(), 0);
		byte[] color = box.getColor();
		gl.glPushAttrib(GL.GL_CURRENT_BIT);
		gl.glColor3b(color[0], color[1], color[2]);
		glut.glutSolidCube(1f);
		gl.glPopAttrib();
		gl.glPopMatrix();
		
		primitiveCount += 6;
	}
	
	public void setSkyBox(SkyBox sky) {
		this.mySkyBox = new JOGLSkyBox(sky);
	}

	@Override
	public void draw(MS3DModel ms3dModel) {
		GL gl = getGL();
		gl.glPushMatrix();
		gl.glMultMatrixd(ms3dModel.getWorldTranslation().getValues(), 0);
		gl.glMultMatrixd(ms3dModel.getWorldRotation().getValues(), 0);
		gl.glMultMatrixd(ms3dModel.getWorldScale().getValues(), 0);
		
		gl.glPushAttrib(GL.GL_CURRENT_BIT);
		gl.glColor3d(0.2, 0.2, 0.2);
		
		MS3DGroup[] groups = ms3dModel.getGroups();
		MS3DTriangle[] triangles = ms3dModel.getTriangles();
		//draw each group in the model
		for (int g = 0; g < groups.length; g++) {
			//get the next group
			MS3DGroup curGroup = groups[g];
			//bind (activate) this group's material and texture
			//bindMaterial(gl, curGroup.getMaterialIndex());
			//get the indices for each triangle in the group
			int numTriangles = curGroup.getNumTriangles();
			short [] triIndices = curGroup.getTriangleIndices();
			//draw each triangle in the group
			gl.glBegin (GL.GL_TRIANGLES);
			for (int tri=0; tri<numTriangles; tri++) {
				MS3DTriangle curTri = triangles[triIndices[tri]];
				drawTriangle (gl, curTri, ms3dModel.getVertices());
			}
			gl.glEnd();
		}//end for each group
		gl.glPopAttrib();
		gl.glPopMatrix();
	}
	
	private void drawTriangle(GL gl, MS3DTriangle tri, MS3DVertex[] vertices) {
		//get the attribute data for the current triangle
		short [] vertIndices = tri.getVertexIndices(); //indices for each of the (3) vertices
		float [] sTexCoords = tri.getSTexCoord() ; //’s’ tex coords for all three vertices
		float [] tTexCoords = tri.getTTexCoord() ; //’t’ tex coords for all three vertices
		float [][] normals = tri.getVertexNormals() ; //[A B C] normals for all three vertices
		//output each vertex of the triangle
		for (int v=0; v<3; v++) {
			MS3DVertex vert = vertices[vertIndices[v]]; //get the next vertex
			//output vertex texture coords
			float s = sTexCoords[v];
			float t = tTexCoords[v];
			gl.glTexCoord2f(s,1-t); //MS3D 't' coords are for origins at TOP-left.
			//output the vertex normal
			float [] normal = normals[v];
			gl.glNormal3fv(normal,0);
			//output the vertex geometry
			float [] geom = vert.getGeometry() ;
			gl.glVertex3fv(geom,0);
		}//end for each vertex
		++primitiveCount;
	}//end drawTriangle()
}
