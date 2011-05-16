package a5;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.color.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;

public class Texture implements Serializable
{
	private static final long serialVersionUID = 1L;
	private byte[] imgRGBA;
	private int textureID;
	private int width;
	private int height;
	
	public Texture()
	{
		resetID();
	}
	
	public void loadFromFile(String filePath) throws FileNotFoundException, IOException
	{
		BufferedImage textureImage = ImageIO.read(new BufferedInputStream(new FileInputStream(filePath)));
		loadRGBAPixelData(textureImage);
	}
	
	private void loadRGBAPixelData(BufferedImage img)
	{
		height = img.getHeight(null);
		width = img.getWidth(null);
		
      WritableRaster raster = Raster.createInterleavedRaster(
      		DataBuffer.TYPE_BYTE, 
      		width, 
      		height, 
      		4, 
      		null);
      
      ComponentColorModel colorModel = new ComponentColorModel(
      		ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[] {8,8,8,8},
            true,
            false,
            ComponentColorModel.TRANSLUCENT,
            DataBuffer.TYPE_BYTE);
      
      BufferedImage newImage = new BufferedImage(colorModel, raster, false, null);
      
      AffineTransform gt = new AffineTransform();
      gt.translate(0, height);
      gt.scale(1, -1d);
      
      Graphics2D g = newImage.createGraphics();
      g.transform(gt);
      
      g.drawImage(img, null, null);
      g.dispose();
      
      DataBufferByte dataBuf = (DataBufferByte)raster.getDataBuffer();
      imgRGBA = dataBuf.getData();
	}
	
	private void installTexture(GL gl)
	{
		int[] textureIDs = new int[1];
		gl.glGenTextures(1, textureIDs, 0);
		textureID = textureIDs[0];
		
		gl.glBindTexture(GL.GL_TEXTURE_2D, textureID);
		ByteBuffer wrappedRGBA = ByteBuffer.wrap(imgRGBA);
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, width, height, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, wrappedRGBA);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	}
	
	public void makeCurrent(GL gl)
	{
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
		gl.glBindTexture(GL.GL_TEXTURE_2D, getTextureID(gl));
	}
	
	private int getTextureID(GL gl)
	{
		if(textureID == -1)
			installTexture(gl);
		return textureID;
	}

	public void resetID()
	{
		textureID = -1;
	}
}
