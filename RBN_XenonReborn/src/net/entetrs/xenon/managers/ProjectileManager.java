package net.entetrs.xenon.managers;

import java.util.LinkedList;
import java.util.List;

import net.entetrs.xenon.commons.AnimatedSprite;
import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.entities.Artefact;
import net.entetrs.xenon.libs.AnimationLib;
import net.entetrs.xenon.libs.SoundLib;

public class ProjectileManager {
	
	private static ProjectileManager pm = new ProjectileManager();
	
	public static ProjectileManager getInstance()
	{
		return pm;
	}
	
	private List<AnimatedSprite> shoots = new LinkedList<>();
	private float speedLaser = 400f;
	
	public void translateShoots(float delta) {
		shoots.forEach(s -> {
			s.translateY(delta * speedLaser);
			if (!s.isAlive()) ExplosionManager.addExplosion(s.getX(), s.getY(), AnimationLib.EXPLOSION_LITTLE);
		});
		shoots.removeIf(s -> (s.getY() > C.HEIGHT || !s.isAlive()));
	}

	public void addShoot(float centerX, float centerY)
	{
		AnimatedSprite s = AnimationLib.FRIENDLY_SHOOT.createAnimatedSprite();
		s.setCenter(centerX, centerY);
		shoots.add(s);
		SoundLib.SHOOT.play();
		
	}

	public List<? extends AnimatedSprite> getShoots()
	{
		return this.shoots;
	}
	
	
	
	

}
