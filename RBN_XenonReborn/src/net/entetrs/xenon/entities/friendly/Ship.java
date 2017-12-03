package net.entetrs.xenon.entities.friendly;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.artefacts.AbstractArtefact;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.utils.GdxCommons;
import net.entetrs.xenon.libs.SoundLib;

public class Ship extends AbstractArtefact
{
	private ShipRenderer shipRenderer;
	private boolean shieldActivated = false;

	public Ship()
	{
		super(0,0, 60, 20);
		shipRenderer = new ShipRenderer(this);
		this.setRadius(shipRenderer.getCurrentSprite().getWidth() / 2);
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
		return shieldActivated;
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		shipRenderer.render(batch, delta);
	}
	
	public void switchShield()
	{
		shieldActivated = !shieldActivated;
		if (shieldActivated)
		{
			SoundLib.SHIELD_UP.play();
		}
		else
		{
			SoundLib.SHIELD_DOWN.play();
		}
	}

	public void checkShipControls(float delta)
	{
		ShipHandler.handle(this);
		this.act(delta);
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
}
