package net.entetrs.xenon.commons;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.entetrs.xenon.MainControler;

public class Launcher
{

	private static int W = C.WIDTH;
	private static int H = C.HEIGHT;

	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = W;
		config.height = H;
		config.forceExit = false;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.title = "XENON - Reborn - ETRS 2017";
		// config.allowSoftwareMode=true;
		new LwjglApplication(MainControler.getInstance(), config);
	}
}
