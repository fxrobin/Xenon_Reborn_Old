package net.entetrs.xenon.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;

import micromod.Module;
import micromod.Player;

/**
 * ModPlayer (music AMIGA / ATARI-ST). Refonte basée sur micromod de Martin Cameron (plus stable que xtools).
 * 
 * @see <a href="https://github.com/martincameron/micromod">https://github.com/martincameron/micromod</a>
 * 
 * @author robin
 *
 */
public final class ModPlayer
{
	private static Log log = LogFactory.getLog(ModPlayer.class);
	
	private static Module module;
	private static Player player;
	
	private ModPlayer()
	{
		// protection
	}

	
    /**
     * charge un module placé en ressources.
     * 
     * @param musicNameResource
     */
	public static void load(String musicNameResource)
	{
		byte[] data = Gdx.files.internal(musicNameResource).readBytes();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
		player = null;
		try
		{
			module = new Module(inputStream);
			player = new Player(module, false, false);
		}
		catch (IOException e)
		{
			if (log.isErrorEnabled())
			{
				log.error("Impossible de lire le module", e);
			}
		}
	}

	/**
	 * lance la lecture du module sous forme de Thread daemon.
	 */
	public static void play()
	{
		if (player != null)
		{
			Thread t = new Thread(player);
			t.setDaemon(true);
			t.start();
			if (log.isInfoEnabled()) log.info("Playing " + getMusicName());
		}
	}
	
	/**
	 * arrête la lecture du module.
	 */
	public static void stop()
	{
		if (player != null)
		{
			player.stop();
		}

	}

	/**
	 * @return le nom du module courant chargé.
	 */
	public static String getMusicName()
	{
		return (module == null) ? "NO MODULE" : module.getSongName().trim();
	}

}
