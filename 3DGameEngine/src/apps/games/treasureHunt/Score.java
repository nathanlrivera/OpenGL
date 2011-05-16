package apps.games.treasureHunt;

import futureEngine.renderer.IRenderer;
import futureEngine.scene.Leaf;
import futureEngine.scene.NonCullableBoundingVolume;

public class Score extends Leaf {
	
	private String score;
	
	public Score()
	{
		setRenderQueueMode(IRenderer.RENDERMODE_ORTHO);
		NonCullableBoundingVolume bv = new NonCullableBoundingVolume();
		setLocalBound(bv);
	}
	
	public void setScore(String newScore)
	{
		score = newScore;
	}
	
	public String getScore()
	{
		return score;
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.draw(this);

	}

}
