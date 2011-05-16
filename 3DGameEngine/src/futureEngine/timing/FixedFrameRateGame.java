package futureEngine.timing;

public abstract class FixedFrameRateGame extends Game {

	private boolean gameOver;
	private int frameCount;
	private long startTime;
	private int desiredFPS;
	protected long desiredFrameDuration;

	@Override
	public final void start() {
		initSystem();
		initGame();
		mainLoop();
		shutDown();
		exit();
	}

	@Override
	protected final void mainLoop() {
		long frameStartTime, frameEndTime, frameDuration, sleepTimeMSec;
		while (!isGameOver()) {
			frameStartTime = System.nanoTime();
			update();
			render();
			++frameCount;
			frameEndTime = System.nanoTime();
			frameDuration = frameEndTime - frameStartTime;
			while (frameDuration < desiredFrameDuration) {
				sleepTimeMSec = (desiredFrameDuration - frameDuration) / 1000000;
				try {
					Thread.sleep(sleepTimeMSec);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				frameDuration = System.nanoTime() - frameStartTime;
			}
			Thread.yield();
		}
	}

	@Override
	protected final void setGameOver(boolean gameState) {
		gameOver = gameState;
	}

	@Override
	protected final boolean isGameOver() {
		return gameOver;
	}

	@Override
	protected void exit() {
		System.exit(0);
	}

	public final void setMaxFrameRate(int rate) {
		desiredFPS = rate;
		desiredFrameDuration = 1000000000 / desiredFPS;
	}

	public final float getFramesPerSec() {
		long currentTime = System.nanoTime();
		float elapsedSeconds = (currentTime - startTime) / (float) 1000000000L;
		float fps = frameCount / elapsedSeconds;
		startTime = currentTime;
		frameCount = 0;
		return fps;
	}
}
