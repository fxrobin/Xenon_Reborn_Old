package net.entetrs.xenon.managers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public class FontManager implements Disposable
{

	private BitmapFont font;

	public FontManager()
	{
		font = new BitmapFont();

	}

	public BitmapFont getFont()
	{
		return font;
	}

	@Override
	public void dispose()
	{
		System.out.print("DISPOSE FONTS ...");
		font.dispose();
		System.out.print("DISPOSE DONE ...");
	}

}
