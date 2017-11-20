package net.entetrs.xenon.commons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.entities.Artefact;
import net.entetrs.xenon.libs.AnimationLib;

/**
 * classe pour gérer les animations d'un sprite en fonction des animations du
 * catalogue.
 * 
 * @author CDT RBN
 *
 */

public class AnimatedSprite extends Sprite implements Artefact
{
	private float stateTime;
	private Animation<TextureRegion> animation;
	private Circle boundingCircle;
	private float radius;
	private int lifePoints = 2;
	private int impactForce = 5;

	public AnimatedSprite(AnimationLib anim)
	{
		super(anim.getAnimation().getKeyFrames()[0]);
		radius = anim.getAnimation().getKeyFrames()[0].getRegionWidth() / 2.0f;
		stateTime = 0f;
		this.animation = anim.getAnimation();
		boundingCircle = new Circle();
	}

	public AnimatedSprite(AnimationLib anim, float centerX, float centerY)
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

	@Override
	public void decreaseLife(int impact)
	{
		lifePoints = lifePoints - impact;

	}

	@Override
	public boolean isAlive()
	{
		return lifePoints > 0;
	}

	@Override
	public int getImpactForce()
	{
		return impactForce;
	}
	
	public void setImpactForce(int impactForce)
	{
		this.impactForce = impactForce;
	}

	@Override
	public Circle getBoundingCircle()
	{
		return boundingCircle;
	}

	@Override
	public Sprite getSprite()
	{
		return this;
	}
	
	public void setLifePoints(int lifePoints)
	{
		this.lifePoints = lifePoints;
	}

}
