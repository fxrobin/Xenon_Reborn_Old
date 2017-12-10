package net.entetrs.xenon.commons.fonts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public enum TrueTypeFont implements Disposable
{
	DEFAULT;

	private Log log = LogFactory.getLog(this.getClass());
	private BitmapFont font;

	private TrueTypeFont()
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
		log.info("DISPOSE FONTS ...");
		font.dispose();
		log.info("DISPOSE DONE ...");
	}

}
