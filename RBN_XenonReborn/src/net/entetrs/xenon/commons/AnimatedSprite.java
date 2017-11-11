package net.entetrs.xenon.commons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.entetrs.xenon.helpers.AnimationLib;

/**
 * classe pour gérer les animations d'un sprite en fonction
 * des animations du catalogue.
 * 
 * @author CDT RBN
 *
 */

public class AnimatedSprite extends Sprite
{
	private float stateTime;
	private Animation<TextureRegion> animation;

	public AnimatedSprite(AnimationLib anim)
	{
		super(anim.getAnimation().getKeyFrames()[0]);
		stateTime = 0f;
		this.animation = anim.getAnimation();
	}

	public void act(float delta)
	{
		stateTime += delta;
		this.setRegion(animation.getKeyFrame(stateTime));
	}

	public void render(Batch batch, float delta)
	{
		this.act(delta);
		this.draw(batch);
	}

	public boolean isFinished()
	{
		return animation.isAnimationFinished(stateTime);
	}

}
