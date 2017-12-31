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
	
	private void switchDiplay()
	{
		displayed=!displayed;
	}
	
	public Blinker(float blinkTime, Renderable renderable)
	{
		this.accumulator = new DeltaTimeAccumulator(blinkTime, this::switchDiplay);
		this.renderable = renderable;
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		accumulator.addAndCheck(delta);
		if (displayed)
		{
			renderable.render(batch, delta);
		}
	}

}
