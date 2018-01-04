package net.entetrs.xenon.commons.libs;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

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