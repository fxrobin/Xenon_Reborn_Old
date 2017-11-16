package net.entetrs.xenon.commons;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * classe utilitaire.
 * 
 * @author CDT RBN
 *
 */
public final class GdxCommons
{

	private GdxCommons()
	{
		// protection
	}

	/**
	 * retourne "true" si les codes des touches en paramètres sont toutes
	 * activées au clavier.
	 * 
	 * @param keys
	 * @return
	 */
	public static boolean checkConcurrentKeys(int... keys)
	{
		for (int key : keys)
		{
			if (!Gdx.input.isKeyPressed(key)) return false;
		}
		return true;
	}

	public static void setOriginCenter(Sprite... sprites)
	{
		Arrays.stream(sprites).forEach(Sprite::setOriginCenter);
	}

	public static float getCenterX(Sprite s)
	{
		return s.getX() + s.getOriginX();
	}

	public static float getCenterY(Sprite s)
	{
		return s.getY() + s.getOriginY();
	}

	public static void disposeAll(Disposable... disposables)
	{
		Arrays.stream(disposables).forEach(Disposable::dispose);
	}
	
	public static TextureRegion[] convertToTextureArray(String fileName, int cols, int rows)
	{
		int totalFrames = cols * rows;
		Texture tmp = new Texture(fileName);
		TextureRegion[][] tmpRegion = TextureRegion.split(tmp, tmp.getWidth() / cols, tmp.getHeight() / rows);
		TextureRegion[] result = new TextureRegion[totalFrames];
		int index = 0;
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				result[index++] = tmpRegion[i][j];
			}
		}
		return result;
	}

	public static void clearScreen()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static void clearScreen(float alpha)
	{
		Gdx.gl.glClearColor(0, 0, 0, alpha);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static void applyAlpha(Sprite s, float alpha)
	{
		s.setColor(s.getColor().r, s.getColor().g, s.getColor().b, alpha);
	}
	

}
