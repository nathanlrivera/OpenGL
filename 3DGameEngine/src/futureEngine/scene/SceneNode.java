package futureEngine.scene;

import java.util.ArrayList;
import java.util.Iterator;

import futureEngine.renderer.IRenderer;
import graphicslib3D.Matrix3D;

public abstract class SceneNode {
	private String nodeName = "";
	private SceneNode parent;
	private Matrix3D localTranslation = new Matrix3D();
	private Matrix3D localScale = new Matrix3D();
	private Matrix3D localRotation = new Matrix3D();
	private int renderQueueMode;
	private Matrix3D worldTranslation = new Matrix3D();
	private Matrix3D worldScale = new Matrix3D();
	private Matrix3D worldRotation = new Matrix3D();
	private ArrayList<Controller> controllers = new ArrayList<Controller>();
	protected BoundingVolume worldBound;
	
	public BoundingVolume getWorldBound() {
		return worldBound;
	}
	
	abstract void setLocalBound(BoundingVolume bv);
	abstract void updateLocalBound();
	abstract void updateWorldBound();
	
	public void addController(Controller c) {
		controllers.add(c);
		c.addControlledNode(this);
	}
	
	public void removeController(Controller c) {
		c.removeControlledNode(this);
		controllers.remove(c);
	}
	
	public Iterator<Controller> getControllers() {
		return controllers.iterator();
	}

	/**
	 * @param nodeName
	 *            the nodeName to set
	 */
	public void setName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the nodeName
	 */
	public String getName() {
		return nodeName;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(SceneNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the parent
	 */
	public SceneNode getParent() {
		return parent;
	}

	/**
	 * @param localTranslation
	 *            the localTranslation to set
	 */
	public void setLocalTranslation(Matrix3D localTranslation) {
		this.localTranslation = localTranslation;
	}

	/**
	 * @return the localTranslation
	 */
	public Matrix3D getLocalTranslation() {
		return (Matrix3D) localTranslation.clone();
	}

	/**
	 * @param localScale
	 *            the localScale to set
	 */
	public void setLocalScale(Matrix3D localScale) {
		this.localScale = localScale;
	}

	/**
	 * @return the localScale
	 */
	public Matrix3D getLocalScale() {
		return (Matrix3D) localScale.clone();
	}

	/**
	 * @param localRotation
	 *            the localRotation to set
	 */
	public void setLocalRotation(Matrix3D localRotation) {
		this.localRotation = localRotation;
	}

	/**
	 * @return the localRotation
	 */
	public Matrix3D getLocalRotation() {
		return (Matrix3D) localRotation.clone();
	}

	/**
	 * @param renderQueueMode
	 *            the renderQueueMode to set
	 */
	public void setRenderQueueMode(int renderQueueMode) {
		this.renderQueueMode = renderQueueMode;
	}

	/**
	 * @return the renderQueueMode
	 */
	public int getRenderQueueMode() {
		return renderQueueMode;
	}

	public void translate(Matrix3D input) {
		localTranslation.concatenate(input);
	}

	public void rotate(Matrix3D input) {
		localRotation.concatenate(input);
	}

	public void scale(Matrix3D input) {
		localScale.concatenate(input);
	}

	public void updateGeometricState(long time, boolean isInitiator) {
		updateTransforms(time); //update this node's transforms
		updateWorldBound(); //update this node's world bound
		if (isInitiator) {
			propagateBoundToRoot();
		}
	}

	protected void updateTransforms(long time) {
		updateLocalTransforms(time); 
		updateWorldTransforms();
	}
	
	protected void propagateBoundToRoot() {
		if (parent != null) {
			parent.updateWorldBound();
			parent.propagateBoundToRoot();
		}
	}

	void updateWorldTransforms() {
		if (parent == null) return;
		
		Matrix3D newTrans = parent.getWorldTranslation();
		newTrans.concatenate(this.getLocalTranslation());
		this.setWorldTranslation(newTrans);

		Matrix3D newScale = parent.getWorldScale();
		newScale.concatenate(this.getLocalScale());
		this.setWorldScale(newScale);

		Matrix3D newRotate = parent.getWorldRotation();
		newRotate.concatenate(this.getLocalRotation());
		this.setWorldRotation(newRotate);
	}
	
	private void updateLocalTransforms(long time) {
		Iterator<Controller> iterator = getControllers();
		Controller c;
		while (iterator.hasNext()) {
			c = iterator.next();
			c.update(time);
		}
	}

	public abstract void draw(IRenderer renderer);

	/**
	 * @param worldTranslation
	 *            the worldTranslation to set
	 */
	public void setWorldTranslation(Matrix3D worldTranslation) {
		this.worldTranslation = worldTranslation;
	}

	/**
	 * @return the worldTranslation
	 */
	public Matrix3D getWorldTranslation() {
		return (Matrix3D) worldTranslation.clone();
	}

	/**
	 * @param worldScale
	 *            the worldScale to set
	 */
	public void setWorldScale(Matrix3D worldScale) {
		this.worldScale = worldScale;
	}

	/**
	 * @return the worldScale
	 */
	public Matrix3D getWorldScale() {
		return (Matrix3D) worldScale.clone();
	}

	/**
	 * @param worldRotation
	 *            the worldRotation to set
	 */
	public void setWorldRotation(Matrix3D worldRotation) {
		this.worldRotation = worldRotation;
	}

	/**
	 * @return the worldRotation
	 */
	public Matrix3D getWorldRotation() {
		return (Matrix3D) worldRotation.clone();
	}

}
