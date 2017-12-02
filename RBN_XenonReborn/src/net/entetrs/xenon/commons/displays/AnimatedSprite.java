package net.entetrs.xenon.commons.displays;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.commons.utils.GdxCommons;

/**
 * classe pour gérer les animations d'un sprite en fonction des animations du
 * catalogue.
 * 
 * @author CDT RBN
 *
 */

public class AnimatedSprite extends Sprite
{
	private float stateTime;
	private Animation<TextureRegion> animation;
	private Circle boundingCircle;
	private float radius;

	public AnimatedSprite(Animation<TextureRegion> anim)
	{
		super(anim.getKeyFrames()[0]);
		radius = anim.getKeyFrames()[0].getRegionWidth() / 2.0f;
		stateTime = 0f;
		this.animation = anim;
		boundingCircle = new Circle();
	}

	public AnimatedSprite(Animation<TextureRegion> anim, float centerX, float centerY)
	{
		this(anim);
		this.setOrigin(centerX, centerY);
	}

	public void act(float delta)
	{
		stateTime += delta;
		this.setRegion(animation.getKeyFrame(stateTime));
		boundingCircle.set(GdxCommons.getCenterX(this), GdxCommons.getCenterY(this), radius);
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
	
	public Circle getBoundingCircle()
	{
		return boundingCircle;
	}
	
	

}
