package net.entetrs.xenon.commons.libs;

import java.util.stream.Stream;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import net.entetrs.xenon.commons.displays.AnimatedSprite;
import net.entetrs.xenon.commons.utils.GdxCommons;

/**
 * catalogue d'animations pour l'ensemble du jeu, accessible sous forme d'ENUM
 * 
 * @author CDT RBN
 *
 */

public enum AnimationAsset implements Disposable
{
	EXPLOSION_BIG("shoots/explosion-sheet.png", 8, 6, 2f, PlayMode.NORMAL), EXPLOSION_LITTLE("shoots/little-explosion.png", 6, 1, 1f, PlayMode.NORMAL), FRIENDLY_SHOOT("shoots/shoot-anim.png", 5, 1, 0.5f, PlayMode.LOOP, 10,
			50), FRIENDLY_BIGSHOOT("shoots/big-shoot.png", 5, 1, 1f, PlayMode.LOOP, 26, 80), BONUS("commons/bonus.png", 8, 1, 1f, PlayMode.LOOP), POWER_UP("commons/bonus-power-up-anim.png", 7, 1, 1f, PlayMode.LOOP);

	private final String fileName;
	private final int cols;
	private final int rows;
	private final float duration;
	private final Animation.PlayMode playMode;

	private float centerX;
	private float centerY;
	private float radius;

	private Texture texture;
	private Animation<TextureRegion> animation;

	private AnimationAsset(String fileName, int cols, int rows, float duration, Animation.PlayMode mode, float centerX, float centerY)
	{
		this(fileName, cols, rows, duration, mode);
		this.centerX = centerX;
		this.centerY = centerY;
	}

	private AnimationAsset(String fileName, int cols, int rows, float duration, Animation.PlayMode playMode)
	{
		this.fileName = fileName;
		this.playMode = playMode;
		this.cols = cols;
		this.rows = rows;
		this.duration = duration;
	}

	private AnimationAsset(String fileName, int cols, int rows, float duration, Animation.PlayMode playMode, float radius)
	{
		this(fileName, cols, rows, duration, playMode);
		this.radius = radius;
	}

	private Animation<TextureRegion> getAnimation()
	{
		if (animation == null)
		{
			this.texture = AssetLib.getInstance().get(fileName, Texture.class);
			TextureRegion[] result = GdxCommons.convertToTextureArray(texture, cols, rows);
			animation = new Animation<>(duration / result.length, result);
			animation.setPlayMode(playMode);
		}
		return animation;
	}

	public AnimatedSprite createAnimatedSprite()
	{
		if (centerX >= 0 || centerY >= 0)
		{
			return new AnimatedSprite(this.getAnimation(), centerX, centerY);
		}
		else
		{
			return new AnimatedSprite(this.getAnimation());
		}
	}

	public float getRadius()
	{
		return radius;
	}

	@Override
	public String toString()
	{
		return this.fileName;
	}

	public void dispose()
	{
		if (texture != null)
		{
			texture.dispose();
		}
	}

	public static void disposeAll()
	{
		GdxCommons.disposeAll(AnimationAsset.values());
	}
}
