package net.entetrs.xenon.managers;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.Renderable;
import net.entetrs.xenon.entities.Enemy;
import net.entetrs.xenon.libs.AnimationLib;

public class EnemyManager implements Renderable
{
	private double accumulator = 0;
	private List<Enemy> enemies = new LinkedList<>();
	
	private static EnemyManager em = new  EnemyManager();
	
	public static EnemyManager getInstance()
	{
		return em;
	}
	
	public void generateEnemies(float delta) {		
		accumulator+=delta;
		if (accumulator > 4f) // toutes les 4 secondes
		{
			accumulator = 0f; // on réinit.
			// on génère 5 enemies.
			for (int i = 0; i < 4; i++) {
				Enemy e = Enemy.random();
				enemies.add(e);
			}
		}
	}
	
	public void translateEnemies(float delta) {
		enemies.forEach(e -> { 
			e.move(delta);
			if (!e.isAlive()) ExplosionManager.addExplosion(e.getX(), e.getY(), AnimationLib.EXPLOSION_BIG);
		});
		enemies.removeIf(e -> e.getY() < -e.getHeight() || !e.isAlive());
	}
	
	public List<Enemy> getEnemies()
	{
		return enemies;
	}
	
	@Override
	public void render(SpriteBatch batch, float delta) {
		enemies.forEach(e -> { if (e.isAlive()) e.draw(batch); });
	}


}
