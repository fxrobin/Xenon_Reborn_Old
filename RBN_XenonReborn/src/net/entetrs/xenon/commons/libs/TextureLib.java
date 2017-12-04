package net.entetrs.xenon.commons.libs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.Disposable;

import net.entetrs.xenon.commons.utils.GdxCommons;

public enum TextureLib implements Disposable
{
	TITLE("commons/xenon-reborn.png"), 
	BACKGROUND_LEFT("backgrounds/left_bg.png", TextureWrap.Repeat), 
	BACKGROUND_RIGHT("backgrounds/right_bg.png", TextureWrap.Repeat), 
	BACKGROUND_SPACE("backgrounds/space.jpg",	TextureWrap.Repeat), 
	FOOTER("backgrounds/footer.png"),

	SHIELD("ships/shield.png"), 
	SHIP_LEFT("ships/ship_left.png"), 
	SHIP_RIGHT("ships/ship_right.png"), 
	SHIP("ships/ship_normal.png"), 
	SHIP_NOREACTOR("ships/ship_noreactor.png"),

	ENEMY("enemies/enemy.png"), 
	BUG("enemies/bug.png"), 
	PERFORATOR("enemies/perforator.png"),
	BIG_ENEMY("enemies/big-enemy.png"),

	FONT_AZ("fonts/font-AZ.png"), 
	FONT_09("fonts/font-09.png");

	private Texture texture;

	private TextureLib(String fileName, TextureWrap wrap)
	{
		this(fileName);
		this.texture.setWrap(wrap, wrap);
	}

	private TextureLib(String fileName)
	{
		this.texture = new Texture(fileName);

	}

	public Texture get()
	{
		return texture;
	}

	@Override
	public void dispose()
	{
		this.texture.dispose();
	}

	public static void disposeAll()
	{
		GdxCommons.disposeAll(TextureLib.values());
	}

}
