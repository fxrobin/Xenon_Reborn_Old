package net.entetrs.xenon.managers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;

import net.entetrs.xenon.commons.AnimatedSprite;
import net.entetrs.xenon.commons.Renderable;
import net.entetrs.xenon.libs.AnimationLib;
import net.entetrs.xenon.libs.SoundLib;

public class ExplosionManager implements Renderable
{
	private static List<AnimatedSprite> explosions = Collections.synchronizedList(new LinkedList<>());

	private ExplosionManager()
	{
		// protection
	}
	
	public static void addExplosion(float centerX, float centerY)
	{
		addExplosion(centerX, centerY, AnimationLib.EXPLOSION_BIG);
	}
	
	public static void addExplosion(float centerX, float centerY, AnimationLib anim)
	{
		AnimatedSprite animatedSprite = anim.createAnimatedSprite();
		animatedSprite.setOriginCenter();
		animatedSprite.setCenter(centerX, centerY);
		explosions.add(animatedSprite);
		SoundLib.EXPLOSION.play();
	}
	
	public static void removeFinishedExplosions()
	{
		explosions.removeIf( ex -> ex.isFinished());
	}
	
	public void render(Batch batch, float delta)
	{
		explosions.forEach( ex -> ex.render(batch, delta));
		removeFinishedExplosions();
	}
	
	

	

}
