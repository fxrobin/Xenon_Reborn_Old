package net.entetrs.xenon.artefacts.friendly;

import com.badlogic.gdx.audio.Sound;

import net.entetrs.xenon.artefacts.ArtefactData;
import net.entetrs.xenon.commons.displays.AnimatedSprite;
import net.entetrs.xenon.commons.libs.AnimationLib;
import net.entetrs.xenon.commons.libs.SoundLib;

public enum ShootType
{
	NORMAL_LASER(AnimationLib.FRIENDLY_SHOOT, 0, 400f, SoundLib.SHOOT.getSound(), 5, 5), 
	BIG_FLAMES(AnimationLib.FRIENDLY_BIGSHOOT, 0, 300f, SoundLib.BIG_SHOOT.getSound(), 30, 20);

	private final AnimationLib anim;
	private final Sound sound;
	private ArtefactData data;
	
	private ShootType(AnimationLib anim, float vX, float vY, Sound sound, int lifeForce, int impactForce)
	{
		this.anim = anim;
		this.sound = sound;
		data = new ArtefactData(lifeForce, impactForce, vX, vY);	
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
		return data.getLifePoints();
	}

	public Sound getSound()
	{
		return sound;
	}
}
