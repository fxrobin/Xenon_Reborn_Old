package net.entetrs.xenon.commons.utils;

/**
 * classe de chrométrage qui permet de définir une limite. Quand la limite est
 * atteinte un booléen est renvoyé. Si un "Runnable" a été fournit à la
 * construction, alors ce runnable est lancé et le chronomètre est
 * ré-initialisé.
 * 
 * @author francois.robin
 *
 */
public class Chronometer
{
	private float timer = 0f;
	private float limit;

	private Runnable runnable = null;

	public Chronometer(float limit)
	{
		this.limit = limit;
	}

	public Chronometer(float limit, Runnable runnable)
	{
		this.limit = limit;
		this.runnable = runnable;
	}

	public boolean isLimitReached()
	{
		return this.timer > this.limit;
	}

	public void reset()
	{
		this.timer = 0f;
	}

	public void accumulateTime(float deltaTime)
	{
		this.timer += deltaTime;
		executeIfLimitIsReached();
	}

	/**
	 * lance le runnable s'il est définit et si la limite est atteinte. Dans ce
	 * cas le chrono est réinitialisé (reset).
	 */
	private void executeIfLimitIsReached()
	{
		if (runnable != null && isLimitReached())
		{
			reset();
			runnable.run();
		}
	}
}
