package net.entetrs.xenon.artefacts.friendly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import net.entetrs.xenon.artefacts.friendly.ShipInput.Horizontal;
import net.entetrs.xenon.artefacts.friendly.ShipInput.Vertical;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.utils.GdxCommons;

/**
 * cette classe change l'état du vaisseau en fonction des entrées du clavier.
 * cette classe génère les tirs.
 * 
 * @author robin
 *
 */
public final class ShipHandler
{
	private static Horizontal hControl;
	private static Vertical vControl;
	
	private ShipHandler()
	{
		// protection
	}
	
	public static Horizontal getHorizontalControl()
	{
		return hControl;
	}
	
	public static Vertical getVerticalControl()
	{
		return vControl;
	}
	
	public static void handle(Ship ship,float delta)
	{
		checkVerticalMove(ship);
		checkHorizontalMove(ship);
		checkShield(ship);
		checkSecondWeapon(ship);
		handleInertia(ship);
	}
	
	private static void checkVerticalMove(Ship ship)
	{
		vControl = Vertical.NONE;
		/* précondition au mouvement : que les 2 touches ne soient pas enfoncées */
		if (GdxCommons.checkConcurrentKeys(Keys.UP, Keys.DOWN)) return;
		
		float vY=ship.getVectorY();

		if (Gdx.input.isKeyPressed(Keys.UP))
		{
			vControl = Vertical.UP;
			vY += Global.SHIP_ACCELLERATION;
			ship.setVectorY(vY > Global.SHIP_SPEED ? Global.SHIP_SPEED : vY);
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN))
		{
			vControl = Vertical.DOWN;
			vY -= Global.SHIP_ACCELLERATION;
			ship.setVectorY(vY < -Global.SHIP_SPEED ? -Global.SHIP_SPEED : vY);
		}
	}
	
	private static void checkHorizontalMove(Ship ship)
	{
		hControl = Horizontal.NONE;

		/* précondition au mouvement : que les 2 touches ne soient pas enfoncées */
		if (GdxCommons.checkConcurrentKeys(Keys.LEFT, Keys.RIGHT)) return;
		
		float vX=ship.getVectorX();

		if (Gdx.input.isKeyPressed(Keys.LEFT))
		{
			hControl = Horizontal.LEFT;
			vX -= Global.SHIP_ACCELLERATION;
			ship.setVectorX(vX < -Global.SHIP_SPEED ? -Global.SHIP_SPEED : vX);
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			hControl = Horizontal.RIGHT;
			vX += Global.SHIP_ACCELLERATION;
			ship.setVectorX(vX > Global.SHIP_SPEED ? Global.SHIP_SPEED : vX);
		}
	}


	private static void handleInertia(Ship ship)
	{
		float vX=ship.getVectorX();
		float vY=ship.getVectorY();
		
		if (hControl == Horizontal.NONE)
		{
			ship.setVectorX(computeInertia(vX));
		}

		if (vControl == Vertical.NONE)
		{
			ship.setVectorY(computeInertia(vY));
		}
	}

	private static float computeInertia(float v)
	{
		float result = v;
		if (v > 0)
		{
			result -= Global.SHIP_ACCELLERATION / 2;
		}
		else if (v < 0)
		{
			result += Global.SHIP_ACCELLERATION / 2;
		}
		return result;
	}
	
	private static void checkShield(Ship ship)
	{
		if (Gdx.input.isKeyJustPressed(Keys.ENTER))
		{
			ship.switchShield();
		}
	}	
	
	private static void checkSecondWeapon(Ship ship)
	{
		if (Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
		{
			ship.weaponCharge();
		}
		else
		{
			ship.getSecondaryWeapon().fullDischarge();
		}
		
	}
}
