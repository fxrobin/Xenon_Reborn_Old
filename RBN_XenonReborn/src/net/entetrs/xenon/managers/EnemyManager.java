package net.entetrs.xenon.managers;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.displays.Renderable;
import net.entetrs.xenon.commons.utils.DeltaTimeAccumulator;
import net.entetrs.xenon.entities.enemies.Enemy;
import net.entetrs.xenon.entities.enemies.EnemyType;
import net.entetrs.xenon.libs.AnimationLib;

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
			for (int i = 0; i < 4; i++)
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
			if (!e.isAlive()) ExplosionManager.addExplosion(e.getX(), e.getY(), AnimationLib.EXPLOSION_BIG);
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
		enemies.forEach(e -> {
			if (e.isAlive()) e.render(batch, delta);
		});
	}

}
