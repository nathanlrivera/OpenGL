package a5;

import java.awt.Color;

public class AmbientLight
{
	static AmbientLight instance;

	private Color intensity;

	private AmbientLight()
	{
		intensity = Color.darkGray;
	}

	public static synchronized AmbientLight getInstance()
	{
		if(instance == null)
			instance = new AmbientLight();
		return instance;
	}

	public synchronized Color getIntensity()
	{
		return intensity;
	}

	public synchronized void setIntensity(Color newIntensity)
	{
		intensity = newIntensity;
	}
}
