package net.entetrs.xenon.commons.libs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.entetrs.xenon.commons.displays.AnimatedSprite;
import net.entetrs.xenon.commons.utils.GdxCommons;

/**
 * catalogue d'animations pour l'ensemble du jeu, accessible sous forme d'ENUM
 * 
 * @author CDT RBN
 *
 */

public enum AnimationLib
{
	EXPLOSION_BIG("shoots/explosion-sheet.png", 8, 6, 2f, PlayMode.NORMAL), 
	EXPLOSION_LITTLE("shoots/little-explosion.png", 6, 1, 1f, PlayMode.NORMAL), 
	FRIENDLY_SHOOT("shoots/shoot-anim.png", 5, 1, 0.5f, PlayMode.LOOP, 10, 50), 
	FRIENDLY_BIGSHOOT("shoots/big-shoot.png", 5, 1, 1f, PlayMode.LOOP, 26, 80),
	BONUS("commons/bonus.png",8,1,1f, PlayMode.LOOP);

	private Animation<TextureRegion> animation;
	private float centerX = -1f;
	private float centerY = -1f;

	private AnimationLib(String fileName, int cols, int rows, float duration, Animation.PlayMode mode, float centerX, float centerY)
	{
		this(fileName, cols, rows, duration, mode);
		this.centerX = centerX;
		this.centerY = centerY;
	}

	private AnimationLib(String fileName, int cols, int rows, float duration, Animation.PlayMode mode)
	{
		TextureRegion[] result = GdxCommons.convertToTextureArray(fileName, cols, rows);
		this.animation = new Animation<>(duration / result.length, result);
		this.animation.setPlayMode(mode);
	}

	public Animation<TextureRegion> getAnimation()
	{
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
}
