package net.entetrs.xenon.managers;

import java.util.List;

import net.entetrs.xenon.entities.Entity;

public class CollisionManager {
	
	public static void checkCollision(List <? extends Entity> targets, List <? extends Entity> projectiles)
	{
		for(Entity t : targets)
		{
			for(Entity p : projectiles)
			{
				if (p.getCircle().overlaps(t.getCircle()))
				{
					// collision !!!
					p.decreaseLife(t.getImpactForce());
					t.decreaseLife(p.getImpactForce());
				}
			}
		}
	}

}
