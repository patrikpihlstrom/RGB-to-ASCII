package rgbtoascii;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class RGBtoASCII
{
	static Map<Integer, String> m_characters = new HashMap<Integer, String>();
	
	public static void main(String[] args) 
	{
		try 
		{
			PopulateCharacterMap(m_characters);
			BufferedImage image = ImageIO.read(new File("image.png"));
			
			float[][] pixels = new float[image.getHeight()][image.getWidth()];
			short maxValue = 255;

			for (int y = 0, x = 0; y < image.getHeight(); y++) 
			{
				for (x = 0; x < image.getWidth(); x++) 
				{
					short r = (short)((image.getRGB(x, y) >> 16) & 0xff), g = (short)((image.getRGB(x, y) >> 8) & 0xff), b = (short)((image.getRGB(x, y)) & 0xff);
					
					if (r >= g)
					{
						if (r >= b)
						{
							pixels[y][x] = r;
						}else
						{
							pixels[y][x] = b;
						}
					}else if (g >= b)
					{
						pixels[y][x] = g;
					}else
					{
						pixels[y][x] = b;
					}
					
					if (pixels[y][x] > maxValue)
					{
						maxValue = (short)pixels[y][x];
					}
				}
			}
			
			for (int y = 0, x = 0; y < image.getHeight(); y++) 
			{
				for (x = 0; x < image.getWidth(); x++) 
				{
					pixels[y][x] /= maxValue;
				}
			}
			
			PrintWriter output = new PrintWriter("output.txt", "UTF-8");
			
			for (int y = 0, x = 0; y < pixels.length; y++) 
			{
				boolean newLine = false;
				
				for (x = 0; x < pixels[0].length; x++) 
				{
					if (pixels[y][x] != -1)
					{
						output.print(GetChar((int)(pixels[y][x]*100)));
						newLine = true;
					}
				}
				
				if (newLine)
				{
					output.println();
				}
			}
			
			output.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return;
	}
	
	static void PopulateCharacterMap(Map<Integer, String> p_characters)
	{
		//! " # $ % & ' ( ) * + , - . / 0 1 2 3 4 5 6 7 8 9 : ; < = > ? @ A B C D E F G H I J K L M N O P Q R S T U V W X Y Z [ \ ] ^ _ ` a b c d e f g h i j k l m n o p q r s t u v w x y z { | } ~
		p_characters.put(0, new String(" "));
		p_characters.put(3, new String("`"));
		p_characters.put(6, new String("-"));
		p_characters.put(7, new String("."));
		p_characters.put(8, new String("~"));
		p_characters.put(10, new String("I"));
		p_characters.put(11, new String("_,"));
		p_characters.put(13, new String("|="));
		p_characters.put(16, new String("i"));
		p_characters.put(17, new String(":"));
		p_characters.put(20, new String("!l"));
		p_characters.put(21, new String(");"));
		p_characters.put(23, new String("<>"));
		p_characters.put(24, new String("^"));
		p_characters.put(25, new String("*+"));
		p_characters.put(28, new String("/"));
		p_characters.put(29, new String("j"));
		p_characters.put(30, new String("r?"));
		p_characters.put(31, new String("1(J"));
		p_characters.put(34, new String("27c"));
		p_characters.put(35, new String("Lu3]"));
		p_characters.put(40, new String("tz"));
		p_characters.put(41, new String("G5"));
		p_characters.put(43, new String("9"));
		p_characters.put(44, new String("6)n("));
		p_characters.put(45, new String("F"));
		p_characters.put(46, new String("0"));
		p_characters.put(47, new String("fE%s"));
		p_characters.put(49, new String("[v#$"));
		p_characters.put(50, new String("4"));
		p_characters.put(51, new String("8e"));
		p_characters.put(53, new String("hATxD"));
		p_characters.put(55, new String("o"));
		p_characters.put(57, new String("yZ"));
		p_characters.put(58, new String("q"));
		p_characters.put(59, new String("&"));
		p_characters.put(63, new String("Yc"));
		p_characters.put(64, new String("B"));
		p_characters.put(66, new String("g@a"));
		p_characters.put(68, new String("U"));
		p_characters.put(69, new String("kS"));
		p_characters.put(71, new String("PbV"));
		p_characters.put(72, new String("O"));
		p_characters.put(78, new String("p"));
		p_characters.put(81, new String("HX"));
		p_characters.put(84, new String("w"));
		p_characters.put(89, new String("mK"));
		p_characters.put(95, new String("M"));
		p_characters.put(96, new String("Q"));
		p_characters.put(98, new String("R"));
		p_characters.put(99, new String("N"));
		p_characters.put(100, new String("W"));
	}
	
	static char GetChar(int p_val)
	{
		while (!m_characters.containsKey(p_val))
		{
			p_val--;
		}
		
		String string = (m_characters.get(p_val));
		
		char result = string.charAt((int)(Math.random()*string.length()));
		
		return result;
	}
}