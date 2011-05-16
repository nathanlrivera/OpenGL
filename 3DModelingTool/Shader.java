package a5;

import java.io.Serializable;
import javax.media.opengl.GL;

public class Shader implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String[] vertexSourceCode;

	private String[] fragmentSourceCode;

	private int programID;

	private String name;

	private int[] vertexSourceLengths;

	private int[] fragmentSourceLengths;
	
	private String parameterName;
	
	private int parameterID;
	
	private float parameterValue;
	
	private Texture texture;
	
	public Shader()
	{
		parameterName = null;
		parameterValue = 0;
		texture = null;
		resetIDs();
	}
	
	public float getParameterValue()
	{
		return parameterValue;
	}

	public void setParameterValue(float parameterValue)
	{
		this.parameterValue = parameterValue;
	}

	public String getParameterName()
	{
		return parameterName;
	}

	public void setParameterName(String parameterName)
	{
		this.parameterName = parameterName;
	}
	
	public int getParameterID(GL gl)
	{
		checkInstallState(gl);
		return parameterID;
	}

	public void loadFromSource(String[] vertexSource, String[] fragmentSource)
	{
		vertexSourceCode = vertexSource;
		vertexSourceLengths = new int[vertexSourceCode.length];
		for(int i = 0; i < vertexSourceLengths.length; ++i)
			vertexSourceLengths[i] = vertexSourceCode[i].length();
		
		fragmentSourceCode = fragmentSource;
		fragmentSourceLengths = new int[fragmentSourceCode.length];
		for(int i = 0; i < fragmentSourceLengths.length; ++i)
			fragmentSourceLengths[i] = fragmentSourceCode[i].length();
	}

	public void install(GL gl)
	{
		int vertexShaderID = gl.glCreateShaderObjectARB(GL.GL_VERTEX_SHADER_ARB);
		int fragmentShaderID = gl.glCreateShaderObjectARB(GL.GL_FRAGMENT_SHADER_ARB);
		programID = gl.glCreateProgramObjectARB();
		
		gl.glShaderSourceARB(vertexShaderID, vertexSourceCode.length, vertexSourceCode, vertexSourceLengths, 0);
		gl.glCompileShaderARB(vertexShaderID);
		gl.glAttachObjectARB(programID, vertexShaderID);
		
		gl.glShaderSourceARB(fragmentShaderID, fragmentSourceCode.length, fragmentSourceCode, fragmentSourceLengths, 0);
		gl.glCompileShaderARB(fragmentShaderID);
		gl.glAttachObjectARB(programID, fragmentShaderID);
		
		gl.glLinkProgramARB(programID);
		
		if(isParameterized())
			parameterID = gl.glGetUniformLocationARB(programID, parameterName);
		
		if(texture != null)
		{
			texture.makeCurrent(gl);
			int texLoc = gl.glGetUniformLocationARB(programID, "theTexture");
			gl.glUniform1i(texLoc, 0);
		}
	}

	public boolean isParameterized()
	{
		return parameterName != null && parameterName.trim().length() > 0;
	}

	public int getProgramID(GL gl)
	{
		checkInstallState(gl);
		return programID;
	}

	private void checkInstallState(GL gl)
	{
		if(programID == -1)
			install(gl);
	}
	
	public void resetIDs()
	{
		programID = -1;
		parameterID = -1;
		if(texture != null)
			texture.resetID();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String newName)
	{
		name = newName;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
}
