package net.entetrs.xenon.commons.fonts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.displays.Displayable;

public class GdxBitmapString extends Displayable
{
	private String text;
	
	public GdxBitmapString(String text)
	{
		super();
		this.text = text;
	}
	
	public int getWidth()
	{
		return FontUtils.getWidth(text);
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		FontUtils.print(batch, this.getPositionX(), this.getPositionY(), text);
	}	

}
