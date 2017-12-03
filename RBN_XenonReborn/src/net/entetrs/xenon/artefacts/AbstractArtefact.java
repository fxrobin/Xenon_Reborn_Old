package net.entetrs.xenon.artefacts;

import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.commons.displays.Renderable;
import net.entetrs.xenon.commons.utils.GdxCommons;

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
	 * vitesse de dépaclement sur l'axe des X.
	 */
	private float vectorX;
	
	/**
	 * vitesse de déplacement sur l'axe des Y.
	 */
	private float vectorY;
	
	/**
	 * cercle de détection des collisions.
	 * 
	 */
	private Circle boundingCircle;

	/**
	 * construit un artefact doté d'une force de vie (lifeForce) et d'une force
	 * d'impact (impactForce).
	 * 
	 * @param lifeForce
	 * @param impactForce
	 */
	public AbstractArtefact(final float vectorX, final float vectorY, final int lifeForce, final int impactForce)
	{
		this.lifePoints = lifeForce;
		this.impactForce = impactForce;
		this.vectorX = vectorX;
		this.vectorY = vectorY;
		boundingCircle = new Circle();
	}

	/**
	 * déplace le sprite associé sur l'axe des X.
	 * 
	 * @param deltaX
	 *            décallage à appliquer.
	 */
	private void translateX(final float deltaX)
	{
		this.getSprite().translateX(deltaX);
	}

	/**
	 * déplace le sprite associé sur l'axe des Y.
	 * 
	 * @param deltaY
	 *            décallage à appliquer.
	 */
	private void translateY(final float deltaY)
	{
		this.getSprite().translateY(deltaY);
	}
	
	
	@Override
	public void act(float delta)
	{
		this.translateX(delta * vectorX);
		this.translateY(delta * vectorY);
		GdxCommons.computeBoundingCircle(getSprite(), getBoundingCircle());
	}

	/**
	 * décrémente la vie d'artefact.
	 */
	@Override
	public void decreaseLife(final int force)
	{
		this.lifePoints -= force;
	}
	
	/**
	 * retourne le nombre de point de vie restant à l'artefact. 
	 */
	@Override
	public int getLifePoints()
	{
		return lifePoints;
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
	
	@Override
	public float getVectorX()
	{
		return vectorX;
	}
	
	@Override
	public float getVectorY()
	{
		return vectorY;
	}
	
	@Override
	public void setVectorX(float vectorX)
	{
		this.vectorX = vectorX;
	}
	
	@Override
	public void setVectorY(float vectorY)
	{
		this.vectorY = vectorY;
	}
	
	@Override
	public void setLifePoints(int lifePoints)
	{
		this.lifePoints = lifePoints;
	}
	
	@Override
	public Circle getBoundingCircle()
	{
		return this.boundingCircle;
	}
	
	@Override
	public void setRadius(float radius)
	{
		this.boundingCircle.setRadius(radius);	
	}
	
	@Override
	public boolean isCollision(Artefact otherArtefact)
	{
		return this.getBoundingCircle().overlaps(otherArtefact.getBoundingCircle());
	}
	
	
}
