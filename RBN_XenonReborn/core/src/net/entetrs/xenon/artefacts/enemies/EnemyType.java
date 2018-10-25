package net.entetrs.xenon.artefacts.enemies;

import com.badlogic.gdx.graphics.Texture;

import net.entetrs.xenon.artefacts.ArtefactData;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.libs.TextureAsset;
import net.entetrs.xenon.commons.utils.RandomUtils;

public enum EnemyType
{
	NORMAL(TextureAsset.ENEMY, 10, 10), 
	BUG(TextureAsset.BUG, 10, 10), 
	PERFORATOR(TextureAsset.PERFORATOR, 10, 10), 
	BIG_ENEMY(TextureAsset.BIG_ENEMY, 20, 20), 
	RAFALE(TextureAsset.RAFALE, 15, 15), 
	BLACK_BIRD(TextureAsset.BLACK_BIRD, 15,	15), 
	XENON_SHIP(TextureAsset.XENON_SHIP, 15, 15);

	private TextureAsset textureRef;

	private ArtefactData data;

	private EnemyType(TextureAsset textureRef, int lifeForce, int impactForce)
	{
		this.textureRef = textureRef;
		data = new ArtefactData(lifeForce, impactForce, 0, 0);
	}

	public int getImpactForce()
	{
		return data.getImpactForce();
	}

	public int getLifeForce()
	{
		return data.getLifePoints();
	}

	public TextureAsset getTextureRef()
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

	private static Enemy selectRandom()
	{
		EnemyType enemyType = RandomUtils.pick(EnemyType.values());
		Texture texture = enemyType.getTextureRef().get();
		return new Enemy(texture, enemyType.getLifeForce(), enemyType.getImpactForce(), texture.getWidth() / 2f);
	}

	private static void randomCoords(Enemy e)
	{
		e.setOriginCenter();
		e.setX((float) Math.random() * Global.width);
		e.setY((float) Math.random() * 100 + Global.height);
		e.setVectorX((float) Math.random() * 200f - 100);
		e.setVectorY(-((float) Math.random() * 500f + 100f));
	}

}
