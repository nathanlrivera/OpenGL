package apps.games.treasureHunt;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import futureEngine.camera.ICamera;
import futureEngine.displaySystem.DisplaySystem;
import futureEngine.displaySystem.IDisplaySystem;
import futureEngine.renderer.IRenderer;
import futureEngine.scene.BouncingController;
import futureEngine.scene.BoundingSphere;
import futureEngine.scene.Group;
import futureEngine.scene.RotationController;
import futureEngine.timing.FixedFrameRateGame;
import graphicslib3D.DisplaySettingsDialog;
import graphicslib3D.Point3D;

public class TreasureHunt extends FixedFrameRateGame {

	private IDisplaySystem display;
	private IRenderer renderer;
	private GraphicsDevice device;
	private Group rootNode;
	private ICamera camera;
	private String[] arguments;
	private Vector<Treasure> treasures;
	private Score theScore;
	private int totalTreasures;
	private int foundTreasures;
	private long currentScore;
	private long lastUpdate;
	private long treasureTime;
	private long scoreTime;
	private int treasureType;

	public TreasureHunt(String[] args) {
		arguments = args;
		treasures = new Vector<Treasure>();
	}

	@Override
	protected void initSystem() {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		if (arguments.length > 0 && arguments[0].equals("fullscreen")) {
			display = new DisplaySystem(device.getDisplayMode(), true, "JOGLRenderer");
		} else {
			DisplaySettingsDialog settings = new DisplaySettingsDialog(device);
			settings.showIt();
			display = new DisplaySystem(settings.getSelectedDisplayMode(),
					settings.isFullScreenModeSelected(), "JOGLRenderer");
		}

		renderer = display.getRenderer();
		camera = renderer.getCamera();
		renderer.setSkyBox(new Sky());
	}

	@Override
	protected void initGame() {
		loopSoundFile("music.wav");
		rootNode = new Group();
		theScore = new Score();
		rootNode.addChild(theScore);
		addTreasure();

		Shape1 node = new Shape1();
		node.addController(new RotationController());
		node.addController(new BouncingController());
		rootNode.addChild(node);
		
		Shape2 node2 = new Shape2();
		node2.addController(new RotationController());
		node2.addController(new BouncingController());
		rootNode.addChild(node2);
		
		rootNode.updateGeometricState(0, true);
		updateTime();
		updateScore();
	}
	
	private long updateTime()
	{
		long temp = lastUpdate;
		lastUpdate = System.nanoTime();
		return lastUpdate - temp;
	}

	private void addTreasure() {
		Treasure newTreasure = new Treasure();	
		switch (treasureType) {
		case 0:
			break;
		case 1:
			newTreasure.addController(new RotationController());
			break;
		case 2:
			newTreasure.addController(new BouncingController());
			break;
		case 3:
			newTreasure.addController(new RotationController());
			newTreasure.addController(new BouncingController());
			break;
		}
		treasureType += 1;
		if (treasureType == 4)
			treasureType = 0;
		treasures.add(newTreasure);
		rootNode.addChild(newTreasure);
		++totalTreasures;
		playSoundFile("add.wav");
		treasureTime = 0;
	}

	@Override
	protected void update() {
		long time = updateTime();
		camera.update(time);
		
		Point3D player = camera.getLocation();
		Iterator<Treasure> iter = treasures.iterator();
		while (iter.hasNext()) {
			Treasure t = iter.next();
			BoundingSphere sphere = (BoundingSphere)t.getWorldBound();
			if (sphere.contains(player)) {
				iter.remove();
				rootNode.removeChild(t);
				++foundTreasures;
				
				updateScore();
				if (currentScore == 100)
					playSoundFile("100.wav");
				else
					playSoundFile("pickup.wav");
			}			
		}
		
		treasureTime += time;
		if (treasureTime >= 7000000000l) {
			addTreasure();
		}
		
		scoreTime += time;
		if (scoreTime >= 500000000l) {
			updateScore();
		}

		rootNode.updateGeometricState(time, true);
	}

	private void updateScore() {
		currentScore = Math.round((100.0 * foundTreasures) / totalTreasures);
		theScore.setScore("Picked up " + foundTreasures + "/" + totalTreasures 
				+ " treasures. The score is: " + currentScore 
				+ ". # of Primitives: " + renderer.getPrimitiveCount()
				+ ". FPS: " + getFramesPerSec());
		scoreTime = 0;
	}

	@Override
	protected void render() {
		renderer.addToRenderQueue(rootNode);
		renderer.processRenderQueue(); // calls canvas.display()
		renderer.clearRenderQueue();
	}

	@Override
	protected void shutDown() {

	}

	@Override
	protected void exit() {
		display.close();
	}
	
	private void playSoundFile(String fileName)
	{
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loopSoundFile(String fileName)
	{
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
