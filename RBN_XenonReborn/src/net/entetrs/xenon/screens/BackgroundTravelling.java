package net.entetrs.xenon.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.R;
import net.entetrs.xenon.libs.TextureLib;

/**
 * affiche un travelling aléatoire d'un fond étoilé.
 * 
 * @author robin
 *
 */

public class BackgroundTravelling
{
	private Texture spaceTexture;

	private float pX = 0;
	private float pY = 0;
	private float vX = random() * 10.0f;
	private float vY = random() * 10.0f;

	private float aX = 0;
	private float aY = 0;

	private int accumulator = 0;

	private float random()
	{
		return (float) (Math.random() * 3.0 - 1.5);
	}

	public BackgroundTravelling()
	{
		spaceTexture = TextureLib.BACKGROUND_SPACE.get();
	}

	/**
	 * affiche le fond.
	 * 
	 * @param batch
	 */
	public void drawBackGround(SpriteBatch batch)
	{
		batch.draw(spaceTexture, 0f, 0f, (int) pX, (int) pY, R.width, R.height);
	}

	/**
	 * calcule la translation du fond.
	 * 
	 * @param delta
	 */
	public void translateBackGround(float delta)
	{
		accumulator += delta;
		if (accumulator % 4000 == 0) // toutes les 4 secondes
		{
			accumulator = 0;
			aX = random();
			aY = random();
		}

		vX += aX;
		vY += aY;

		pX += vX * delta;
		pY += vY * delta;
	}

}
