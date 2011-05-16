package apps.games.treasureHunt;

import java.util.Random;

import futureEngine.scene.Group;
import graphicslib3D.Matrix3D;

public class Treasure extends Group {
	
	public Treasure()
	{
		Random r = new Random();
		Matrix3D loc = new Matrix3D();
		loc.translate(r.nextInt(60) - 30, r.nextInt(60) - 30, r.nextInt(60) - 30);
		setLocalTranslation(loc);
		
		Crystal c = new Crystal();
		byte[] crystalColor = new byte[3];
		r.nextBytes(crystalColor);
		c.setColor(crystalColor);
		
		byte[] boxColor = new byte[3];
		r.nextBytes(boxColor);
		Box b = new Box();
		b.setColor(boxColor);
		
		addChild(c);
		addChild(b);
	}
}
