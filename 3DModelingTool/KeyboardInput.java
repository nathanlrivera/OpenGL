package a5;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardInput extends KeyAdapter
{
	private Camera myCamera;

	/**
	 * @param canvas The GLCanvas that the user will click/drag on.
	 */
	public KeyboardInput(Camera camera)
	{
		myCamera = camera;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(!myCamera.getRelativeMode())
			return;
		double increment = 5.0;
		double angleIncrement = 2.5;
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_A:
				myCamera.changeX(-increment);
				break;
			case KeyEvent.VK_D:
				myCamera.changeX(increment);
				break;
			case KeyEvent.VK_W:
				myCamera.changeY(-increment);
				break;
			case KeyEvent.VK_S:
				myCamera.changeY(increment);
				break;
			case KeyEvent.VK_Q:
				myCamera.changeZ(-increment);
				break;
			case KeyEvent.VK_E:
				myCamera.changeZ(increment);
				break;
			case KeyEvent.VK_UP:
				myCamera.changePitch(-angleIncrement);
				break;
			case KeyEvent.VK_DOWN:
				myCamera.changePitch(angleIncrement);
				break;
			case KeyEvent.VK_LEFT:
				myCamera.changeYaw(-angleIncrement);
				break;
			case KeyEvent.VK_RIGHT:
				myCamera.changeYaw(angleIncrement);
				break;
			case KeyEvent.VK_INSERT:
				myCamera.changeRoll(-angleIncrement);
				break;
			case KeyEvent.VK_PAGE_UP:
				myCamera.changeRoll(angleIncrement);
				break;
		}
	}
}
