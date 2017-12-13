package net.entetrs.xenon.screens;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * tout contrôleur doit pouvoir afficher un écran.
 * 
 * @author robin
 *
 */
public interface XenonControler
{
	/**
	 * affiche un écran.
	 * 
	 * @param screen
	 */
	void showScreen(XenonScreen screen);

	/**
	 *  @return instance d'un shapeRender pour afficher des formes.
	 */
	ShapeRenderer getShapeRenderer();
}
