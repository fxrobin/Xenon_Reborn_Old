package net.entetrs.xenon;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.entetrs.xenon.commons.R;

public class Launcher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = R.WIDTH;
		config.height = R.HEIGHT;
		config.forceExit = false;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.title = "XENON - Reborn - ETRS 2017";
		config.useGL30=false;
		new LwjglApplication(MainControler.getInstance(), config);
	}
}
