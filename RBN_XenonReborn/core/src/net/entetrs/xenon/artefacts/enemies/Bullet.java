package net.entetrs.xenon.artefacts.enemies;

import net.entetrs.xenon.artefacts.Artefact;
import net.entetrs.xenon.artefacts.friendly.Ship;
import net.entetrs.xenon.commons.libs.TextureAsset;

/**
 * représente un "bullet", un tir de l'ennemi. Seule les collisions avec le
 * vaisseau du joueur sont prises en compte, c'est à dire qu'une bullet n'entre
 * pas en collision avec les tirs du vaisseau.
 * 
 * @author robin
 *
 */
public class Bullet extends Enemy
{
	public Bullet()
	{
		super(TextureAsset.BULLET.get(), 5, 5, 8);
	}

	/*
	 * redéfinition de la méthode "isCollision" pour ne rentrer en coolision
	 * qu'avec le vaisseau.
	 */

	@Override
	public boolean isCollision(Artefact otherArtefact)
	{
		/* une petite ternaire pour la route ... */
		return otherArtefact instanceof Ship ? super.isCollision(otherArtefact) : false;
	}
}
