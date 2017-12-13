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
	public static int height = 576; /* NOSONAR */

	/**
	 * vitesse max de déplacement du vaisseau.
	 */
	public static final float SHIP_SPEED = 400f;

	
	/**
	 * accelération du vaisseau.
	 */
	public static final float SHIP_ACCELLERATION = 20f;

	/**
	 * bouclier : maximum 100%
	 */
	public static final float SHIELD_MAX_ENERGY = 100f;

	/**
	 * bouclier : vitesse de chargement du bouclier. (20f = 5 secondes)
	 */
	public static final float SHIELD_CHARGING_SPEED = 20f;

	/**
	 * bouclier :  vitesse de décharge du bouclier (10f = 10 secondes)
	 */
	public static final float SHIELD_DISCHARGING_SPEED = 10f;

	/**
	 * bouclier : seuil d'activation potentiel du bouclier. (90%)
	 */
	public static final float SHIELD_ACTIVATION_THRESHOLD = 90f;

	private Global()
	{
		/* protection, empèche l'instaciation depuis l'extérieur */
	}
}
