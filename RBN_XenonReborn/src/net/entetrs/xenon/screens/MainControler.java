package net.entetrs.xenon.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.displays.Fader;
import net.entetrs.xenon.commons.displays.Fader.State;
import net.entetrs.xenon.commons.utils.GdxCommons;

/**
 * contrôleur principal LibGDX qui gère les changement d'écrans par
 * Fade-in / Fade-out. Cette classe fournit aussi un SpriteBatch, eventuellement.
 * Ce contrôleur implémente le design pattern singleton (une seule instance unique en mémoire JVM).
 * 
 * @author CDT RBN
 *
 */
public final class MainControler extends Game implements XenonControler
{
	/* pour dessiner des texture et sprites à l'écran */
	private SpriteBatch batch;

	/* écran courant */
	private Screen currentScreen;

	/* instance du fader pour "fade-in et fade-out" */
	private Fader fader;

	/* DP SINGLETON */
	private static MainControler instance = new MainControler();

	private MainControler()
	{
		/* protection */
	}

	public static MainControler getInstance()
	{
		return instance;
	}

	@Override
	public void create()
	{
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch = new SpriteBatch();
		batch.enableBlending();
		fader = Fader.getInstance();	
		this.showScreen(XenonScreen.LOADING);
		this.fade();
	}

	/**
	 * cette méthode est déclenchée par LibGDX à 60 FPS (60 images par secondes !)
	 */
	@Override
	public void render()
	{
		GdxCommons.clearScreen();
		if (!fader.getCurrentState().equals(State.BLACK_SCREEN))
		{
			batch.begin();
			super.render();
			batch.end();
		}
		this.fade();
	}


	@Override
	public void showScreen(XenonScreen screen)
	{
		if (currentScreen != null)
		{
			fader.startFadeOut();
		}
		currentScreen = screen.createScreen(this, this.batch);
	}

	private void fade()
	{
		if (fader.getCurrentState().equals(State.BLACK_SCREEN))
		{
			this.setScreen(currentScreen);
			fader.startFadeIn();
		}
		else
		{
			fader.fade();
		}
	}
}
