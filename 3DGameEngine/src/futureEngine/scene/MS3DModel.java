package futureEngine.scene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

import futureEngine.renderer.IRenderer;
import graphicslib3D.Point3D;

public class MS3DModel extends Leaf {
	private MS3DVertex[] vertices;
	private MS3DTriangle[] triangles;
	private MS3DGroup[] groups;
	//private MS3DMaterial materials;
	
	public BoundingSphere getBoundingSphere() {
		Point3D[] points = new Point3D[vertices.length];
		for (int i = 0; i < vertices.length; ++i) {
			float[] geom = vertices[i].getGeometry();
			Point3D point = new Point3D(geom[0], geom[1], geom[2]);
			points[i] = point;
		}
		return new BoundingSphere(points);
	}
	
	public boolean load(String modelFileName) throws IOException {
		//create mapped byte buffer for the model file
		FileInputStream inFile = new FileInputStream(new File(modelFileName));
		long fileSize = inFile.getChannel().size();
		MappedByteBuffer inBuf = inFile.getChannel().map(MapMode.READ_ONLY, 0, fileSize);
		inBuf.order(ByteOrder.nativeOrder());
		
		boolean ok = readFileHeader(inBuf);		//read the MS3D file header
		setVertices(loadVertices(inBuf));			//get an array of vertices from the file
		setTriangles(loadTriangles(inBuf));		//get an array of faces (triangles) from the file
		setGroups(loadGroups(inBuf));			//get an array of groups from the file
		//materials = loadMaterials(inBuf);		//get an array of materials from the file
		
		//return (ok && vertices!=null && triangles != null && groups != null && materials != null);
		return (ok && getVertices() != null && getTriangles() != null && getGroups() != null);
	}

	private MS3DGroup[] loadGroups(MappedByteBuffer inBuf) {
		short numGroups = inBuf.getShort();
		MS3DGroup[] grps = new MS3DGroup[numGroups];
		
		//load each vertex into the verts array
		for (int i = 0; i < numGroups; ++i) {
			//read the file values for the vertex:
			byte flags = inBuf.get();

            byte [] bytes = new byte[32];
            inBuf.get(bytes);
            int len;
            for (len = 1; bytes[len] != '\0'; ++len);            
            byte [] theString = new byte[len];
            System.arraycopy(theString, 0, bytes, 0, len);
            String groupName = new String(bytes);
            
            short numTriangles = inBuf.getShort();
            short[] triangleIndices = new short[numTriangles];
            for (int j = 0; j < numTriangles; ++j) {
            	triangleIndices[j] = inBuf.getShort();
            }
            
            byte matIndex = inBuf.get();
			
			//build a new MS3D vertex object containing the file values
			MS3DGroup grp = new MS3DGroup();
			grp.setFlags(flags);
			grp.setGroupName(groupName);
			grp.setNumTriangles(numTriangles);
			grp.setTriangleIndices(triangleIndices);
			grp.setMaterialIndex(matIndex);
			
			grps[i] = grp;		//ad the new vert to the array
		}
		return grps;
	}

	private MS3DTriangle[] loadTriangles(MappedByteBuffer inBuf) {
		short numTriangles = inBuf.getShort();				//get # of vertices in the file
		MS3DTriangle[] tris = new MS3DTriangle[numTriangles]; //array to be returned
		
		//load each vertex into the verts array
		for (int i = 0; i < numTriangles; ++i) {
			//read the file values for the vertex:
			short flags = inBuf.getShort();
			short[] vertexIndices = new short[3];
			for (int j = 0; j < 3; ++j) {
				vertexIndices[j] = inBuf.getShort();
			}
			float[][] vertexNormals = new float[3][];
			for (int x = 0; x < 3; ++x) {
				float[] normal = new float[3];
				for (int y = 0; y < 3; ++y) {
					normal[y] = inBuf.getFloat();
				}
				vertexNormals[x] = normal;
			}
			float[] sTexCoord = new float[3];
            for (int s = 0; s < 3; ++s) {
            	sTexCoord[s] = inBuf.getFloat();
            }
			float[] tTexCoord = new float[3];
            for (int t = 0; t < 3; ++t) {
            	tTexCoord[t] = inBuf.getFloat();
            }
			
			//skip the last two bytes
			inBuf.get();
			inBuf.get();
			
			//build a new MS3D vertex object containing the file values
			MS3DTriangle tri = new MS3DTriangle();
			tri.setFlags(flags);
			tri.setVertexIndices(vertexIndices);
			tri.setVertexNormals(vertexNormals);
			tri.setSTexCoord(sTexCoord);
			tri.setTTexCoord(tTexCoord);
			tris[i] = tri;		//ad the new vert to the array
		}
		return tris;
	}

	private boolean readFileHeader(MappedByteBuffer inBuf) {
		//read 10 bytes from the mapped byte buffer into "header"
		byte[] header = new byte[10];
		inBuf.get(header);
		
		//verify that the 10-byte header = "MS3D000000"
		String headerString = new String(header);
		if (!headerString.equals("MS3D000000")) {
			return false;
		}
		
		//read the MS3D file format version number from the mapped byte buffer
		int versionNum = inBuf.getInt();
		
		//verify that the file format version number is 4
		if (versionNum != 4) {
			return false;
		}
		
		return true;		//valid file header found
	}
	
	private MS3DVertex[] loadVertices(MappedByteBuffer inBuf) {
		short numVertices = inBuf.getShort();				//get # of vertices in the file
		MS3DVertex[] verts = new MS3DVertex[numVertices]; //array to be returned
		
		//load each vertex into the verts array
		for (int i = 0; i < numVertices; ++i) {
			//read the file values for the vertex:
			byte flags = inBuf.get();
			float[] geom = new float[3];
			for (int j = 0; j < 3; ++j) {
				geom[j] = inBuf.getFloat();
			}
			byte boneID = inBuf.get();
			byte refCount = inBuf.get();
			
			//build a new MS3D vertex object containing the file values
			MS3DVertex vert = new MS3DVertex();
			vert.setFlags(flags);
			vert.setGeometry(geom);
			vert.setBoneID(boneID);
			vert.setRefCount(refCount);
			
			verts[i] = vert;		//ad the new vert to the array
		}
		return verts;
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.draw(this);
	}

	/**
	 * @param vertices the vertices to set
	 */
	public void setVertices(MS3DVertex[] vertices) {
		this.vertices = vertices;
	}

	/**
	 * @return the vertices
	 */
	public MS3DVertex[] getVertices() {
		return vertices;
	}

	/**
	 * @param triangles the triangles to set
	 */
	public void setTriangles(MS3DTriangle[] triangles) {
		this.triangles = triangles;
	}

	/**
	 * @return the triangles
	 */
	public MS3DTriangle[] getTriangles() {
		return triangles;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(MS3DGroup[] groups) {
		this.groups = groups;
	}

	/**
	 * @return the groups
	 */
	public MS3DGroup[] getGroups() {
		return groups;
	}
}
