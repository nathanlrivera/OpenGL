package futureEngine.displaySystem;

import futureEngine.renderer.IRenderer;

public interface IDisplaySystem {
	int getWidth();

	int getHeight();

	int getBitDepth();

	int getRefreshRate();

	void setWidth(int width);

	void setHeight(int height);

	void setBitDepth(int numBits);

	void setRefreshRate(int rate);

	void setTitle(String title);

	IRenderer getRenderer();

	boolean isRealized();

	void close();
}
