package net.entetrs.xenon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.commons.Fader;
import net.entetrs.xenon.commons.Fader.State;
import net.entetrs.xenon.commons.GdxCommons;
import net.entetrs.xenon.entities.Artefact;
import net.entetrs.xenon.screens.ArtefactsScene;
import net.entetrs.xenon.screens.XenonScreen;

/**
 * contrôleur principal LibGDX qui gère les changement d'écrans par
 * Fade-in / Fade-out. Cette classe fournit aussi un SpriteBatch, eventuellement.
 * Ce contrôleur implémente le design pattern singleton (une seule instance unique en mémoire JVM).
 * 
 * @author CDT RBN
 *
 */
public class MainControler extends Game
{
	/* pour dessiner des texture et sprites à l'écran */
	private SpriteBatch batch;

	/* pour dessiner des cercles (bounding circles) à l'écran */
	private ShapeRenderer shapeRenderer;

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
		shapeRenderer = new ShapeRenderer();
		this.showScreen(XenonScreen.MENU);
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

	public SpriteBatch getBatch()
	{
		return batch;
	}

	public void showScreen(XenonScreen screen)
	{
		if (currentScreen != null)
		{
			fader.startFadeOut();
		}
		currentScreen = screen.createScreen();
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

	
	public void showBoundingCircles()
	{
		if (this.getScreen() instanceof ArtefactsScene)
		{
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
			ArtefactsScene as = (ArtefactsScene) this.getScreen();
			for (Artefact a : as.getArtefacts())
			{
				Circle c = a.getBoundingCircle();
				shapeRenderer.circle(c.x, c.y, c.radius);
			}
			shapeRenderer.end();
		}
	}

}
