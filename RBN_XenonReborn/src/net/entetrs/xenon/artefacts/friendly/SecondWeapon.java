package net.entetrs.xenon.artefacts.friendly;

import net.entetrs.xenon.commons.Global;

/**
 * <p>
 * représente bouclier et son énergie. L'energie va de 100f : chargé au max, 0f déchargé. 
 * En dessous de 90f il n'est pas activable A 0.f il se désactive automatiquement.
 * </p>
 * 
 * <p>
 * Toutes les secondes le bouclier prend 20% en recharge. Toutes les secondes le
 * bouclier prend 10% en décharge.
 * </p>
 */

public class SecondWeapon
{
	/**
	 * énergie courante du bouclier
	 */
	private float energy = 0f;

	/**
	 * bouclier activé ou non.
	 * 
	 */
	private boolean activated;
	
	private boolean ready;

	/**
	 * met à jour l'état du bouclier en fonction de "delta".
	 * 
	 * @param delta
	 */
	public void update(float delta)
	{
		if (!activated)
		{
			this.decharger(delta);
		}
		else
		{
			this.recharger(delta);
		}
		
		activated = false;
	}

	private void recharger(float delta)
	{
		energy += (delta * Global.WEAPON_CHARGING_SPEED);
		energy = (energy > 100) ? 100 : energy;
		if (!ready)
		{
		 ready = (energy >= 100);
		}
	}
	
	public void fire()
	{
		ready = false;
	}

	private void decharger(float delta)
	{
		energy = 0f;
	}

	/**
	 * active ou désactive le bouclier en fonction de son état. si le niveau
	 * d'énergie est inférieur à 90%, il ne peut pas être activé.
	 * 
	 */
	public void charge()
	{
		activated = true;
	}


	/**
	 * @return état du bouclier activé ou non (true or false).
	 */
	public boolean isActivated()
	{
		return activated;
	}
	
	public boolean isReady()
	{
		return ready;
	}

	/**
	 * @return le niveau d'énergie du bouclier (max 100f à min 0f)
	 */
	public float getEnergy()
	{
		return this.energy;
	}

}
