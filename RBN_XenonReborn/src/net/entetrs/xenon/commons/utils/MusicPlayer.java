package net.entetrs.xenon.commons.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;

public class MusicPlayer
{
	private static MusicPlayer player = new MusicPlayer();
	
	public static MusicPlayer getPlayer()
	{
		return player;
	}
	
	private Music music;
	
	public void play(Music music)
	{
		this.music = music;
		music.play();
	}

	public void stop()
	{
		music.stop();
	}

	public void fadeOut()
	{
		Timer.schedule(new Timer.Task()
		{

			private float MUSIC_FADE_STEP = 0.01f;

			@Override
			public void run()
			{
				if (music!=null && music.getVolume() >= MUSIC_FADE_STEP)
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

	public void loop(Music music)
	{
		music.setLooping(true);
		this.play(music);
	}

	public void loop(Music music, float f)
	{
		music.setVolume(f);
		this.loop(music);
	}

	public boolean isPlaying()
	{
		return music.isPlaying();
	}

}
