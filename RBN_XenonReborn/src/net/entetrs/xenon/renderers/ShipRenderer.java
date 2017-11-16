package net.entetrs.xenon.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.commons.GdxCommons;
import net.entetrs.xenon.commons.Renderable;
import net.entetrs.xenon.entities.Ship;
import net.entetrs.xenon.libs.TextureLib;

public class ShipRenderer implements Renderable
{
	private Ship ship;

	private Sprite shipSpriteReactorOn;
	private Sprite shipSpriteLeft;
	private Sprite shipSpriteRight;
	private Sprite shipSpriteReactorOff;
	private Sprite shieldSprite;

	private Sprite currentSprite;

	public ShipRenderer(Ship ship)
	{
		super();
		this.ship = ship;
		this.loadSprites();
		GdxCommons.setOriginCenter(shipSpriteLeft, shipSpriteRight, shipSpriteReactorOff, shipSpriteReactorOn);
		GdxCommons.setOriginCenter(shieldSprite);
		currentSprite = shipSpriteReactorOn;
		currentSprite.setCenter(C.WIDTH / 2, 80);
	}

	private void loadSprites()
	{
		shipSpriteReactorOn = new Sprite(TextureLib.SHIP.get());
		shipSpriteLeft = new Sprite(TextureLib.SHIP_LEFT.get());
		shipSpriteRight = new Sprite(TextureLib.SHIP_RIGHT.get());
		shipSpriteReactorOff = new Sprite(TextureLib.SHIP_NOREACTOR.get());
		shieldSprite = new Sprite(TextureLib.SHIELD.get());
	}

	public Sprite getCurrentSprite()
	{
		return currentSprite;
	}

	public float getCenterX()
	{
		return GdxCommons.getCenterX(currentSprite);
	}

	public float getCenterY()
	{
		return GdxCommons.getCenterY(currentSprite);
	}

	@Override
	public void render(Batch batch, float delta)
	{
		this.setCorrectSprite();
		currentSprite.draw(batch);

		if (ship.isShieldActivated())
		{
			shieldSprite.setCenter(this.getCenterX(), this.getCenterY());
			shieldSprite.draw(batch);
		}
	}

	private void setCorrectSprite()
	{
		switch (ship.getvControl())
		{
			case UP:
			{
				this.changeCurrentSprite(shipSpriteReactorOn);
				break;
			}
			case DOWN:
			{
				this.changeCurrentSprite(shipSpriteReactorOff);
				break;
			}
			default:
				this.changeCurrentSprite(shipSpriteReactorOff);
		}

		switch (ship.gethControl())
		{
			case LEFT:
			{
				this.changeCurrentSprite(shipSpriteLeft);
				break;
			}
			case RIGHT:
			{
				this.changeCurrentSprite(shipSpriteRight);
				break;
			}
			default:
				this.changeCurrentSprite(shipSpriteReactorOff);
		}
	}

	private void changeCurrentSprite(Sprite newSprite)
	{
		float x = this.getCenterX();
		float y = this.getCenterY();
		currentSprite = newSprite;
		currentSprite.setCenter(x, y);
	}

}
