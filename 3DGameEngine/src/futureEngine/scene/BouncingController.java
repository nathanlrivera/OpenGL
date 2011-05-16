package futureEngine.scene;

import java.util.Iterator;

import graphicslib3D.Matrix3D;

public class BouncingController extends Controller {
	private int direction;
	private double totalAmount;
	
	public BouncingController() {
		direction = 1;
		totalAmount = 0;
	}
	
	@Override
	void update(long time) {
		double amount = (time / 500000000d) * direction;
		totalAmount += amount;
		
		Iterator<SceneNode> iterator = getControlledNodes();
		SceneNode n;
		while (iterator.hasNext()) {
			n = iterator.next();
			Matrix3D trans = n.getLocalTranslation();
			trans.setElementAt(1, 3, amount + trans.elementAt(1, 3));
			n.setLocalTranslation(trans);
		}
		
		if (totalAmount >= 1)
			direction = -1;
		else if (totalAmount <= 0)
			direction = 1;
	}

}
