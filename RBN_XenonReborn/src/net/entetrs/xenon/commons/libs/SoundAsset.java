package net.entetrs.xenon.commons.libs;

import com.badlogic.gdx.audio.Sound;

public enum SoundAsset
{
	INTRO_SOUND("intro-sound.mp3"),
	SHOOT("shoot.mp3"), 
	BIG_SHOOT("big-shoot.wav"), 
	SHIELD_UP("shield_up.wav"), 
	SHIELD_DOWN("shield_down.wav"), 
	CLIC("clic.wav"), 
	EXPLOSION("explosion.wav"), 
	INTRO("intro.mp3"), 
	MUSIC("music.mp3"),
	BONUS("bonus.wav");
	
	private final String fileName;
	private Sound sound;
	
	private SoundAsset(String fileName)
	{
		this.fileName = "sounds/" + fileName;
	}	
	
	public void play()
	{
		this.getSound().play();
	}
	
	@Override
	public String toString()
	{
		return this.fileName;
	}

	public void loop(float f)
	{
		this.getSound().loop(f);
	}

	public void stop()
	{
		this.getSound().stop();
	}

	public void loop()
	{
		this.getSound().loop();	
	}

	public Sound getSound()
	{
		if (sound == null)
		{
			sound = AssetLib.assetLib.get(this, Sound.class);
		}
		return sound;
	}
}