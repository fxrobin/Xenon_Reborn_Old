package net.entetrs.xenon.commons;

import java.util.stream.Stream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Fader
{
	private float step = 1.0f / C.FADE_SECONDS; // augmentation par secondes
	private float currentAlpha = 0;
	private State currentState = State.BLACK_SCREEN;

	private static Fader instance = new Fader();

	public static Fader getInstance()
	{
		return instance;
	}

	private Fader()
	{
		// protection
	}

	public enum State
	{
		BLACK_SCREEN, FADING_OUT, FADING_IN, DISPLAYING_SCREEN;
	}

	public State getCurrentState()
	{
		return currentState;
	}

	public float getCurrentAlpha()
	{
		return currentAlpha;
	}

	public void startFadeIn()
	{
		currentAlpha = 0;
		currentState = State.FADING_IN;
	}

	public void startFadeOut()
	{
		currentAlpha = 1;
		currentState = State.FADING_OUT;
	}

	public void fade(Batch batch)
	{
		float delta = Gdx.graphics.getDeltaTime();
		switch (currentState)
		{
			case FADING_IN:
				fadeIn(delta);
				break;
			case FADING_OUT:
				fadeOut(delta);
				break;
			default:
				break;
		}
		batch.setColor(1, 1, 1, currentAlpha);
	}

	private void fadeIn(float delta)
	{
		currentAlpha = currentAlpha + (step * delta);
		if (currentAlpha > 1.0f)
		{
			currentAlpha = 1.0f;
			this.currentState = State.DISPLAYING_SCREEN;
		}
	}

	private void fadeOut(float delta)
	{
		currentAlpha = currentAlpha - (step * delta);
		if (currentAlpha < 0.0f)
		{
			currentAlpha = 0.0f;
			this.currentState = State.BLACK_SCREEN;
		}
	}

	public void setSpriteAlpha(Stream<? extends Sprite> stream)
	{
		stream.forEach(s -> GdxCommons.applyAlpha(s, this.getCurrentAlpha()));
	}

}
