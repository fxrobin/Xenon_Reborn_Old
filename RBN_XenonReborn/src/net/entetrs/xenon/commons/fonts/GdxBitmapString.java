package net.entetrs.xenon.commons.fonts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.entetrs.xenon.commons.displays.Displayable;

public class GdxBitmapString extends Displayable
{
	// chaque lettre sera un sprite avec sa propre position.
	private Sprite[] sprites;
	
	// taille totale de la chaine
	private int width;
	
	// hauteur de la font
	private int letterWidth;

	public GdxBitmapString(String text)
	{
		super();		
		this.width = FontUtils.getWidth(text);
		this.letterWidth = FontUtils.getFontWidth();
		this.populateSprites(text);
	}
	
	/* initialisation du tableau de sprites en fonction du TextureRegion calculés */
	private void populateSprites(String text)
	{
		sprites = new Sprite[text.length()];
		int index = 0;
		for (char c : text.toCharArray())
		{
			TextureRegion tr = FontUtils.getTextureRegionOfChar(c);
			sprites[index++] = (tr!=null) ? new Sprite(tr) : null;
		}
	}

	public int getWidth()
	{
		return width;
	}
	
	@Override
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);
		
		// calcul de la position X de chacun des sprites.
		float offsetX = this.getPositionX();
		for (Sprite s : sprites)
		{
			if (s != null)
			{
				s.setX(offsetX);	
				s.setY(y);	
			}
			offsetX += letterWidth;
		}	
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		for (Sprite s : sprites)
		{
			if (s != null)
			{
				s.draw(batch);	
			}
    	}
	}
}
