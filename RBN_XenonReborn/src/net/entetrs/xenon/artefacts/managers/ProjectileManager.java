package net.entetrs.xenon.artefacts.managers;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.artefacts.Artefact;
import net.entetrs.xenon.artefacts.friendly.Ship;
import net.entetrs.xenon.artefacts.friendly.Shoot;
import net.entetrs.xenon.artefacts.friendly.ShootType;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.libs.AnimationLib;

public class ProjectileManager
{
	private List<Artefact> shoots = new LinkedList<>();

	private static ProjectileManager pm = new ProjectileManager();

	public static ProjectileManager getInstance()
	{
		return pm;
	}
	
	public static void checkFire(Ship ship)
	{
		if (!ship.isShieldActivated())
		{
			if (Gdx.input.isKeyJustPressed(Keys.CONTROL_RIGHT))
			{
				pm.addShoot(ShootType.NORMAL_LASER, ship.getCenterX(), ship.getCenterY());
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.SHIFT_RIGHT))
			{
				pm.addShoot(ShootType.BIG_FLAMES, ship.getCenterX(), ship.getCenterY());
			}		
		}	
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
