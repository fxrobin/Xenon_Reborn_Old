package net.entetrs.xenon.commons;

/**
 * permet d'exécuter un runnable une seule et une seule fois, même si le singleExecutor est
 * rappelé.
 * 
 * @author robin
 *
 */
public class SingleExecutor
{
	private Runnable runnable;
	private boolean ran = false;
	
	public SingleExecutor(Runnable runnable)
	{
		this.runnable = runnable;
	}
	
	public void execute()
	{
		if (!ran && runnable != null)
		{
		    ran = true;
			runnable.run();
		}
	}

}
