package net.entetrs.xenon;

import java.util.stream.Stream;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.Fader;
import net.entetrs.xenon.commons.Fader.State;
import net.entetrs.xenon.commons.GdxCommons;
import net.entetrs.xenon.screens.GamePlayScreen;
import net.entetrs.xenon.screens.MenuScreen;

public class MainControler extends Game
{
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
	private Screen currentScreen;
	private Fader fader;

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		fader = new Fader();
		this.showScreen(XenonScreen.MENU);
	}

	public void showScreen(XenonScreen screen)
	{
		if (currentScreen != null) fader.startFadeOut();
		
		switch (screen)
		{
			case MENU:
				currentScreen = new MenuScreen(this);		
				break;
			case GAME_PLAY:
				currentScreen = new GamePlayScreen(this);
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


	public void setSpriteAlpha(Stream<? extends Sprite> stream)
	{
		stream.forEach(s -> GdxCommons.applyAlpha(s, fader.getCurrentAlpha()));
	}

}
