package net.entetrs.xenon.managers;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.artefacts.Artefact;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.entities.friendly.Shoot;
import net.entetrs.xenon.entities.friendly.ShootType;
import net.entetrs.xenon.libs.AnimationLib;

public class ProjectileManager
{
	private List<Artefact> shoots = new LinkedList<>();

	private static ProjectileManager pm = new ProjectileManager();

	public static ProjectileManager getInstance()
	{
		return pm;
	}

	public void renderShoots(SpriteBatch batch, float delta)
	{
		shoots.forEach(s -> {
			s.render(batch, delta);
			if (!s.isAlive()) ExplosionManager.addExplosion(s.getBoundingCircle().x, s.getBoundingCircle().y, AnimationLib.EXPLOSION_LITTLE);
		});
		shoots.removeIf(s -> (s.getSprite().getY() > Global.height || !s.isAlive()));
	}

	public void addShoot(ShootType shootType, float centerX, float centerY)
	{
		Shoot s = new Shoot(shootType.createAnimatedSprite(), shootType.getLifeForce(), shootType.getImpactForce(), shootType.getVX(), shootType.getVY());
		s.getSprite().setCenter(centerX, centerY);
		shoots.add(s);
		shootType.getSound().play();
	}


	public List<Artefact> getShoots()
	{
		return this.shoots;
	}

}
