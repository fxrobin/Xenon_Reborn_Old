package net.entetrs.xenon;

import java.util.stream.Stream;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.commons.GdxCommons;
import net.entetrs.xenon.managers.FontManager;
import net.entetrs.xenon.managers.SoundManager;
import net.entetrs.xenon.managers.TextureManager;
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

	private SoundManager soundManager;
	private TextureManager textureManager;
	private FontManager fontManager;

	private float step = 1.0f / C.FADE_SECONDS; // augmentation par secondes
	private float currentAlpha = 0;
	private boolean fadeIn = false;
	private boolean fadeOut = false;

	private Screen currentScreen;

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		textureManager = new TextureManager();
		fontManager = new FontManager();
		this.showScreen(XenonScreen.MENU);
	}

	public void showScreen(XenonScreen screen)
	{
		startFadeOut();

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
	}

	@Override
	public void render()
	{
		batch.begin();
		this.checkFadeInFadeOut();
		super.render();
		batch.end();
	}

	private void checkFadeInFadeOut()
	{
		if (fadeOutFinished())
		{
			this.setScreen(currentScreen);
			startFadeIn();
		}
		clearScreen();
		float delta = Gdx.graphics.getDeltaTime();
		if (fadeIn) fadeIn(delta);
		if (fadeOut) fadeOut(delta);
		batch.setColor(1, 1, 1, currentAlpha);
	}

	private void fadeIn(float delta)
	{
		currentAlpha = currentAlpha + (step * delta);
		if (currentAlpha >= 1.0f)
		{
			currentAlpha = 1.0f;
			fadeIn = false;
		}
	}

	private void fadeOut(float delta)
	{
		currentAlpha = currentAlpha - (step * delta);
		if (currentAlpha <= 0.0f)
		{
			currentAlpha = 0.0f;
		}
	}

	public boolean fadeOutFinished()
	{

		if (currentAlpha == 0f && fadeOut)
		{
			fadeOut = false;
			return true;
		}
		else
		{
			return false;
		}
	}

	public void startFadeIn()
	{
		currentAlpha = 0;
		fadeIn = true;

	}

	public void startFadeOut()
	{
		currentAlpha = 1;
		fadeOut = true;
	}

	public SpriteBatch getBatch()
	{
		return batch;
	}

	@Override
	public void dispose()
	{
		super.dispose();
		GdxCommons.disposeAll(batch, soundManager, textureManager, fontManager);
	}

	public SoundManager getSoundManager()
	{
		return soundManager;
	}

	public FontManager getFontManager()
	{
		return fontManager;
	}

	public TextureManager getTextureManager()
	{
		return textureManager;
	}

	public void clearScreen()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public void clearScreen(float alpha)
	{
		Gdx.gl.glClearColor(0, 0, 0, alpha);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public void setSpriteAlpha(Stream<? extends Sprite> stream)
	{
		stream.forEach(s -> MainControler.applyAlpha(s, currentAlpha));
	}

	public static void applyAlpha(Sprite s, float alpha)
	{
		s.setColor(s.getColor().r, s.getColor().g, s.getColor().b, alpha);
	}

}
