package net.entetrs.xenon.commons.utils;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Disposable;

import net.entetrs.xenon.commons.Global;

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
		/* protection */
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

	public static void applyAlpha(Sprite s, float alpha)
	{
		s.setColor(s.getColor().r, s.getColor().g, s.getColor().b, alpha);
	}
	
	public static void computeBoundingCircle(Sprite sprite, Circle boundingCircle)
	{
		boundingCircle.setX(getCenterX(sprite));
		boundingCircle.setY(getCenterY(sprite));
	}

	public static void switchFullScreen()
	{
		Monitor currMonitor = Gdx.graphics.getMonitor();
		DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
	    
		if (!Gdx.graphics.isFullscreen())
		{
			if (!Gdx.graphics.setFullscreenMode(displayMode))
			{
				System.err.println("Erreur de passage en plein écran"); //NOSONAR
			}
		}
		else
		{
			if (!Gdx.graphics.setWindowedMode(Global.width, Global.height))
			{
				System.err.println("Erreur de passage mode fenêtré"); //NOSONAR
			}
		}
	}

}
