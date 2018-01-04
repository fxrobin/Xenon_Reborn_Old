package net.entetrs.xenon.commons.libs;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import net.entetrs.xenon.commons.utils.GdxCommons;

public enum SoundAsset implements Disposable
{
	SHOOT("shoot.mp3"), BIG_SHOOT("big-shoot.wav"), SHIELD_UP("shield_up.wav"), SHIELD_DOWN("shield_down.wav"), SHIELD_ACTIVATED("shield-activated.mp3"), CLIC("clic.wav"), EXPLOSION("explosion.wav"), BONUS("bonus.wav");

	private final String fileName;
	private Sound sound;

	private SoundAsset(String fileName)
	{
		this.fileName = "sounds/" + fileName;
	}

	@Override
	public String toString()
	{
		return this.fileName;
	}

	public Sound getSound()
	{
		if (sound == null)
		{
			sound = AssetLib.assetLib.get(this, Sound.class);
		}
		return sound;
	}

	public void play()
	{
		this.getSound().play();
	}

	public void stop()
	{
		this.getSound().stop();
	}

	public void loop()
	{
		this.getSound().loop();
	}

	public void loop(float f)
	{
		this.getSound().loop(f);
	}

	@Override
	public void dispose()
	{
		if (sound != null)
		{
			this.sound.dispose();
		}
	}

	public static void disposeAll()
	{
		GdxCommons.disposeAll(SoundAsset.values());
	}

}