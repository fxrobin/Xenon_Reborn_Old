package net.entetrs.xenon.entities.friendly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.artefacts.AbstractArtefact;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.utils.GdxCommons;
import net.entetrs.xenon.entities.friendly.ShipInput.Horizontal;
import net.entetrs.xenon.entities.friendly.ShipInput.Vertical;
import net.entetrs.xenon.libs.SoundLib;

public class Ship extends AbstractArtefact
{
	private static final float SHIP_SPEED = 400f;
	private static final float SHIP_ACCELLERATION = 20f;

	private ShipRenderer shipRenderer;

	private ShipInput.Horizontal hControl = ShipInput.Horizontal.NONE;
	private ShipInput.Vertical vControl = ShipInput.Vertical.NONE;

	private boolean shieldActivated = false;

	private float vX = 0;
	private float vY = 0;

	private Circle boundingCircle;

	public Ship()
	{
		super(60, 20);
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
	public void render(SpriteBatch batch, float delta)
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
		this.checkVerticalMove();
		this.checkHorizontalMove();
		this.handleInertia();
		this.translateShip(delta);
	}

	private void translateShip(float delta)
	{
		shipRenderer.getCurrentSprite().translateX(delta * vX);
		shipRenderer.getCurrentSprite().translateY(delta * vY);
		this.controlPosition();
	}

	private void handleInertia()
	{
		if (this.hControl == Horizontal.NONE)
		{
			vX = this.computeInertia(vX);
		}

		if (this.vControl == Vertical.NONE)
		{
			vY = this.computeInertia(vY);
		}
	}

	private void controlPosition()
	{
		Sprite currentSprite = shipRenderer.getCurrentSprite();

		if (currentSprite.getX() < 0) currentSprite.setX(0);
		if (currentSprite.getY() < 80) currentSprite.setY(80);
		if (currentSprite.getX() > Global.width - currentSprite.getWidth()) currentSprite.setX(Global.width - currentSprite.getWidth());
		if (currentSprite.getY() > Global.height - currentSprite.getHeight()) currentSprite.setY(Global.height - currentSprite.getHeight());

		computeBoundingCircle();
	}

	private void computeBoundingCircle()
	{
		boundingCircle.setX(this.getCenterX());
		boundingCircle.setY(this.getCenterY());
	}

	private float computeInertia(float v)
	{
		float result = v;
		if (v > 0)
		{
			result -= SHIP_ACCELLERATION / 2;
		}
		else if (v < 0)
		{
			result += SHIP_ACCELLERATION / 2;
		}
		return result;
	}

	private void checkHorizontalMove()
	{
		this.hControl = Horizontal.NONE;

		/* précondition au mouvement : que les 2 touches ne soient pas enfoncées */
		if (GdxCommons.checkConcurrentKeys(Keys.LEFT, Keys.RIGHT)) return;

		if (Gdx.input.isKeyPressed(Keys.LEFT))
		{
			this.hControl = Horizontal.LEFT;
			vX -= SHIP_ACCELLERATION;
			vX = (vX < -SHIP_SPEED) ? -SHIP_SPEED : vX;
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			this.hControl = Horizontal.RIGHT;
			vX += SHIP_ACCELLERATION;
			vX = (vX > SHIP_SPEED) ? SHIP_SPEED : vX;
		}
	}

	private void checkVerticalMove()
	{
		this.vControl = Vertical.NONE;
		/* précondition au mouvement : que les 2 touches ne soient pas enfoncées */
		if (GdxCommons.checkConcurrentKeys(Keys.UP, Keys.DOWN)) return;

		if (Gdx.input.isKeyPressed(Keys.UP))
		{
			this.vControl = Vertical.UP;
			vY += SHIP_ACCELLERATION;
			vY = (vY > SHIP_SPEED) ? SHIP_SPEED : vY;
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN))
		{
			this.vControl = Vertical.DOWN;
			vY -= SHIP_ACCELLERATION;
			vY = (vY < -SHIP_SPEED) ? -SHIP_SPEED : vY;
		}
	}

	public float getWidth()
	{
		return this.getSprite().getWidth();
	}

	public float getX()
	{
		return this.getSprite().getX();
	}

	public float getY()
	{
		return this.getSprite().getY();
	}

	public ShipInput.Horizontal getHorizontalControl()
	{
		return hControl;
	}

	public ShipInput.Vertical getVerticalControl()
	{
		return vControl;
	}

	@Override
	public Sprite getSprite()
	{
		return this.shipRenderer.getCurrentSprite();
	}

	@Override
	public Circle getBoundingCircle()
	{
		return this.getBoundingCircle();
	}


}
