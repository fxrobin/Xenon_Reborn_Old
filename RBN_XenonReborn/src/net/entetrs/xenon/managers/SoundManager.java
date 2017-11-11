package net.entetrs.xenon.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import net.entetrs.xenon.commons.GdxCommons;

public class SoundManager implements Disposable
{
	private Sound shoot;
	private Sound intro;
	private Sound music;
	private Sound shieldUpSound;
	private Sound shieldDownSound;
	private Sound clic;
	private Sound explosion;

	public SoundManager()
	{
		shoot = load("shoot.mp3");
		intro = load("intro.mp3");
		music = load("music.mp3");
		clic = load("clic.wav");
		explosion = load("explosion.wav");
		shieldDownSound = load("shield_down.wav");
		shieldUpSound = load("shield_up.wav");
	}

	public Sound getShoot()
	{
		return shoot;
	}

	public Sound getMusic()
	{
		return music;
	}

	public Sound getShieldUpSound()
	{
		return shieldUpSound;
	}

	public Sound getShieldDownSound()
	{
		return shieldDownSound;
	}

	public Sound getIntro()
	{
		return intro;
	}

	public Sound getClic()
	{
		return clic;
	}

	public Sound getExplosion()
	{
		return explosion;
	}

	@Override
	public void dispose()
	{
		System.out.print("DISPOSE SOUNDS ...");
		GdxCommons.disposeAll(shoot, intro, music, shieldUpSound, shieldDownSound);
		System.out.println("OK");
	}

	private Sound load(String name)
	{
		String completeName = String.format("sounds/%s", name);
		return Gdx.audio.newSound(Gdx.files.internal(completeName));
	}

}
