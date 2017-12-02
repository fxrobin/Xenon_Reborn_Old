package net.entetrs.xenon;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.screens.MainControler;

/**
 * lanceur principal de Xenon_Reborn. Ce lanceur configure Lwjgl et libgdx. Un
 * singleton (instance unique) de MainControler est obtenue et devient le point
 * d'entrée du jeu vidéo.
 * 
 * @author CDT RBN
 *
 */

public final class Launcher
{
	/**
	 * point d'entrée du jeu.
	 * 
	 * @param arg
	 */
	public static void main(final String... arg)
	{
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = C.width;
		config.height = C.height;
		config.forceExit = false;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.title = "XENON - Reborn - ETRS 2017";
		config.useGL30 = false;
		new LwjglApplication(MainControler.getInstance(), config);
	}

	private Launcher()
	{
		// protection.
	}
}
