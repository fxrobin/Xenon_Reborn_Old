package net.entetrs.xenon.managers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;

import net.entetrs.xenon.commons.AnimatedSprite;
import net.entetrs.xenon.helpers.AnimationLib;

public class ExplosionManager
{
	private static List<AnimatedSprite> explosions = Collections.synchronizedList(new LinkedList<>());

	private ExplosionManager()
	{
		// protection
	}
	
	public static void addExplosion(float centerX, float centerY)
	{
		AnimatedSprite animatedSprite = AnimationLib.EXPLOSION.createAnimatedSprite();
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
