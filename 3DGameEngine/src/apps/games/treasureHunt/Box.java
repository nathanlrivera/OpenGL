package apps.games.treasureHunt;

import futureEngine.renderer.IRenderer;
import futureEngine.scene.BoundingSphere;
import futureEngine.scene.Leaf;
import graphicslib3D.Point3D;

public class Box extends Leaf {
	
	private byte[] myColor;
	
	public Box() {
		setRenderQueueMode(IRenderer.RENDERMODE_OPAQUE);
		setLocalBound(new BoundingSphere(new Point3D(0,0,0), 1.0));
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.draw(this);

	}
	
	/**
	 * @param myColor the myColor to set
	 */
	public void setColor(byte[] myColor) {
		this.myColor = myColor;
	}

	/**
	 * @return the myColor
	 */
	public byte[] getColor() {
		return myColor;
	}

}
