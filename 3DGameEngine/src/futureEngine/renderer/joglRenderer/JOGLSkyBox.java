package futureEngine.renderer.joglRenderer;

import java.io.File;
import java.io.IOException;
import java.nio.DoubleBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLException;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

import futureEngine.scene.SkyBox;

public class JOGLSkyBox {

	private Texture front, back, left, right, top, bottom;
	private boolean texturesLoaded = false;
	private SkyBox mySkyBox;
	
	public JOGLSkyBox(SkyBox sky) {
		mySkyBox = sky;
	}

	private void loadTextures() {
		try {
			front = TextureIO.newTexture(new File(mySkyBox.getFront()), false);
			back = TextureIO.newTexture(new File(mySkyBox.getBack()), false);
			left = TextureIO.newTexture(new File(mySkyBox.getLeft()), false);
			right = TextureIO.newTexture(new File(mySkyBox.getRight()), false);
			top = TextureIO.newTexture(new File(mySkyBox.getTop()), false);
			bottom = TextureIO.newTexture(new File(mySkyBox.getBottom()), false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		texturesLoaded = true;
	}

	public void draw(GL gl) {
		if (!texturesLoaded)
			loadTextures();
		
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glDepthMask(false);
		gl.glDisable(GL.GL_DEPTH_TEST);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();

		// remove camera translation from MV
		DoubleBuffer modelView = DoubleBuffer.allocate(16);
		gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, modelView);
		double[] mat = modelView.array();
		mat[12] = mat[13] = mat[14] = 0;
		gl.glLoadMatrixd(mat, 0);

		gl.glEnable(GL.GL_TEXTURE_2D);

		drawFront(gl);
		drawBack(gl);
		drawLeft(gl);
		drawRight(gl);
		drawTop(gl);
		drawBottom(gl);

		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();
		
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glDepthMask(true);
		gl.glEnable(GL.GL_DEPTH_TEST);
	}

	private void drawBottom(GL gl) {
		bottom.bind();
		bottom.enable();

		gl.glPushMatrix();
		gl.glRotatef(-90f, 1f, 0f, 0f);
		gl.glScalef(1f, -1f, 1f);
		drawTexture(gl);
		gl.glPopMatrix();
	}

	private void drawTop(GL gl) {
		top.bind();
		top.enable();

		gl.glPushMatrix();
		gl.glRotatef(90f, 1f, 0f, 0f);
		gl.glScalef(1f, -1f, 1f);
		drawTexture(gl);
		gl.glPopMatrix();
	}

	private void drawRight(GL gl) {
		right.bind();
		right.enable();

		gl.glPushMatrix();
		gl.glRotatef(-90f, 0f, 1f, 0f);
		gl.glScalef(1f, -1f, 1f);
		drawTexture(gl);
		gl.glPopMatrix();
	}

	private void drawLeft(GL gl) {
		left.bind();
		left.enable();

		gl.glPushMatrix();
		gl.glRotatef(90f, 0f, 1f, 0f);
		gl.glScalef(1f, -1f, 1f);
		drawTexture(gl);
		gl.glPopMatrix();
	}

	private void drawBack(GL gl) {
		back.bind();
		back.enable();

		gl.glPushMatrix();
		gl.glRotatef(180f, 0f, 1f, 0f);
		gl.glScalef(1f, -1f, 1f);
		drawTexture(gl);
		gl.glPopMatrix();
	}

	private void drawFront(GL gl) {
		front.bind();
		front.enable();

		gl.glPushMatrix();
		gl.glScalef(1f, -1f, 1f);
		drawTexture(gl);
		gl.glPopMatrix();
	}

	private void drawTexture(GL gl) {
		gl.glBegin(GL.GL_QUADS);

		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d(1.0, 1.0, -1.0);

		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(-1.0, 1.0, -1.0);

		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(-1.0, -1.0, -1.0);

		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(1.0, -1.0, -1.0);

		gl.glEnd();
	}
}
