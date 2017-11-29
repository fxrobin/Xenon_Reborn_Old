package net.entetrs.xenon.entities.ennemies;

import java.util.Random;

import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.libs.TextureLib;

public enum EnemyType
{
	NORMAL(TextureLib.ENEMY, 10,10),
	BUG(TextureLib.BUG, 10,10),
	PERFORATOR(TextureLib.PERFORATOR, 10,10);
	
	private TextureLib textureRef;
	private int lifeForce;
	private int impactForce;
	
	private EnemyType(TextureLib textureRef, int lifeForce, int impactForce)
	{
		this.textureRef = textureRef;
		this.lifeForce = lifeForce;
		this.impactForce = impactForce;
	}
	
	public int getImpactForce()
	{
		return impactForce;
	}
	
	public int getLifeForce()
	{
		return lifeForce;
	}
	
	public TextureLib getTextureRef()
	{
		return textureRef;
	}
	
	/**
	 * contruit un enemy au hasard.
	 * 
	 * @return
	 */
	public static Enemy random()
	{
		Enemy e = selectRandom();
		randomCoords(e);
		return e;
	}

	private static void randomCoords(Enemy e)
	{
		e.setOriginCenter();
		e.setX((float) Math.random() * C.width);
		e.setY((float) Math.random() * 100 + C.height);
		e.setvX((float) Math.random() * 200f - 100);
		e.setvY(-((float) Math.random() * 500f + 100f));
	}

	private static Enemy selectRandom()
	{
		Random randomGenerator = new Random();
		int choosen = randomGenerator.nextInt(EnemyType.values().length);
		EnemyType enemyType = EnemyType.values()[choosen];	
		return new Enemy(enemyType.getTextureRef().get(), enemyType.getLifeForce(), enemyType.getImpactForce());
	}
	
}
