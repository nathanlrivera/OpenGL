package futureEngine.scene;

public class MS3DTriangle {
	private short flags;
	private short[] vertexIndices;
	private float[][] vertexNormals;
	private float[] sTexCoord;
	private float[] tTexCoord;
	/**
	 * @param flags the flags to set
	 */
	public void setFlags(short flags) {
		this.flags = flags;
	}
	/**
	 * @return the flags
	 */
	public short getFlags() {
		return flags;
	}
	/**
	 * @param vertexIndices the vertexIndices to set
	 */
	public void setVertexIndices(short[] vertexIndices) {
		this.vertexIndices = vertexIndices;
	}
	/**
	 * @return the vertexIndices
	 */
	public short[] getVertexIndices() {
		return vertexIndices;
	}
	/**
	 * @param vertexNormals the vertexNormals to set
	 */
	public void setVertexNormals(float[][] vertexNormals) {
		this.vertexNormals = vertexNormals;
	}
	/**
	 * @return the vertexNormals
	 */
	public float[][] getVertexNormals() {
		return vertexNormals;
	}
	/**
	 * @param sTexCoord the sTexCoord to set
	 */
	public void setSTexCoord(float[] sTexCoord) {
		this.sTexCoord = sTexCoord;
	}
	/**
	 * @return the sTexCoord
	 */
	public float[] getSTexCoord() {
		return sTexCoord;
	}
	/**
	 * @param tTexCoord the tTexCoord to set
	 */
	public void setTTexCoord(float[] tTexCoord) {
		this.tTexCoord = tTexCoord;
	}
	/**
	 * @return the tTexCoord
	 */
	public float[] getTTexCoord() {
		return tTexCoord;
	}
}
