package futureEngine.scene;

public class SkyBox {
	
	public String frontPath, backPath, leftPath, rightPath, topPath, bottomPath;
	
	public String getFront() {
		return frontPath;
	}
	
	public void setFront(String fileName) {
		frontPath = fileName;
	}

	public String getBack() {
		return backPath;
	}

	public void setBack(String fileName) {
		backPath = fileName;
	}
	
	public String getLeft() {
		return leftPath;
	}

	public void setLeft(String fileName) {
		leftPath = fileName;
	}
	
	public String getRight() {
		return rightPath;
	}

	public void setRight(String fileName) {
		rightPath = fileName;
	}
	
	public String getTop() {
		return topPath;
	}

	public void setTop(String fileName) {
		topPath = fileName;
	}
	
	public String getBottom() {
		return bottomPath;
	}

	public void setBottom(String fileName) {
		bottomPath = fileName;
	}
}
