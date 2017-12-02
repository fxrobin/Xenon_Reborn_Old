package net.entetrs.xenon.fonts;

import java.util.Locale;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import net.entetrs.xenon.libs.TextureLib;

public final class FontUtils
{
	private static final int FONT_W = 32;
	private static final int FONT_H = 38;
	private static Texture fontAZ = TextureLib.FONT_AZ.get();
	private static Texture font09 = TextureLib.FONT_09.get();

	private FontUtils()
	{
		/* protection */
	}

	public static int getWidth(String txt)
	{
		return txt.length() * FONT_W;
	}

	public static int getHeight(String txt) /* NOSONAR */
	{
		return FONT_H;
	}

	public static void print(Batch b, float x, float y, String txt)
	{
		String upperTxt = txt.toUpperCase(Locale.FRANCE);
		for (int i = 0; i < upperTxt.length(); i++)
		{
			print(b, x + (i * FONT_W), y, upperTxt.charAt(i));
		}
	}

	public static void print(Batch batch, float positionX, float positionY, char character)
	{
		if (character >= 65 && character <= 90)
		{
			int offset = (character - 65) * FONT_W;
			batch.draw(fontAZ, positionX, positionY, offset, 0, FONT_W, FONT_H);
		}
		if (character >= 48 && character <= 57)
		{
			final int offset = (character - 48) * FONT_W;
			batch.draw(font09, positionX, positionY, offset, 0, FONT_W, FONT_H);
		}
	}

}
