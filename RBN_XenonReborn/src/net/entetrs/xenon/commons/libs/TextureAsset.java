package net.entetrs.xenon.commons.libs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;

public enum TextureAsset
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
	
	private final String fileName;
	private final TextureWrap wrap;
	
	private Texture texture;


	private TextureAsset(String fileName)
	{
		this(fileName, null);
	}
	
	private TextureAsset(String fileName, TextureWrap wrap)
	{
		this.fileName = fileName;
		this.wrap = wrap;
	}

	@Override
	public String toString()
	{
		return this.fileName;
	}

	public Texture get()
	{
		if (texture == null)
		{
			Texture tmpTexture = AssetLib.getInstance().get(this, Texture.class);
			if (wrap != null)
			{
				tmpTexture.setWrap(wrap, wrap);
			}
			this.texture = tmpTexture;
		}
		return texture;
	}

}
