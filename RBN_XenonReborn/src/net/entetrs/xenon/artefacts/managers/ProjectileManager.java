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
import net.entetrs.xenon.commons.libs.AnimationAsset;
import net.entetrs.xenon.commons.utils.GdxCommons;
import net.entetrs.xenon.commons.utils.RandomUtils;

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
		if (!ship.isShieldActivated() && !ship.isFullyDestroyed())
		{
			checkNormalFire(ship);	
			checkBigFire(ship);		
		}	
	}

	private static void checkBigFire(Ship ship)
	{
		// quand l'arme est chargée et qu'on relache la touche SHIFT.
		if (ship.getSecondaryWeapon().isReady() && !Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
		{
			ship.getSecondaryWeapon().disable();
			pm.addShoot(ShootType.BIG_FLAMES, ship.getCenterX(), ship.getCenterY());
		}
	}

	private static void checkNormalFire(Ship ship)
	{
		if (Gdx.input.isKeyJustPressed(Keys.CONTROL_RIGHT))
		{
			pm.addShoot(ShootType.NORMAL_LASER, ship.getCenterX(), ship.getCenterY());
		}
	}

	public void renderShoots(SpriteBatch batch, float delta)
	{
		shoots.forEach(s -> {
			s.render(batch, delta);
			if (!s.isAlive()) ExplosionManager.addExplosion(s, AnimationAsset.EXPLOSION_LITTLE);
		});
		shoots.removeIf(s -> (s.getSprite().getY() > Global.height || !s.isAlive()));
	}

	public void addShoot(ShootType shootType, float centerX, float centerY)
	{
		// on va décaller le tir au hasard un peu à droite ou un peu à gauche pour faire joli.
		float decallage = RandomUtils.randomRange(-4, 4);
		Shoot s = new Shoot(shootType.createAnimatedSprite(), 
							shootType.getLifeForce(), 
							shootType.getImpactForce(), 
							centerX + decallage, 
							centerY, 
							shootType.getVX(), 
							shootType.getVY());
		shoots.add(s);
		shootType.getSound().play();
	}


	public List<Artefact> getShoots()
	{
		return this.shoots;
	}

}
