package net.entetrs.xenon.artefacts.extra;

import com.badlogic.gdx.audio.Sound;

import net.entetrs.xenon.commons.displays.AnimatedSprite;
import net.entetrs.xenon.commons.libs.AnimationLib;

public enum BonusType
{NORMAL_BONUS(AnimationLib.BONUS, 0, -60f, null, 5, 5);

	private final AnimationLib anim;
	private final float vX;
	private final float vY;
	private final Sound sound;
	private final int lifeForce;
	private final int impactForce;

	private BonusType(AnimationLib anim, float vX, float vY, Sound sound, int lifeForce, int impactForce)
	{
		this.anim = anim;
		this.vX = vX;
		this.vY = vY;
		this.sound = sound;
		this.lifeForce = lifeForce;
		this.impactForce = impactForce;
	}
	
	public AnimatedSprite createAnimatedSprite()
	{
		return anim.createAnimatedSprite();
	}

	public float getVX()
	{
		return vX;
	}

	public float getVY()
	{
		return vY;
	}

	public int getLifeForce()
	{
		return lifeForce;
	}

	public int getImpactForce()
	{
		return impactForce;
	}

	public Sound getSound()
	{
		return sound;
	}
}
