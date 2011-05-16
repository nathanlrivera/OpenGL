package futureEngine.scene;

public class MS3DGroup {
	private byte flags;
	private String groupName;
	private short numTriangles;
	private short[] triangleIndices;
	private short materialIndex;
	/**
	 * @param flags the flags to set
	 */
	public void setFlags(byte flags) {
		this.flags = flags;
	}
	/**
	 * @return the flags
	 */
	public byte getFlags() {
		return flags;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param numTriangles the numTriangles to set
	 */
	public void setNumTriangles(short numTriangles) {
		this.numTriangles = numTriangles;
	}
	/**
	 * @return the numTriangles
	 */
	public short getNumTriangles() {
		return numTriangles;
	}
	/**
	 * @param triangleIndexes the triangleIndexes to set
	 */
	public void setTriangleIndices(short[] triangleIndices) {
		this.triangleIndices = triangleIndices;
	}
	/**
	 * @return the triangleIndexes
	 */
	public short[] getTriangleIndices() {
		return triangleIndices;
	}
	/**
	 * @param materialIndex the materialIndex to set
	 */
	public void setMaterialIndex(short materialIndex) {
		this.materialIndex = materialIndex;
	}
	/**
	 * @return the materialIndex
	 */
	public short getMaterialIndex() {
		return materialIndex;
	}
}
