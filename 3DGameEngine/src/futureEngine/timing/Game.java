package futureEngine.timing;

public abstract class Game {
	public abstract void start();

	protected abstract void initSystem();

	protected abstract void initGame();

	protected abstract void mainLoop();

	protected abstract void update();

	protected abstract void render();

	protected abstract void setGameOver(boolean gameState);

	protected abstract boolean isGameOver();

	protected abstract void shutDown();

	protected abstract void exit();
}
