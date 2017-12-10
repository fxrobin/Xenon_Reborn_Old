package net.entetrs.xenon.artefacts.extra;

import com.badlogic.gdx.audio.Sound;

import net.entetrs.xenon.artefacts.ArtefactData;
import net.entetrs.xenon.commons.displays.AnimatedSprite;
import net.entetrs.xenon.commons.libs.AnimationAsset;

public enum BonusType
{
	NORMAL_BONUS(AnimationAsset.BONUS, 0, -60f, null, 5, 5);

	private final AnimationAsset anim;
	private final Sound sound;
	private ArtefactData data;

	private BonusType(AnimationAsset anim, float vectorX, float vectorY, Sound sound, int lifePoints, int impactForce)
	{
		this.anim = anim;
		this.sound = sound;
		this.data = new ArtefactData(lifePoints, impactForce, vectorX, vectorY);
	}

	public AnimatedSprite createAnimatedSprite()
	{
		return anim.createAnimatedSprite();
	}

	public float getVX()
	{
		return data.getVectorX();
	}

	public float getVY()
	{
		return data.getVectorY();
	}

	public int getLifeForce()
	{
		return data.getLifePoints();
	}

	public int getImpactForce()
	{
		return data.getImpactForce();
	}

	public Sound getSound()
	{
		return sound;
	}
}
