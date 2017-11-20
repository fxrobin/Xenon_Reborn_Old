package net.entetrs.xenon;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.entetrs.xenon.commons.C;

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
		config.useGL30=true;
		new LwjglApplication(MainControler.getInstance(), config);
	}
}
