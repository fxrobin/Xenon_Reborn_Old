package net.entetrs.xenon.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.commons.GdxCommons;
import net.entetrs.xenon.commons.Renderable;
import net.entetrs.xenon.entities.Ship;
import net.entetrs.xenon.entities.ShipJoystick.Horizontal;
import net.entetrs.xenon.entities.ShipJoystick.Vertical;
import net.entetrs.xenon.libs.TextureLib;

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
		currentSprite.setCenter(C.WIDTH / 2f, 80);
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
		if (ship.getHorizontalControl() == Horizontal.LEFT)
		{
			this.changeCurrentSprite(shipSpriteLeft);
		}
		else if (ship.getHorizontalControl() == Horizontal.RIGHT)
		{
			this.changeCurrentSprite(shipSpriteRight);
		}
	}

	private void updateSpriteForVerticalMovement()
	{
		if (ship.getVerticalControl() == Vertical.UP)
		{
			this.changeCurrentSprite(shipSpriteReactorOn);
		}
		else
		{
			// dans tous les cas on coupe le réacteur
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
