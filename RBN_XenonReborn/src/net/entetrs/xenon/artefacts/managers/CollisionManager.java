package net.entetrs.xenon.artefacts.managers;

import java.util.List;
import java.util.Random;

import net.entetrs.xenon.artefacts.Artefact;
import net.entetrs.xenon.artefacts.extra.BonusType;

public final class CollisionManager
{
	private static CollisionManager instance = new CollisionManager();
	private static Random randomGenerator = new Random();

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
				if (p.isCollision(t))
				{
					/* collision !!! */
					p.decreaseLife(t.getImpactForce());
					t.decreaseLife(p.getImpactForce());

					/* MAJ du score */
					if (!t.isAlive())
					{
						ScoreManager.getInstance().add(10);
						// random bonus.
						if (randomGenerator.nextBoolean())
						{
							BonusManager.getInstance().addBonus(BonusType.NORMAL_BONUS, t.getBoundingCircle().x, t.getBoundingCircle().y);
						}
						
					}
				}
			}

		}
	}

}
