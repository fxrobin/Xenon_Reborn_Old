package net.entetrs.xenon.commons.utils;

import java.net.URL;
import java.util.Properties;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.quippy.javamod.mixer.Mixer;
import de.quippy.javamod.multimedia.MultimediaContainer;
import de.quippy.javamod.multimedia.MultimediaContainerManager;
import de.quippy.javamod.multimedia.mod.ModContainer;
import de.quippy.javamod.system.Helpers;

/**
 * ModPlayer (music AMIGA / ATARI-ST). Refonte basée sur JavaMod (MOD, XM, S3M,
 * etc.)
 * 
 * @see <a href="http://www.javamod.de/">http://www.javamod.de/</a>
 * 
 * @author robin
 *
 */
public final class ModPlayer
{
	private static Log log = LogFactory.getLog(ModPlayer.class);

	private static Mixer mixer;
	private static String resourceName;

	static
	{
		try
		{
			Helpers.registerAllClasses();
			Properties props = new Properties();
			props.setProperty(ModContainer.PROPERTY_PLAYER_ISP, "3");
			props.setProperty(ModContainer.PROPERTY_PLAYER_STEREO, "2");
			props.setProperty(ModContainer.PROPERTY_PLAYER_WIDESTEREOMIX, "0");
			props.setProperty(ModContainer.PROPERTY_PLAYER_NOISEREDUCTION, "0");
			props.setProperty(ModContainer.PROPERTY_PLAYER_NOLOOPS, "1");
			props.setProperty(ModContainer.PROPERTY_PLAYER_MEGABASS, "1");
			props.setProperty(ModContainer.PROPERTY_PLAYER_BITSPERSAMPLE, "16");
			props.setProperty(ModContainer.PROPERTY_PLAYER_FREQUENCY, "48000");
			MultimediaContainerManager.configureContainer(props);
		}
		catch (ClassNotFoundException e)
		{
			if (log.isErrorEnabled())
			{
				log.error("Impossible d'instancier JavaMod", e);
			}
		}
	}

	private ModPlayer()
	{

	}

	/**
	 * charge un module placé en ressources.
	 * 
	 * @param musicNameResource
	 */
	private static void loadAndPlay(String musicNameResource)
	{
		try
		{
			resourceName = musicNameResource;
			URL modUrl = ModPlayer.class.getClassLoader().getResource(musicNameResource);
			if (log.isInfoEnabled()) log.info("Chargement de " + modUrl);
			MultimediaContainer multimediaContainer = MultimediaContainerManager.getMultimediaContainer(modUrl);
			mixer = multimediaContainer.createNewMixer();
			mixer.startPlayback();
		}
		catch (UnsupportedAudioFileException e)
		{
			if (log.isErrorEnabled())
			{
				log.error("Impossible d'instancier JavaMod", e);
			}
		}

	}

	/**
	 * lance la lecture du module sous forme de Thread daemon.
	 */
	public static void play(String musicNameResource)
	{

		Thread t = new Thread(() -> ModPlayer.loadAndPlay(musicNameResource));
		t.setDaemon(true);
		t.start();
		if (log.isInfoEnabled()) log.info("Playing " + getMusicName());

	}

	/**
	 * arrête la lecture du module.
	 */
	public static void stop()
	{
		if (mixer != null)
		{
			mixer.stopPlayback();
		}

	}

	/**
	 * @return le nom du module courant chargé.
	 */
	public static String getMusicName()
	{
		return (mixer == null) ? "NO MODULE" : resourceName;
	}

}
