package net.entetrs.xenon.managers;

import java.util.List;

import net.entetrs.xenon.entities.Artefact;

public class CollisionManager {
	
	public static void checkCollision(List <? extends Artefact> targets, List <? extends Artefact> projectiles)
	{
		for(Artefact t : targets)
		{
			for(Artefact p : projectiles)
			{
				if (p.getBoundingCircle().overlaps(t.getBoundingCircle()))
				{
					// collision !!!
					p.decreaseLife(t.getImpactForce());
					t.decreaseLife(p.getImpactForce());
					
					// MAJ du score
					if (!t.isAlive())
					{
						ScoreManager.getInstance().add(10);
					}
				}
			}
		}
	}

}
