package apps.games.treasureHunt;

import java.io.IOException;
import java.util.Random;

import futureEngine.renderer.IRenderer;
import futureEngine.scene.MS3DModel;
import graphicslib3D.Matrix3D;

public class Shape1 extends MS3DModel {

	public Shape1() {
		try {
			load("Shape1.ms3d");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setRenderQueueMode(IRenderer.RENDERMODE_OPAQUE);
		setLocalBound(getBoundingSphere());
		
		Random r = new Random();
		Matrix3D loc = new Matrix3D();
		loc.translate(r.nextInt(60) - 30, r.nextInt(60) - 30, r.nextInt(60) - 30);
		setLocalTranslation(loc);
	}
}
