package net.entetrs.xenon.artefacts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.commons.displays.Renderable;

/**
 * représente un artefact qui possède des points de vie, une force d'impact, un
 * cercle de collision et un sprite courant. Un artefact est "Renderable" par
 * héritage d'interface.
 * 
 * @author robin
 * @see Renderable
 */

public interface Artefact extends Renderable
{
	/**
	 * retourne le cercle de collision.
	 * 
	 * @return
	 */
	Circle getBoundingCircle();

	/**
	 * décrémente les points de vie en fonction de la force d'impact exercée.
	 * 
	 * @param force
	 *            force d'impact.
	 */
	void decreaseLife(int force);

	/**
	 * retourne la force d'impact de cet artefact.
	 * 
	 * @return la force d'impact.
	 */
	int getImpactForce();

	/**
	 * retourne "true" si l'artefact est toujours en vie, "false" sinon.
	 * 
	 * @return "true" si l'artefact est toujours en vie, "false" sinon.
	 */
	boolean isAlive();

	/**
	 * retourne le Sprite courant resprésenté par cet artefact.
	 * 
	 * @return le sprite courant.
	 */
	Sprite getSprite();
}
