package net.entetrs.xenon.commons.displays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.utils.DeltaTimeAccumulator;


/**
 * permet d'encapsuler la logique de clignotement.
 * 
 * @author robin
 *
 */
public class Blinker implements Renderable
{
	private Renderable renderable;
	private DeltaTimeAccumulator accumulator;
	private boolean displayed;
	private float totalBlinkingTime;
	
	private void switchDiplay()
	{
		displayed=!displayed;
	}
	
	public Blinker(float blinkTime, Renderable renderable)
	{
		this(blinkTime, renderable, Float.MAX_VALUE);
	}
	
	public Blinker(float blinkRate, Renderable renderable, float totalBlinkingTime)
	{
		this.accumulator = new DeltaTimeAccumulator(blinkRate, this::switchDiplay);
		this.renderable = renderable;
		this.totalBlinkingTime = totalBlinkingTime;
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		accumulator.addAndCheck(delta);
		// si c'est activé pour l'affichage ou si le temps de blinking est dépassé, alors on affiche
		if (displayed || this.isBlinkingFinished())
		{
			renderable.render(batch, delta);
		}
	}
	
	public boolean isBlinkingFinished()
	{
		return accumulator.getAccumulatedTime() > totalBlinkingTime ;
	}
	
	public void restart()
	{
		this.accumulator.restart();
	}
	
	public void setRenderable(Renderable r)
	{
		this.renderable = r;
	}

}
