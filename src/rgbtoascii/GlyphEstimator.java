package rgbtoascii;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class GlyphEstimator
{
	public GlyphEstimator(String p_imagePath)
	{
		try 
		{
			BufferedImage image = ImageIO.read(new File(p_imagePath));
			
			float[][] pixels = new float[image.getHeight()][image.getWidth()];
			
			int minY = 0, maxY = 0, minX = 0, maxX = 0;

			for (int y = 0, x = 0; y < image.getHeight(); y++) 
			{
				boolean broken = false;
				
				for (x = 0; x < image.getWidth(); x++) 
				{
					short r = (short)((image.getRGB(x, y) >> 16) & 0xff), g = (short)((image.getRGB(x, y) >> 8) & 0xff), b = (short)((image.getRGB(x, y)) & 0xff);
					
					if (!(r == 255 && g == 255 && b == 255))
					{
						minY = y;
						broken = true;
						break;
					}
				}
				
				if (broken)
				{
					break;
				}
			}

			for (int y = image.getHeight() - 1, x = 0; y > 0; y--) 
			{
				boolean broken = false;
				
				for (x = 0; x < image.getWidth(); x++) 
				{
					short r = (short)((image.getRGB(x, y) >> 16) & 0xff), g = (short)((image.getRGB(x, y) >> 8) & 0xff), b = (short)((image.getRGB(x, y)) & 0xff);
					
					if (!(r == 255 && g == 255 && b == 255))
					{
						maxY = y;
						broken = true;
						break;
					}
				}
				
				if (broken)
				{
					break;
				}
			}

			for (int x = 0, y = 0; x < image.getWidth(); x++) 
			{
				boolean broken = false;
				
				for (y = 0; y < image.getHeight(); y++) 
				{
					short r = (short)((image.getRGB(x, y) >> 16) & 0xff), g = (short)((image.getRGB(x, y) >> 8) & 0xff), b = (short)((image.getRGB(x, y)) & 0xff);
					
					if (!(r == 255 && g == 255 && b == 255))
					{
						minX = x;
						broken = true;
						break;
					}
				}
				
				if (broken)
				{
					break;
				}
			}

			for (int x = image.getWidth() - 1, y = 0; x > 0; x--) 
			{
				boolean broken = false;
				
				for (y = 0; y < image.getHeight(); y++) 
				{
					short r = (short)((image.getRGB(x, y) >> 16) & 0xff), g = (short)((image.getRGB(x, y) >> 8) & 0xff), b = (short)((image.getRGB(x, y)) & 0xff);
					
					if (!(r == 255 && g == 255 && b == 255))
					{
						maxX = x;
						broken = true;
						break;
					}
				}
				
				if (broken)
				{
					break;
				}
			}

			//if (maxY - minY > 0 && maxX - minX > 0)
			{
				for (int y = minY, x = minX; y < maxY; y++) 
				{
					for (x = minX; x < maxX; x++) 
					{
						short r = (short)((image.getRGB(x, y) >> 16) & 0xff), g = (short)((image.getRGB(x, y) >> 8) & 0xff), b = (short)((image.getRGB(x, y)) & 0xff);
						
						if (r >= g)
						{
							if (r >= b)
							{
								pixels[y - minY][x - minX] = r;
							}else
							{
								pixels[y - minY][x - minX] = b;
							}
						}else if (g >= b)
						{
							pixels[y - minY][x - minX] = g;
						}else
						{
							pixels[y - minY][x - minX] = b;
						}
						
						pixels[y - minY][x - minX]/=255;
						
						pixels[y - minY][x - minX] = 1 - pixels[y - minY][x - minX];
					}
				}
				
				float average = 0.f;
				
				for (int y = 0, x = 0; y < pixels.length; y++) 
				{
					for (x = 0; x < pixels[0].length; x++) 
					{
						average += pixels[y][x];
					}
				}
				
				//if (average != 0.f)
				{
					average = average/(pixels.length*pixels[0].length);
					
					try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("characterValues.txt", true)))) 
					{
					    out.println("\n" + "~" + " : " + average);
					    out.close();
					}catch (IOException e) 
					{
						
					}
				}
			}

		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}