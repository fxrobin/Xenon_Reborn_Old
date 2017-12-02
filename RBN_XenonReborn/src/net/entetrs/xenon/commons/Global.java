package net.entetrs.xenon.commons;

/**
 * classes de constantes et de paramètrages de l'application.
 * la classe est nommée "C" même si elle ne contient pas que des
 * constantes.
 * 
 * @author robin
 *
 */

public final class Global
{
	/**
	 * durée du fade-in/fade-out.
	 */
	public static final float FADE_SECONDS = 1.5f;
	
	/**
	 * largeur de la surface de jeu en pixels.
	 */
	public static int width = 1024; /* NOSONAR */
	
	/**
	 * hauteur de la surface de jeu en pixels.
	 */
	public static int height = 768; /* NOSONAR */

	private Global()
	{
		/* protection */
	}
}
