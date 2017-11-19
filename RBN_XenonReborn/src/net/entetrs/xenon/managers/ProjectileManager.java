package net.entetrs.xenon.managers;

import java.util.LinkedList;
import java.util.List;

import net.entetrs.xenon.commons.AnimatedSprite;
import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.libs.AnimationLib;
import net.entetrs.xenon.libs.SoundLib;

public class ProjectileManager
{
	private List<AnimatedSprite> shoots = new LinkedList<>();

	private static final float LASER_SPEED = 400f;
	private static ProjectileManager pm = new ProjectileManager();

	public static ProjectileManager getInstance()
	{
		return pm;
	}

	public void translateShoots(float delta)
	{
		shoots.forEach(s -> {
			s.translateY(delta * LASER_SPEED);
			if (!s.isAlive()) ExplosionManager.addExplosion(s.getX(), s.getY(), AnimationLib.EXPLOSION_LITTLE);
		});
		shoots.removeIf(s -> (s.getY() > C.HEIGHT || !s.isAlive()));
	}

	public void addShoot(float centerX, float centerY)
	{
		this.addShoot(centerX, centerY, AnimationLib.FRIENDLY_SHOOT);
	}
	
	public void addShoot(float centerX, float centerY, AnimationLib anim)
	{
		AnimatedSprite s = anim.createAnimatedSprite();
		s.setCenter(centerX, centerY);
		shoots.add(s);
		SoundLib.SHOOT.play();
	}

	public List<AnimatedSprite> getShoots()
	{
		return this.shoots;
	}

}
