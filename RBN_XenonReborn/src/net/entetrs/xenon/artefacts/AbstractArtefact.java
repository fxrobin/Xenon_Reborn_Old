package net.entetrs.xenon.artefacts;

import net.entetrs.xenon.commons.displays.Renderable;

/**
 * représente un artefact de base doté d'une force de vie (lifeForce) et d'une
 * force d'impact (impactForce).
 * 
 * @author robin
 *
 */
public abstract class AbstractArtefact implements Artefact, Renderable
{
	/**
	 * points de vie de l'artefact.
	 */
	private int lifePoints;
	
	/**
	 * force d'impact à appliquer lors des collisions.
	 */
	private final int impactForce;

	/**
	 * construit un artefact doté d'une force de vie (lifeForce) et d'une force
	 * d'impact (impactForce).
	 * 
	 * @param lifeForce
	 * @param impactForce
	 */
	public AbstractArtefact(final int lifeForce, final int impactForce)
	{
		this.lifePoints = lifeForce;
		this.impactForce = impactForce;

	}

	/**
	 * déplace le sprite associé sur l'axe des X.
	 * 
	 * @param deltaX
	 *            décallage à appliquer.
	 */
	public void translateX(final float deltaX)
	{
		this.getSprite().translateX(deltaX);
	}

	/**
	 * déplace le sprite associé sur l'axe des Y.
	 * 
	 * @param deltaY
	 *            décallage à appliquer.
	 */
	public void translateY(final float deltaY)
	{
		this.getSprite().translateY(deltaY);
	}

	/**
	 * décrémente la vie d'artefacte.
	 */
	@Override
	public void decreaseLife(final int force)
	{
		this.lifePoints -= force;
	}

	/**
	 * retourne la valeur de la force d'impact de l'artefact.
	 */
	@Override
	public int getImpactForce()
	{
		return impactForce;
	}

	/**
	 * retourne "true" si l'artefact est toujours vivant.
	 */
	@Override
	public boolean isAlive()
	{
		return lifePoints > 0;
	}
}
