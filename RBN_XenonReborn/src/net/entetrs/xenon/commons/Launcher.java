package net.entetrs.xenon.commons;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.entetrs.xenon.MainControler;

public class Launcher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = C.WIDTH;
		config.height = C.HEIGHT;
		config.forceExit = false;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.title = "XENON - Reborn - ETRS 2017";
		new LwjglApplication(MainControler.getInstance(), config);
	}
}
