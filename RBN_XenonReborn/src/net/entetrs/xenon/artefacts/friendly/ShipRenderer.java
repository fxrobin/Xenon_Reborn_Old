package net.entetrs.xenon.artefacts.friendly;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.artefacts.friendly.ShipInput.Horizontal;
import net.entetrs.xenon.artefacts.friendly.ShipInput.Vertical;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.displays.Renderable;
import net.entetrs.xenon.commons.libs.TextureAsset;
import net.entetrs.xenon.commons.utils.GdxCommons;

/**
 * cette classe assure le rendu du vaisseau en fonction de son état.
 * 
 * @author robin
 *
 */
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
		currentSprite.setCenter(Global.width / 2f, 80);
	}

	private void loadSprites()
	{
		shipSpriteReactorOn = new Sprite(TextureAsset.SHIP.get());
		shipSpriteLeft = new Sprite(TextureAsset.SHIP_LEFT.get());
		shipSpriteRight = new Sprite(TextureAsset.SHIP_RIGHT.get());
		shipSpriteReactorOff = new Sprite(TextureAsset.SHIP_NOREACTOR.get());
		shieldSprite = new Sprite(TextureAsset.SHIELD.get());
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
	public void render(SpriteBatch batch, float delta)
	{
		this.setCorrectSprite();
		currentSprite.draw(batch);
		this.drawShieldIfActivated(batch);
	}

	private void drawShieldIfActivated(Batch batch)
	{
		if (ship.isShieldActivated())
		{
			shieldSprite.setCenter(this.getCenterX(), this.getCenterY());
			shieldSprite.draw(batch);
		}
	}

	private void setCorrectSprite()
	{
		updateSpriteForVerticalMovement();
		updateSpriteForHorizontalMovement();
	}

	private void updateSpriteForHorizontalMovement()
	{
		if (ShipHandler.getHorizontalControl() == Horizontal.LEFT)
		{
			this.changeCurrentSprite(shipSpriteLeft);
		}
		else if (ShipHandler.getHorizontalControl() == Horizontal.RIGHT)
		{
			this.changeCurrentSprite(shipSpriteRight);
		}
	}

	private void updateSpriteForVerticalMovement()
	{
		if (ShipHandler.getVerticalControl() == Vertical.UP)
		{
			this.changeCurrentSprite(shipSpriteReactorOn);
		}
		else
		{
			/* dans tous les cas on coupe le réacteur */
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
