package futureEngine.scene;

import java.util.Iterator;

import graphicslib3D.Matrix3D;

public class RotationController extends Controller {

	@Override
	void update(long time) {
		Iterator<SceneNode> iterator = getControlledNodes();
		SceneNode n;
		while (iterator.hasNext()) {
			n = iterator.next();
			Matrix3D rot = n.getLocalRotation();
			rot.rotateY(time / 300000000d);
			n.setLocalRotation(rot);
		}
	}

}
