package futureEngine.displaySystem;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import futureEngine.camera.ICamera;
import futureEngine.renderer.IRenderer;
import futureEngine.renderer.joglRenderer.JOGLRenderer;

public class DisplaySystem extends JFrame implements IDisplaySystem,
		KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private int prevMouseX;
	private int prevMouseY;
	private Robot robot; // variables for tracking mouse mode
	private Point centerLocation;
	private boolean isRecentering;
	private GraphicsDevice device;
	private ICamera theCamera;
	private IRenderer myRenderer;
	private boolean fullScreen;

	public DisplaySystem(DisplayMode displayMode, boolean isFullScreen,
			String rendererClass) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myRenderer = new JOGLRenderer();
		// add the canvas to the JFrame and make it visible
		Canvas theCanvas = myRenderer.getCanvas();
		this.getContentPane().add(theCanvas);
		theCamera = myRenderer.getCamera();

		// get default screen device out of the local graphics environment
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		fullScreen = isFullScreen;

		if (fullScreen) {
			setFullScreenMode(device.getDisplayMode());
			initMouseMode();
		} else {
			setSize(displayMode.getWidth(), displayMode.getHeight());
			setLocationRelativeTo(null);
		}

		this.setVisible(true);

		theCanvas.addKeyListener(this);
		theCanvas.addMouseListener(this);
		theCanvas.addMouseMotionListener(this);
		theCanvas.setFocusable(true);
		theCanvas.requestFocus();
	}

	private void initMouseMode() {
		centerLocation = new Point();
		isRecentering = false;
		try {
			robot = new Robot();
			recenterMouse();
		} catch (AWTException ex) {
			System.out.println("Couldn't create Robot!");
		}
	}

	/** Uses the Robot class to position the mouse in the center of the screen. */
	private void recenterMouse() {
		Window window = device.getFullScreenWindow();
		if (robot != null && window.isShowing()) {
			centerLocation.x = window.getWidth() / 2;
			centerLocation.y = window.getHeight() / 2;
			SwingUtilities.convertPointToScreen(centerLocation, window);
			isRecentering = true;
			robot.mouseMove(centerLocation.x, centerLocation.y);
		}
	}

	private void gameShutdown() {
		// restore device to non-fullscreen state
		if (device != null) {
			Window window = device.getFullScreenWindow();
			if (window != null) {
				window.dispose();
			}
			device.setFullScreenWindow(null);
		}
	}

	/**
	 * Set the screen to full screen exclusive mode (FSEM) with the specified
	 * values.
	 */
	private void setFullScreenMode(DisplayMode dispMode) {
		if (device.isFullScreenSupported()) {
			setUndecorated(true); // suppress title bar, borders, etc.
			setResizable(false); // full-screen so not resizeable
			setIgnoreRepaint(true); // ignore AWT repaints if using active
			// rendering
			// Put device in full-screen mode (must be done BEFORE changing
			// DisplayMode)
			device.setFullScreenWindow(this);
			// try to set the full-screen device DisplayMode
			if (dispMode != null && device.isDisplayChangeSupported()) {
				try {
					device.setDisplayMode(dispMode);
					this.setSize(dispMode.getWidth(), dispMode.getHeight());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				Toolkit tk = Toolkit.getDefaultToolkit();
				Cursor invisibleCursor = tk.createCustomCursor(tk.getImage(""),
						new Point(), "InvisibleCursor");
				setCursor(invisibleCursor);
			} else {
				System.err.println("Cannot set screen display mode");
			}
		} else {
			System.err.println("Full-Screen Exclusive Mode not supported");
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBitDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRefreshRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IRenderer getRenderer() {
		return myRenderer;
	}

	@Override
	public boolean isRealized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBitDepth(int numBits) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHeight(int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRefreshRate(int rate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			gameShutdown();
			System.exit(0);
			break;
		case KeyEvent.VK_W:
			theCamera.setForwardMotion(-1.0);
			break;
		case KeyEvent.VK_S:
			theCamera.setForwardMotion(1.0);
			break;
		case KeyEvent.VK_A:
			theCamera.setStrafeMotion(-1.0);
			break;
		case KeyEvent.VK_D:
			theCamera.setStrafeMotion(1.0);
			break;
		case KeyEvent.VK_LEFT:
			theCamera.yaw(-5.0);
			break;
		case KeyEvent.VK_RIGHT:
			theCamera.yaw(5.0);
			break;
		case KeyEvent.VK_UP:
			theCamera.pitch(5.0);
			break;
		case KeyEvent.VK_DOWN:
			theCamera.pitch(-5.0);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			theCamera.setForwardMotion(0.0);
			break;
		case KeyEvent.VK_S:
			theCamera.setForwardMotion(0.0);
			break;
		case KeyEvent.VK_A:
			theCamera.setStrafeMotion(0.0);
			break;
		case KeyEvent.VK_D:
			theCamera.setStrafeMotion(0.0);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		prevMouseX = e.getX();
		prevMouseY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int curX = e.getX(); // get current mouse loc
		int curY = e.getY(); // get current mouse loc
		Dimension size = e.getComponent().getSize(); // get size of frame
		// determine drag amount as a signed percentage of frame width
		double dragAmountX = (double) (curX - prevMouseX) / (double) size.width;
		double dragAmountY = (double) (curY - prevMouseY) / (double) size.height;
		theCamera.yaw(dragAmountX * 360.0);
		theCamera.pitch(dragAmountY * 360.0);
		// save current mouse loc for next drag
		prevMouseX = curX;
		prevMouseY = curY;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!fullScreen)
			return;

		// if this event is from re-centering the mouse, ignore it
		if (isRecentering && centerLocation.x == e.getX() && centerLocation.y == e.getY()) {
			isRecentering = false;
		} else {
			int x = e.getX(); // it’s a real move; get current mouse loc
			int y = e.getY();
			// ...code here to calculate camera changes based on mouse distance
			// from center
			Dimension size = e.getComponent().getSize(); // get size of frame
			// determine drag amount as a signed percentage of frame width
			double mouseDeltaX = (double) (x - centerLocation.x)
					/ (double) size.width;
			double mouseDeltaY = (double) (y - centerLocation.y)
					/ (double) size.height;
			theCamera.yaw(Math.toDegrees(mouseDeltaX * 2 * Math.PI));
			theCamera.pitch(Math.toDegrees(mouseDeltaY * 2 * Math.PI));
			recenterMouse();
		}

	}

}
