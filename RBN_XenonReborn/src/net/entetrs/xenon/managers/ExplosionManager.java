package net.entetrs.xenon.managers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;

import net.entetrs.xenon.animations.AnimatedSprite;
import net.entetrs.xenon.animations.AnimationCatalog;

public class ExplosionManager
{
	private static List<AnimatedSprite> explosions = Collections.synchronizedList(new LinkedList<>());

	private ExplosionManager()
	{
		// protection
	}
	
	public static void addExplosion(float centerX, float centerY)
	{
		AnimatedSprite animatedSprite = AnimationCatalog.EXPLOSION.createAnimatedSprite();
		animatedSprite.setOriginCenter();
		animatedSprite.setCenter(centerX, centerY);
		explosions.add(animatedSprite);
	}
	
	public static void removeFinishedExplosions()
	{
		explosions.removeIf( ex -> ex.isFinished());
	}
	
	public static void render(Batch batch, float delta)
	{
		explosions.forEach( ex -> ex.render(batch, delta));
		removeFinishedExplosions();
	}
	
	

	

}
