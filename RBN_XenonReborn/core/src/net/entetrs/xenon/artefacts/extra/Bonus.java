package net.entetrs.xenon.artefacts.extra;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import net.entetrs.xenon.artefacts.AbstractArtefact;
import net.entetrs.xenon.commons.displays.AnimatedSprite;
import net.entetrs.xenon.commons.displays.Interpolator;

/**
 * représente un tir quel qu'il soit.
 * 
 * @author robin
 *
 */
public class Bonus extends AbstractArtefact
{
	private Interpolator interpolator;
	private AnimatedSprite animatedSprite;
	private BonusType type;

	public Bonus(BonusType type, int lifeForce, int impactForce, float x, float y, float vX, float vY)
	{
		super(vX, vY, lifeForce, impactForce);
		this.type = type;
		this.animatedSprite = type.createAnimatedSprite();
		this.animatedSprite.setCenter(x, y);
		this.interpolator = new Interpolator(Interpolation.sine, 2f, 50, x);
	}

	public BonusType getType()
	{
		return type;
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		float originalX = interpolator.getOriginalValue();
		this.animatedSprite.setCenterX(originalX);
		this.update(delta);
		float newPosition = interpolator.calculate(delta);
		this.animatedSprite.setCenterX(newPosition);
		this.getBoundingCircle().x = newPosition;
		animatedSprite.render(batch, delta);
	}

	@Override
	public Sprite getSprite()
	{
		return animatedSprite;
	}

}
