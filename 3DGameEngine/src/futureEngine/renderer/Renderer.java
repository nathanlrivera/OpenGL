package futureEngine.renderer;

import java.util.ArrayList;
import java.util.Iterator;

import futureEngine.camera.Camera;
import futureEngine.camera.ICamera;
import futureEngine.scene.BoundingVolume;
import futureEngine.scene.Group;
import futureEngine.scene.SceneNode;

public abstract class Renderer implements IRenderer {

	protected ArrayList<SceneNode> opaqueQueue;
	protected ArrayList<SceneNode> transparentQueue;
	protected ArrayList<SceneNode> orthoQueue;
	protected ICamera myCamera = new Camera();

	public void addToRenderQueue(SceneNode n) {
		if (n instanceof Group) {
			Group g = (Group) n;
			Iterator<SceneNode> sceneIterator = g.getChildren();
			SceneNode child;
			while (sceneIterator.hasNext()) {
				child = sceneIterator.next();
				addToRenderQueue(child);
			}
		} else {
			int mode = n.getRenderQueueMode();
			addToRenderQueue(n, mode);
		}
	}

	public void addToRenderQueue(SceneNode s, int renderMode) {
		switch (renderMode) {
		case RENDERMODE_OPAQUE:
			opaqueQueue.add(s);
			break;
		case RENDERMODE_TRANSPARENT:
			transparentQueue.add(s);
			break;
		case RENDERMODE_ORTHO:
			orthoQueue.add(s);
			break;
		default:
			throw new RuntimeException("Illegal render mode");
		}
	}

	public void clearRenderQueue() {
		opaqueQueue.clear();
		transparentQueue.clear();
		orthoQueue.clear();
	}

	private void drawQueue(ArrayList<SceneNode> queue) {
		Iterator<SceneNode> sceneIterator = queue.iterator();
		SceneNode node;
		while (sceneIterator.hasNext()) {
			node = sceneIterator.next();
			if (!boundLiesOutsideViewFrustum(node.getWorldBound())) {
				node.draw(this);
			}
		}
	}

	protected void renderOrthographics() {
		// orthoQueue.sort(); //insure closest to camera is first
		this.enableOrthoMode(true); // set orthographic projection mode
		/*
		 * for (each node in orthoQueue) { node.draw(this); //tell node to draw
		 * itself using this renderer }
		 */
		drawQueue(orthoQueue);
		this.enableOrthoMode(false); // return to perspective projection
	}

	protected void renderOpaques() {
		// opaqueQueue.sort(); //insure closest-to-camera is first in list
		/*
		 * for (each node in opaqueQueue) { node draw(this); //tell node node.to
		 * draw itself using this renderer }
		 */
		drawQueue(opaqueQueue);
	}

	protected void renderTransparents() {
		// transparentQueue.sort(); // insure farthest from camera is first in
		// list
		/*
		 * for (each node in transparentQueue) { node.draw(this); // tell node
		 * to draw itself using this renderer }
		 */
		drawQueue(transparentQueue);
	}
	
	private boolean boundLiesOutsideViewFrustum(BoundingVolume bound) {
		return myCamera.intersects(bound);
	}
}
