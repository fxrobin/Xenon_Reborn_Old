package net.entetrs.xenon.libs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import net.entetrs.xenon.commons.GdxCommons;

public enum SoundLib implements Disposable
{
	SHOOT("shoot.mp3"), 
	BIG_SHOOT("big-shoot.wav"),
	SHIELD_UP("shield_up.wav"), 
	SHIELD_DOWN("shield_down.wav"), 
	CLIC("clic.wav"), 
	EXPLOSION("explosion.wav"), 
	INTRO("intro.mp3"), 
	MUSIC("music.mp3");
	
	private Log log = LogFactory.getLog(this.getClass());
	private Sound sound;
	
	private SoundLib(String fileName)
	{
		String completeName = String.format("sounds/%s", fileName);
		this.sound = Gdx.audio.newSound(Gdx.files.internal(completeName));
	}

	public Sound getSound()
	{
		return this.sound;
	}
	
	public void play()
	{
		this.sound.play();
	}
	
	public void play(float vol)
	{
		this.sound.play(vol);
	}
	
	public void loop(float vol)
	{
		this.sound.loop(vol);
	}
	
	public void loop()
	{
		this.sound.loop();
	}
	
	public void stop()
	{
		this.sound.stop();
	}


	@Override
	public void dispose()
	{
		log.info("DISPOSE SOUNDS ...");
		GdxCommons.disposeAll(SoundLib.values());
	}



}
