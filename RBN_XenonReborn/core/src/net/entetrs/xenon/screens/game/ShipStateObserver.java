package net.entetrs.xenon.screens.game;

import java.util.Observable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.entetrs.xenon.artefacts.Artefact;
import net.entetrs.xenon.artefacts.Event;
import net.entetrs.xenon.artefacts.managers.ExplosionManager;
import net.entetrs.xenon.commons.libs.AnimationAsset;
import net.entetrs.xenon.commons.libs.SoundAsset;


/**
 * fournit des méthodes déclencher en observation d'un changement d'état d'un artefact.
 * 
 * @author robin
 *
 */
public final class ShipStateObserver 
{
	private static final Log log = LogFactory.getLog(ShipStateObserver.class);
	
	private ShipStateObserver() 
	{
		// protection
	}
	
	/**
	 * observer d'explosion pour l'instance de Ship.
	 * Refactorisation à terme pour sortir le choix de l'explosion (et le son).
	 * 
	 * @param o
	 * @param event
	 */
	public static void shipExplosion(Observable o, Object event)
	{
		if (o instanceof Artefact && event instanceof Event)
		{	
			if (log.isInfoEnabled())
			{
				log.info("Event : " + event);
			}
			ExplosionManager.addExplosion((Artefact) o , AnimationAsset.EXPLOSION_BIG);
			SoundAsset.SHIP_EXPLOSION.play();
		}
	}

}
