package net.entetrs.xenon.entities.friendly;

import com.badlogic.gdx.audio.Sound;

import net.entetrs.xenon.commons.displays.AnimatedSprite;
import net.entetrs.xenon.libs.AnimationLib;
import net.entetrs.xenon.libs.SoundLib;

public enum ShootType
{
	NORMAL_LASER(AnimationLib.FRIENDLY_SHOOT, 0, 400f, SoundLib.SHOOT.getSound(), 5, 5), 
	BIG_FLAMES(AnimationLib.FRIENDLY_BIGSHOOT, 0, 300f, SoundLib.BIG_SHOOT.getSound(), 30, 20);

	private final AnimationLib anim;
	private final float vX;
	private final float vY;
	private final Sound sound;
	private final int lifeForce;
	private final int impactForce;

	private ShootType(AnimationLib anim, float vX, float vY, Sound sound, int lifeForce, int impactForce)
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
