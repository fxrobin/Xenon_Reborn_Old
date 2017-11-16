package net.entetrs.xenon.commons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.entetrs.xenon.libs.TextureLib;

public final class FontCommons
{
	private static final int FONT_W = 32;
	private static final int FONT_H = 38;
	private static Texture fontAZ = TextureLib.FONT_AZ.get();
	private static Texture font09 = TextureLib.FONT_09.get();

	private FontCommons()
	{
		// protection
	}

	public static int getWidth(String txt)
	{
		return txt.length() * FONT_W;
	}

	public static int getHeight(String txt) //NOSONAR
	{
		return FONT_H;
	}

	public static void print(Batch b, float x, float y, String txt)
	{
		String upperTxt = txt.toUpperCase();
		for (int i = 0; i < upperTxt.length(); i++)
		{
			char c = upperTxt.charAt(i);
			print(b, x + (i * FONT_W), y, c);
		}
	}

	public static void print(Batch b, float x, float y, char c)
	{
		if (c >= 65 && c <= 90)
		{
			int offset = (c - 65) * FONT_W;
			b.draw(fontAZ, x, y, offset, 0, FONT_W, FONT_H);
		}
		if (c >= 48 && c <= 57)
		{
			int offset = (c - 48) * FONT_W;
			b.draw(font09, x, y, offset, 0, FONT_W, FONT_H);
		}
	}

}
