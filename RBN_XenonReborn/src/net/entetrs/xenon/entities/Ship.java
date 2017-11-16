package net.entetrs.xenon.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.commons.GdxCommons;
import net.entetrs.xenon.commons.Renderable;
import net.entetrs.xenon.entities.ShipControl.Horizontal;
import net.entetrs.xenon.entities.ShipControl.Vertical;
import net.entetrs.xenon.libs.SoundLib;
import net.entetrs.xenon.renderers.ShipRenderer;

public class Ship implements Renderable
{
	private static final float SHIP_SPEED = 400f;
	private static final float SHIP_ACCELLERATION = 20f;

	private ShipRenderer shipRenderer;

	private ShipControl.Horizontal hControl = ShipControl.Horizontal.NONE;
	private ShipControl.Vertical vControl = ShipControl.Vertical.NONE;

	private boolean shieldActivated = false;

	private float vX = 0;
	private float vY = 0;

	private Circle boundingCircle;

	public Ship()
	{
		boundingCircle = new Circle();
		shipRenderer = new ShipRenderer(this);
		boundingCircle.setRadius(shipRenderer.getCurrentSprite().getWidth() / 2);
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
	public void render(Batch batch, float delta)
	{
		shipRenderer.render(batch, delta);
	}

	public void checkShield()
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
		boolean keyMove = this.checkVerticalMove();
		keyMove = this.checkHorizontalMove() ? true : keyMove;

		if (!keyMove)
		{
			handleInertia();
		}

		shipRenderer.getCurrentSprite().translateX(delta * vX);
		shipRenderer.getCurrentSprite().translateY(delta * vY);

		controlPosition();
	}

	private void controlPosition()
	{
		Sprite currentSprite = shipRenderer.getCurrentSprite();

		if (currentSprite.getX() < 0) currentSprite.setX(0);
		if (currentSprite.getY() < 80) currentSprite.setY(80);
		if (currentSprite.getX() > C.WIDTH - currentSprite.getWidth()) currentSprite.setX(C.WIDTH - currentSprite.getWidth());
		if (currentSprite.getY() > C.HEIGHT - currentSprite.getHeight()) currentSprite.setY(C.HEIGHT - currentSprite.getHeight());

		boundingCircle.setX(this.getCenterX());
		boundingCircle.setY(this.getCenterY());
	}

	private void handleInertia()
	{
		// inertie sur X
		if (vX > 0)
		{
			vX -= SHIP_ACCELLERATION / 2;
		}
		else if (vX < 0)
		{
			vX += SHIP_ACCELLERATION / 2;
		}

		// inertie sur Y
		if (vY > 0)
		{
			vY -= SHIP_ACCELLERATION / 2;
		}
		else if (vY < 0)
		{
			vY += SHIP_ACCELLERATION / 2;
		}
	}

	private boolean checkHorizontalMove()
	{
		boolean keyMove = false;
		this.vControl = Vertical.NONE;
		this.hControl = Horizontal.NONE;

		// précondition au mouvement : que les 2 touches ne soient pas enfoncées
		if (GdxCommons.checkConcurrentKeys(Keys.LEFT, Keys.RIGHT)) return false;

		if (Gdx.input.isKeyPressed(Keys.LEFT))
		{
			this.hControl = Horizontal.LEFT;
			keyMove = true;
			vX -= SHIP_ACCELLERATION;
			vX = (vX < -SHIP_SPEED) ? -SHIP_SPEED : vX;
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			this.hControl = Horizontal.RIGHT;
			keyMove = true;
			vX += SHIP_ACCELLERATION;
			vX = (vX > SHIP_SPEED) ? SHIP_SPEED : vX;
		}
		return keyMove;
	}

	private boolean checkVerticalMove()
	{

		// précondition au mouvement : que les 2 touches ne soient pas enfoncées
		if (GdxCommons.checkConcurrentKeys(Keys.UP, Keys.DOWN)) return false;

		boolean keyMove = false;
		if (Gdx.input.isKeyPressed(Keys.UP))
		{
			keyMove = true;
			vY += SHIP_ACCELLERATION;
			vY = (vY > SHIP_SPEED) ? SHIP_SPEED : vY;
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN))
		{
			keyMove = true;
			vY -= SHIP_ACCELLERATION;
			vY = (vY < -SHIP_SPEED) ? -SHIP_SPEED : vY;
		}
		return keyMove;
	}

	public float getWidth()
	{
		return shipRenderer.getCurrentSprite().getWidth();
	}

	public float getX()
	{
		return shipRenderer.getCurrentSprite().getX();
	}

	public float getY()
	{
		return shipRenderer.getCurrentSprite().getY();
	}

	public ShipControl.Horizontal gethControl()
	{
		return hControl;
	}

	public ShipControl.Vertical getvControl()
	{
		return vControl;
	}
}
