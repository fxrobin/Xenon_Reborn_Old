package net.entetrs.xenon.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.entetrs.xenon.commons.GdxCommons;

/**
 * catalogue d'animations pour l'ensemble du jeu, accessible sous forme d'ENUM
 * 
 * @author CDT RBN
 *
 */

public enum AnimationCatalog
{
	EXPLOSION("shoots/explosion-sheet.png", 8, 6, 2f, PlayMode.NORMAL ), 
	FRIENDLY_SHOOT("shoots/shoot-anim.png", 5, 1, 0.5f, PlayMode.LOOP);

	private Animation<TextureRegion> animation;
	
	private AnimationCatalog(String fileName, int cols, int rows, float duration, Animation.PlayMode mode)
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
		return new AnimatedSprite(this);
	}

}
