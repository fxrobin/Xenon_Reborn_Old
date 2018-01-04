package net.entetrs.xenon.commons.libs;

import com.badlogic.gdx.audio.Music;

public enum MusicAsset
{
	INTRO_SOUND("intro-sound.mp3"),
	INTRO("intro.mp3"), 
	MUSIC("music.mp3");
	
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

}