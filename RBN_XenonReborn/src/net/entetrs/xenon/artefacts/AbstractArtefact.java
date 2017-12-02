package net.entetrs.xenon.artefacts;

import net.entetrs.xenon.commons.displays.Renderable;

public abstract class AbstractArtefact implements Artefact, Renderable
{
	private int lifePoints;
	private final int impactForce;

	public AbstractArtefact(int lifeForce, int impactForce)
	{
		this.lifePoints = lifeForce;
		this.impactForce = impactForce;
		
	}

	public void translateX(float dx)
	{
		this.getSprite().translateX(dx);
	}

	public void translateY(float dy)
	{
		this.getSprite().translateY(dy);
	}

	@Override
	public void decreaseLife(int force)
	{
		this.lifePoints -= force;
	}

	@Override
	public int getImpactForce()
	{
		return impactForce;
	}

	@Override
	public boolean isAlive()
	{
		return lifePoints > 0;
	}
}
