package net.entetrs.xenon.commons.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.vwp.sound.mod.modplay.ThreadedPlayer;
import com.vwp.sound.mod.modplay.loader.InvalidFormatException;
import com.vwp.sound.mod.modplay.loader.ModuleLoader;
import com.vwp.sound.mod.modplay.module.Module;
import com.vwp.sound.mod.modplay.player.PlayerException;
import com.vwp.sound.mod.sound.output.JavaSoundOutput;
import com.vwp.sound.mod.sound.output.SoundDataFormat;

/**
 * façade pour la lecture de fichier "MOD" ou "XM" (Musique). Les .MOD ou .XM
 * sont des format de musiques "anciens" mais de petite taille, fondés sur la
 * technique des sons "samplés" et des séquenceurs. Ils étaient très en vogue
 * sur ATARI ST et AMIGA avant l'avénement du MP3.
 *
 * @author CDT ROBIN
 */
public final class ModPlayer
{

	private ModPlayer()
	{
		// protection.
	}

	private static ThreadedPlayer player = new ThreadedPlayer();
	private static double volume;
	private static String musicName;

	/**
	 * permet de savoir si un module est en cours de lecture.
	 *
	 * @return true si une musique est en cours de diffusion, false dans le cas
	 *         contraire.
	 */
	public static boolean isPlaying()
	{
		return ModPlayer.player.isRunning();
	}

	/**
	 * allume ou éteint la diffusion Audio.
	 */
	public static void switchOnOff()
	{
		if (ModPlayer.isPlaying())
		{
			ModPlayer.stopFadeOut();
		}
		else
		{
			ModPlayer.playFadeIn();
		}
	}

	/**
	 * charge un fichier "module" présent en ressource.
	 *
	 * @param musicNameResource
	 *            nom du module, <b>avec</b> l'extension !
	 */
	public static void load(final String musicNameResource)
	{
		try (InputStream in = Gdx.files.internal(musicNameResource).read())
		{
			ModPlayer.musicName = musicNameResource;
			ModPlayer.load(in);
		}
		catch (IOException | InvalidFormatException | PlayerException ex)
		{
			System.err.println("Impossible de lire la ressource : " + musicNameResource);
			System.err.println(ex);
		}

	}

	/**
	 * charge un fichier "module" ouvert par un InputStream.
	 *
	 * @param inputStream
	 *            flux d'entrée.
	 *
	 * @throws InvalidFormatException
	 *             levée si le format est invalide.
	 * @throws IOException
	 *             levée si le flux n'a pas pu être lu.
	 * @throws PlayerException
	 *             levée si le lecteur n'a pas pu s'instancier.
	 *
	 */
	public static void load(final InputStream inputStream) throws InvalidFormatException, IOException, PlayerException
	{
		final ModuleLoader ml = ModuleLoader.getModuleLoader(inputStream, ModPlayer.musicName);
		final Module module = ml.getModule();
		ModPlayer.player.init(new JavaSoundOutput(new SoundDataFormat(16, 44100, 2), 200), true);
		ModPlayer.player.load(module);
	}

	/**
	 * charge un fichier "module" passé sous forme d'instance de File.
	 *
	 * @param file
	 *            référence vers un fichier.
	 */
	public static void load(final File file)
	{
		try (InputStream in = new BufferedInputStream(new FileInputStream(file));)
		{
			ModPlayer.musicName = file.getName();
			ModPlayer.load(in);
		}
		catch (IOException | InvalidFormatException | PlayerException ex)
		{
			System.err.println("Impossible de lire le fichier : " + file);
		}
	}

	/**
	 * retourne le titre du module.
	 *
	 * @return titre du module.
	 */
	public static String getMusicName()
	{
		return ModPlayer.player.getModule().getName();
	}

	/**
	 * diffuse la musique et effectue une montée progressive du volume.
	 */
	public static void playFadeIn()
	{
		ModPlayer.player.setVolume(0);
		ModPlayer.player.start();
		ModPlayer.fadeIn();
	}

	/**
	 * diffuse la musique directement.
	 */
	public static void playNormal()
	{
		ModPlayer.player.setVolume(100);
		ModPlayer.player.start();
	}

	/**
	 * arrête la musique directement.
	 */
	public static void stopNormal()
	{
		ModPlayer.player.stop();
	}

	/**
	 * arrête la musique en effectuant une descente progressive du volume.
	 */
	public static void stopFadeOut()
	{
		ModPlayer.fadeOut();
	}

	/**
	 * réduit le volume sonore à zéro, mais la diffusion continue.
	 */
	public static void switchMute()
	{
		if (ModPlayer.volume != 0)
		{
			ModPlayer.volume = ModPlayer.player.getVolume();
			ModPlayer.player.setVolume(0);
		}
		else
		{
			ModPlayer.player.setVolume(ModPlayer.volume);
		}
	}

	/**
	 * baisse progressivement le volume.
	 */
	private static void fadeOut()
	{
		final Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				ModPlayer.volume = ModPlayer.volume / 1.5;
				if (ModPlayer.volume < 0.005f)
				{
					t.cancel();
					ModPlayer.volume = 0;
					ModPlayer.player.stop();
				}
				ModPlayer.player.setVolume(ModPlayer.volume);

			}
		}, new Date(), 300);
	}

	/**
	 * augmente progressivment le volume.
	 */
	private static void fadeIn()
	{
		final Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				if (ModPlayer.volume == 0)
				{
					ModPlayer.volume = 0.005;
				}
				ModPlayer.volume = ModPlayer.volume * 1.5;
				if (ModPlayer.volume > 0.9f)
				{
					t.cancel();
					ModPlayer.volume = 1;
				}
				ModPlayer.player.setVolume(ModPlayer.volume);

			}
		}, new Date(), 300);
	}
}
