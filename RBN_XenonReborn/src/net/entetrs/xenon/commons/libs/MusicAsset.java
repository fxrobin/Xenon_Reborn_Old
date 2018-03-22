package net.entetrs.xenon.commons.libs;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

import net.entetrs.xenon.commons.utils.GdxCommons;

public enum MusicAsset implements Disposable
{
	INTRO_SOUND("intro-sound.mp3"), INTRO("intro.mp3"), MUSIC("music.mp3");

	private final String fileName;
	private Music music;

	private MusicAsset(String fileName)
	{
		this.fileName = "musics/" + fileName;
	}

	@Override
	public String toString()
	{
		return this.fileName;
	}

	public Music getMusic()
	{
		if (music == null)
		{
			music = AssetLib.assetLib.get(this, Music.class);
		}
		return music;
	}

	public void play()
	{
		this.getMusic().play();
	}

	public void stop()
	{
		this.getMusic().stop();
	}

	public void fadeOut()
	{
		Timer.schedule(new Timer.Task()
		{

			private float MUSIC_FADE_STEP = 0.01f;

			@Override
			public void run()
			{
				if (music.getVolume() >= MUSIC_FADE_STEP)
					music.setVolume(music.getVolume() - MUSIC_FADE_STEP);
				else
				{
					music.stop();
					this.cancel();
				}
			}
		}, 0f, 0.01f);
	}

	public void fadeIn()
	{
		music.setVolume(0);
		music.play();

		Timer.schedule(new Timer.Task()
		{

			private float MUSIC_FADE_STEP = 0.01f;

			@Override
			public void run()
			{
				if (music.getVolume() < 1.0)
					music.setVolume(music.getVolume() + MUSIC_FADE_STEP);
				else
				{

					this.cancel();
				}
			}
		}, 0f, 0.01f);
	}

	public void loop()
	{
		this.getMusic().setLooping(true);
		this.play();
	}

	public void loop(float f)
	{
		this.getMusic().setVolume(f);
		this.loop();
	}

	public boolean isPlaying()
	{
		return this.getMusic().isPlaying();
	}

	@Override
	public void dispose()
	{
		if (music != null)
		{
			this.music.dispose();
		}
	}

	public static void disposeAll()
	{
		GdxCommons.disposeAll(MusicAsset.values());
	}

}