package net.entetrs.xenon.commons.fonts;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.entetrs.xenon.commons.libs.TextureAsset;

public final class FontUtils
{
	private static final Log log = LogFactory.getLog(FontUtils.class);

	static
	{
		Properties config = new Properties();
		try
		{
			config.load(FontUtils.class.getResourceAsStream("/fonts/font-blue.properties"));
			fontWidth = Integer.parseInt(config.getProperty("letter-width"));
			fontHeight = Integer.parseInt(config.getProperty("letter-height"));
			stringMap = config.getProperty("string-map");
			log.info("Font loaded");
		}
		catch (IOException e)
		{
			log.error("Impossible de lire le fichier de ressource de font.");
		}

	}

	private static int fontWidth;
	private static int fontHeight;
	private static String stringMap;

	private static Texture font = TextureAsset.FONT.get();

	private FontUtils()
	{
		/* protection */
	}

	public static int getWidth(String txt)
	{
		return txt.length() * fontWidth;
	}

	public static int getHeight(String txt) /* NOSONAR */
	{
		return fontHeight;
	}

	public static int getFontWidth()
	{
		return fontWidth;
	}

	public static void setFontHeight(int fontHeight)
	{
		FontUtils.fontHeight = fontHeight;
	}

	public static void print(Batch b, float x, float y, String txt)
	{
		for (int i = 0; i < txt.length(); i++)
		{
			print(b, x + (i * fontWidth), y, txt.charAt(i));
		}
	}

	public static void print(Batch batch, float positionX, float positionY, char character)
	{
		int offset = getCharOffset(character);
		if (offset >= 0)
		{
			batch.draw(font, positionX, positionY, offset, 0, fontWidth, fontHeight);
		}
	}

	public static int getCharOffset(char character)
	{
		int charIndex = stringMap.indexOf(character);
		return (charIndex >= 0) ? charIndex * fontWidth : -1;
	}

	public static TextureRegion getTextureRegionOfChar(char character)
	{
		int offset = getCharOffset(character);
		return (offset >= 0) ? new TextureRegion(font, offset, 0, fontWidth, fontHeight) : null;
	}

}
