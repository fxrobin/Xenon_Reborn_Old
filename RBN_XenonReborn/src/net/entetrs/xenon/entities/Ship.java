package net.entetrs.xenon.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.commons.GdxCommons;
import net.entetrs.xenon.helpers.SoundLib;
import net.entetrs.xenon.helpers.TextureLib;

public class Ship
{

	private Sprite shipSpriteReactorOn;
	private Sprite shipSpriteLeft;
	private Sprite shipSpriteRight;
	private Sprite shipSpriteReactorOff;

	private Sprite currentSprite;

	private Sprite shieldSprite;
	private boolean shieldActivated = false;

	private static float SHIP_SPEED = 400f;

	private float vX = 0;
	private float vY = 0;
	private float accel = 20f;

	public Ship()
	{
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

	public float getCenterX()
	{
		return GdxCommons.getCenterX(currentSprite);
	}

	public float getCenterY()
	{
		return GdxCommons.getCenterY(currentSprite);
	}

	public boolean isShieldActivated()
	{
		return shieldActivated;
	}

	public void render(float delta, SpriteBatch batch)
	{
		currentSprite.draw(batch);
		if (shieldActivated)
		{
			shieldSprite.setCenter(this.getCenterX(), this.getCenterY());
			shieldSprite.draw(batch);
		}
	}

	public void checkShield(float delta)
	{
		if (Gdx.input.isKeyJustPressed(Keys.ENTER))
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
	}

	public void checkShipMoves(float delta)
	{
		boolean keyMove = false;
		keyMove = this.checkVerticalMove(delta);
		keyMove = this.checkHorizontalMove(delta);

		if (!keyMove)
		{
			handleInertia();
		}

		if (!keyMove && currentSprite != shipSpriteReactorOn)
		{
			this.changeCurrentSprite(shipSpriteReactorOn);
		}

		currentSprite.translateX(delta * vX);
		currentSprite.translateY(delta * vY);

		controlPosition();
	}

	private void controlPosition()
	{
		if (currentSprite.getX() < 0) currentSprite.setX(0);
		if (currentSprite.getY() < 80) currentSprite.setY(80);
		if (currentSprite.getX() > C.WIDTH - currentSprite.getWidth()) currentSprite.setX(C.WIDTH - currentSprite.getWidth());
		if (currentSprite.getY() > C.HEIGHT - currentSprite.getHeight()) currentSprite.setY(C.HEIGHT - currentSprite.getHeight());
	}

	private void handleInertia()
	{
		// inertie sur X
		if (vX > 0)
		{
			vX -= accel / 2;
		}
		else if (vX < 0)
		{
			vX += accel / 2;
		}

		// inertie sur Y
		if (vY > 0)
		{
			vY -= accel / 2;
		}
		else if (vY < 0)
		{
			vY += accel / 2;
		}
	}

	private boolean checkHorizontalMove(float delta)
	{

		// précondition au mouvement : que les 2 touches ne soient pas enfoncées
		if (GdxCommons.checkConcurrentKeys(Keys.LEFT, Keys.RIGHT)) return false;

		boolean keyMove = false;
		if (Gdx.input.isKeyPressed(Keys.LEFT))
		{
			keyMove = true;
			this.changeCurrentSprite(shipSpriteLeft);
			vX -= accel;
			vX = (vX < -SHIP_SPEED) ? -SHIP_SPEED : vX;
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			keyMove = true;
			this.changeCurrentSprite(shipSpriteRight);
			vX += accel;
			vX = (vX > SHIP_SPEED) ? SHIP_SPEED : vX;
		}
		return keyMove;
	}

	private boolean checkVerticalMove(float delta)
	{

		// précondition au mouvement : que les 2 touches ne soient pas enfoncées
		if (GdxCommons.checkConcurrentKeys(Keys.UP, Keys.DOWN)) return false;

		boolean keyMove = false;
		if (Gdx.input.isKeyPressed(Keys.UP))
		{
			keyMove = true;
			this.changeCurrentSprite(shipSpriteReactorOn);
			vY += accel;
			vY = (vY > SHIP_SPEED) ? SHIP_SPEED : vY;
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN))
		{
			keyMove = true;
			this.changeCurrentSprite(shipSpriteReactorOff);
			vY -= accel;
			vY = (vY < -SHIP_SPEED) ? -SHIP_SPEED : vY;
		}
		return keyMove;
	}

	private void changeCurrentSprite(Sprite newSprite)
	{
		float x = this.getCenterX();
		float y = this.getCenterY();
		currentSprite = newSprite;
		currentSprite.setCenter(x, y);
	}

	public Sprite getShieldSprite()
	{
		return shieldSprite;
	}

	public Sprite getShipSprite()
	{
		return currentSprite;
	}

	public float getWidth()
	{
		return currentSprite.getWidth();
	}

	public float getX()
	{
		return currentSprite.getX();
	}

	public float getY()
	{
		return currentSprite.getY();
	}

}
