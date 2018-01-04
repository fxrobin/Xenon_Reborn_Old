package net.entetrs.xenon.artefacts.enemies;

import net.entetrs.xenon.artefacts.Artefact;
import net.entetrs.xenon.artefacts.friendly.Ship;
import net.entetrs.xenon.commons.libs.TextureAsset;

public class Bullet extends Enemy
{
	public Bullet()
	{
		super(TextureAsset.BULLET.get(), 5, 5, 8);
	}

	@Override
	public boolean isCollision(Artefact otherArtefact)
	{
        /* collision uniquement avec le vaisseau */
		return otherArtefact instanceof Ship ? super.isCollision(otherArtefact) : false;
	}
}
