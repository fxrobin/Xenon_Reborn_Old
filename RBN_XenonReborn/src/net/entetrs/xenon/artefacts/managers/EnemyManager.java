package net.entetrs.xenon.artefacts.managers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.artefacts.enemies.Bullet;
import net.entetrs.xenon.artefacts.enemies.Enemy;
import net.entetrs.xenon.artefacts.enemies.EnemyType;
import net.entetrs.xenon.commons.displays.Renderable;
import net.entetrs.xenon.commons.libs.AnimationAsset;
import net.entetrs.xenon.commons.utils.DeltaTimeAccumulator;
import net.entetrs.xenon.commons.utils.GdxCommons;

public class EnemyManager implements Renderable
{
	private DeltaTimeAccumulator deltaTimeAccumulator = new DeltaTimeAccumulator(4f);
	private List<Enemy> enemies = new LinkedList<>();

	private static EnemyManager em = new EnemyManager();

	public static EnemyManager getInstance()
	{
		return em;
	}

	public void generateEnemies(float delta)
	{
		if (deltaTimeAccumulator.addAndCheck(delta))
		{
			/* on génère 5 enemis toutes les 4 secondes */
			for (int i = 0; i < 6; i++)
			{
				Enemy e = EnemyType.random();
				enemies.add(e);
			}
		}
	}

	public void act(float delta)
	{
		enemies.forEach(e -> {
			e.act(delta);
			if (!e.isAlive())
			{
				AnimationAsset anim = e instanceof Bullet ? AnimationAsset.EXPLOSION_LITTLE : AnimationAsset.EXPLOSION_BIG;
				ExplosionManager.addExplosion(e, anim);
			}
		});

		enemies.removeIf(e -> e.getY() < -e.getHeight() || !e.isAlive());
	}

	public List<Enemy> getEnemies()
	{
		return enemies;
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		List<Enemy> localEnemies = new ArrayList<>(enemies);
		localEnemies.forEach(e -> {
			if (e.isAlive()) e.render(batch, delta);
		});
	}

	public void generateBullet(Enemy e)
	{
		Bullet b = new Bullet();
		b.setOriginCenter();
		b.setX(GdxCommons.getCenterX(e.getSprite()));
		b.setY(e.getY());
		b.setVectorX(0);
		b.setVectorY(e.getVectorY() * 2f);
		enemies.add(b);
	}

}
