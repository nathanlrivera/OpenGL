package futureEngine.scene;

import graphicslib3D.Matrix3D;

public abstract class Leaf extends SceneNode {
	private BoundingVolume localBound;
	
	public void updateWorldBound() {
		Matrix3D worldTransform = new Matrix3D();
		worldTransform.concatenate(getWorldTranslation());
		worldTransform.concatenate(getWorldRotation());
		worldTransform.concatenate(getWorldScale());
		worldBound = this.localBound.transformBy(worldTransform);
	}
	
	public void updateLocalBound() {
		// Calculate local bound from model data
	}
	
	public void setLocalBound(BoundingVolume newBound) {
		localBound = newBound;
	}
}
