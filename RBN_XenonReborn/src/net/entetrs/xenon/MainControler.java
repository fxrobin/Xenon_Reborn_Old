package net.entetrs.xenon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.commons.Fader;
import net.entetrs.xenon.commons.Fader.State;
import net.entetrs.xenon.commons.GdxCommons;
import net.entetrs.xenon.entities.Artefact;
import net.entetrs.xenon.screens.ArtefactsScene;
import net.entetrs.xenon.screens.GamePlayScreen;
import net.entetrs.xenon.screens.MenuScreen;

public class MainControler extends Game
{
	// DP SINGLETON
	
	private static MainControler instance = new MainControler();

	private MainControler()
	{
		// protection car Singleton.
	}

	public static MainControler getInstance()
	{
		return instance;
	}
	

	public enum XenonScreen
	{
		MENU, GAME_PLAY;
	}

	private SpriteBatch batch;
	private ShapeRenderer shareRenderer;
	private Screen currentScreen;
	private Fader fader;

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		fader = Fader.getInstance();
		shareRenderer = new ShapeRenderer();
		this.showScreen(XenonScreen.MENU);
	}

	public void showScreen(XenonScreen screen)
	{
		if (currentScreen != null) fader.startFadeOut();
		
		switch (screen)
		{
			case MENU:
				currentScreen = new MenuScreen();		
				break;
			case GAME_PLAY:
				currentScreen = new GamePlayScreen();
				break;
			default:
				System.out.println("Ecran inconnu");
		}	
		this.fade();
	}

	@Override
	public void render()
	{
		batch.begin();
		GdxCommons.clearScreen();
		this.fade();
		super.render();
		batch.end();
		
		if (this.getScreen() instanceof ArtefactsScene)
		{
			shareRenderer.begin(ShapeType.Line);
			shareRenderer.setColor(Color.RED);
			ArtefactsScene as = (ArtefactsScene) this.getScreen();
			for(Artefact a : as.getArtefacts())
			{
				Circle c = a.getBoundingCircle();
				shareRenderer.circle(c.x, c.y, c.radius);
			}
			shareRenderer.end();
		}
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
			fader.fade(batch);
		}
	}
	
	public SpriteBatch getBatch()
	{
		return batch;
	}

	@Override
	public void dispose()
	{
		super.dispose();
	//	GdxCommons.disposeAll(batch, soundManager, textureManager, fontManager);
	}


	

}
