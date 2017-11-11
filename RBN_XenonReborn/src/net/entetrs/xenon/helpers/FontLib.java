package net.entetrs.xenon.helpers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public enum FontLib implements Disposable
{
	DEFAULT;

	private BitmapFont font;

	private FontLib()
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
