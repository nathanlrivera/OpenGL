package futureEngine.scene;

import java.util.ArrayList;
import java.util.Iterator;

import futureEngine.renderer.IRenderer;

public class Group extends SceneNode {

	private ArrayList<SceneNode> children;

	public Group() {
		children = new ArrayList<SceneNode>();
	}

	public void addChild(SceneNode node) {
		node.setParent(this);
		children.add(node);
	}

	public void removeChild(SceneNode node) {
		children.remove(node);
		node.setParent(null);
	}

	public Iterator<SceneNode> getChildren() {
			return children.iterator();
	}

	@Override
	public void draw(IRenderer renderer) {
		Iterator<SceneNode> sceneIterator = getChildren();
		SceneNode child;
		while (sceneIterator.hasNext()) {
			child = sceneIterator.next();
			child.draw(renderer);
		}
	}

	@Override
	protected void updateTransforms(long time) {
		super.updateTransforms(time);

		Iterator<SceneNode> sceneIterator = getChildren();
		SceneNode child;
		while (sceneIterator.hasNext()) {
			child = sceneIterator.next();
			child.updateGeometricState(time, false);
		}
	}

	@Override
	public void setLocalBound(BoundingVolume bv) {
		Iterator<SceneNode> sceneIterator = getChildren();
		SceneNode child;
		while (sceneIterator.hasNext()) {
			child = sceneIterator.next();
			child.setLocalBound(bv);
		}
	}

	@Override
	public void updateLocalBound() {
		Iterator<SceneNode> sceneIterator = getChildren();
		SceneNode child;
		while (sceneIterator.hasNext()) {
			child = sceneIterator.next();
			child.updateLocalBound();
		}
	}

	@Override
	public void updateWorldBound() {
		this.worldBound = null;
		Iterator<SceneNode> sceneIterator = getChildren();
		SceneNode child;
		while (sceneIterator.hasNext()) {
			child = sceneIterator.next();
			BoundingVolume childBV = child.getWorldBound();
			worldBound = childBV.merge(this.worldBound);
		}
	}
}
