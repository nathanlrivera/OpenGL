package futureEngine.scene;
/*
 * This class describes a model vertex in an MS3D file.
 */
public class MS3DVertex {
	private byte flags;
	private float[] geometry = new float[3];		//vertex XYZ geometry values
	private byte boneID = -1;
	private byte refCount;
	
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
	 * @param geometry the geometry to set
	 */
	public void setGeometry(float[] geometry) {
		this.geometry = geometry;
	}
	/**
	 * @return the geometry
	 */
	public float[] getGeometry() {
		return geometry;
	}
	/**
	 * @param boneID the boneID to set
	 */
	public void setBoneID(byte boneID) {
		this.boneID = boneID;
	}
	/**
	 * @return the boneID
	 */
	public byte getBoneID() {
		return boneID;
	}
	/**
	 * @param refCount the refCount to set
	 */
	public void setRefCount(byte refCount) {
		this.refCount = refCount;
	}
	/**
	 * @return the refCount
	 */
	public byte getRefCount() {
		return refCount;
	}
}
