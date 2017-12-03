package net.entetrs.xenon.commons.libs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public enum FontLib implements Disposable
{
	DEFAULT;

	private Log log = LogFactory.getLog(this.getClass());
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
		log.info("DISPOSE FONTS ...");
		font.dispose();
		log.info("DISPOSE DONE ...");
	}

}
