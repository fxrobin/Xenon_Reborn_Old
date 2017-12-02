package net.entetrs.xenon.managers;

import java.util.List;

import net.entetrs.xenon.artefacts.Artefact;

public final class CollisionManager
{

	private static CollisionManager instance = new CollisionManager();

	public static CollisionManager getInstance()
	{
		return instance;
	}

	private CollisionManager()
	{
		/* protection */
	}

	public void checkCollision(List<? extends Artefact> targets, List<? extends Artefact> projectiles)
	{
		for (Artefact t : targets)
		{
			for (Artefact p : projectiles)
			{
				if (p.getBoundingCircle().overlaps(t.getBoundingCircle()))
				{
					/* collision !!! */
					p.decreaseLife(t.getImpactForce());
					t.decreaseLife(p.getImpactForce());

					/* MAJ du score */
					if (!t.isAlive())
					{
						ScoreManager.getInstance().add(10);
					}
				}
			}

		}
	}

}
