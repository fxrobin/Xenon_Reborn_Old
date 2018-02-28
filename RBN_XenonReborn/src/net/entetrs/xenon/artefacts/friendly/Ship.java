package net.entetrs.xenon.artefacts.friendly;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.artefacts.AbstractArtefact;
import net.entetrs.xenon.artefacts.managers.ExplosionManager;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.libs.AnimationAsset;
import net.entetrs.xenon.commons.libs.SoundAsset;
import net.entetrs.xenon.commons.utils.GdxCommons;

public class Ship extends AbstractArtefact
{
	private ShipRenderer shipRenderer;
	private Shield shield;
	private boolean vulnerable;
	private int lifeCount;

	public Ship()
	{
		super(0,0, Global.SHIP_LIFE_POINTS, 20);
		shipRenderer = new ShipRenderer(this);
		shield = new Shield();
		this.setRadius(shipRenderer.getCurrentSprite().getWidth() / 2); 
		vulnerable = false;
		lifeCount = 3;
	}

	public float getCenterX()
	{
		return shipRenderer.getCenterX();
	}

	public float getCenterY()
	{
		return shipRenderer.getCenterY();
	}

	public boolean isShieldActivated()
	{
		return shield.isActivated();
	}
	
	
	/* redéfinition de "decreaseLife" et "increaseLife" 
	 * pour que les méthodes n'aient aucune conséquence
	 * en cas de bouclier activé.
	 */
	
	@Override
	public void decreaseLife(int force)
	{
		if (!isShieldActivated() && vulnerable)
		{
			
			super.decreaseLife(force);
			checkShipIsAliveAndBlink();
		}
	}

	private void checkShipIsAliveAndBlink()
	{
		if (!super.isAlive())
		{
			this.shipRenderer.blink();
			decreaseLifeCount();
		}
	}

	private void decreaseLifeCount()
	{
		lifeCount--;
		if (lifeCount > 0)
		{
			ExplosionManager.addExplosion(this, AnimationAsset.EXPLOSION_BIG);
			SoundAsset.SHIP_EXPLOSION.play();
		    this.setLifePoints(Global.SHIP_LIFE_POINTS);
		}
	}
	
	@Override
	public void increaseLife(int force)
	{
		if (!isShieldActivated())
		{
			super.increaseLife(force);
		}
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		vulnerable = !shipRenderer.isBlinking();
		shipRenderer.render(batch, delta);
	}
	
	public void switchShield()
	{
		shield.switchShield();
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);
		shield.update(delta);
		this.controlPosition();
	}

	private void controlPosition()
	{
		Sprite currentSprite = shipRenderer.getCurrentSprite();
		if (currentSprite.getX() < 0) currentSprite.setX(0);
		if (currentSprite.getY() < 80) currentSprite.setY(80);
		if (currentSprite.getX() > Global.width - currentSprite.getWidth()) currentSprite.setX(Global.width - currentSprite.getWidth());
		if (currentSprite.getY() > Global.height - currentSprite.getHeight()) currentSprite.setY(Global.height - currentSprite.getHeight());
		GdxCommons.computeBoundingCircle(currentSprite, getBoundingCircle());
	}

	@Override
	public Sprite getSprite()
	{
		return this.shipRenderer.getCurrentSprite();
	}
	
	public float getShieldEnergy()
	{
		return this.shield.getEnergy();
	}
	
	public int getLifeCount()
	{
		return lifeCount;
	}
	
	public boolean isFullyDestroyed()
	{
		return lifeCount <= 0;
	}
}
