package net.entetrs.xenon.entities.friendly;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.artefacts.AbstractArtefact;
import net.entetrs.xenon.commons.displays.AnimatedSprite;

/**
 * représente un tir quel qu'il soit.
 * 
 * @author robin
 *
 */
public class Shoot extends AbstractArtefact
{
	private AnimatedSprite animatedSprite;
	private float vX;
	private float vY;

	public Shoot(AnimatedSprite animatedSprite, int lifeForce, int impactForce, float vX, float vY)
	{
		super(lifeForce, impactForce);
		this.animatedSprite = animatedSprite;
		this.vX = vX;
		this.vY = vY;
	}

	public Circle getBoundingCircle()
	{
		return this.animatedSprite.getBoundingCircle();
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		this.translateX(vX * delta);
		this.translateY(vY * delta);
		animatedSprite.render(batch, delta);
	}

	@Override
	public Sprite getSprite()
	{
		return animatedSprite;
	}

}
