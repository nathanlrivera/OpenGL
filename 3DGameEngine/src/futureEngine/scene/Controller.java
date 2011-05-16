package futureEngine.scene;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Controller {
	private ArrayList<SceneNode> controllees = new ArrayList<SceneNode>();
	
	public void addControlledNode(SceneNode node) {
		controllees.add(node);
	}
	
	public void removeControlledNode(SceneNode node) {
		controllees.remove(node);
	}
	
	public Iterator<SceneNode> getControlledNodes() {
		return controllees.iterator();
	}
	
	abstract void update(long time);

}
