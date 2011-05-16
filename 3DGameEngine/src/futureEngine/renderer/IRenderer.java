package futureEngine.renderer;

import java.awt.Canvas;

import apps.games.treasureHunt.Box;
import apps.games.treasureHunt.Crystal;
import apps.games.treasureHunt.Score;
import futureEngine.camera.ICamera;
import futureEngine.scene.MS3DModel;
import futureEngine.scene.SceneNode;
import futureEngine.scene.SkyBox;

public interface IRenderer {

	public final int RENDERMODE_OPAQUE = 0;
	public final int RENDERMODE_TRANSPARENT = 1;
	public final int RENDERMODE_ORTHO = 2;

	public void addToRenderQueue(SceneNode s, int renderMode);
	public void addToRenderQueue(SceneNode s);
	public void processRenderQueue();
	public void clearRenderQueue();

	public ICamera getCamera();

	public void enableOrthoMode(boolean enable);
	public boolean isInOrthoMode();

	public Canvas getCanvas();

	public void draw(Score score);

	public void setSkyBox(SkyBox sky);
	
	public long getPrimitiveCount();
	public void draw(Crystal crystal);
	public void draw(Box box);
	public void draw(MS3DModel ms3dModel);
}
