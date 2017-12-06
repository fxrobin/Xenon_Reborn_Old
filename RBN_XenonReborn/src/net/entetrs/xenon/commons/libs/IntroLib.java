package net.entetrs.xenon.commons.libs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * enum à chargement rapide pour l'intro et l'écran de chargement initial.
 * 
 * @author robin
 *
 */
public final class IntroLib
{
	private static final String INTRO_SOUND = "sounds/intro-sound.mp3";
	private static final Texture logo = new Texture("commons/xenon-reborn.png");

	private IntroLib()
	{
		// protection
	}
	
	public static void playIntroSound()
	{
		Sound introSound = Gdx.audio.newSound(Gdx.files.internal(INTRO_SOUND));
		introSound.play();
	}
	
	public static void drawLogo(SpriteBatch b)
	{
		b.draw(logo,0,0);
	}
	
}
