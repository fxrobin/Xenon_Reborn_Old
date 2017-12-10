package net.entetrs.xenon.artefacts.friendly;

import com.badlogic.gdx.audio.Sound;

import net.entetrs.xenon.artefacts.ArtefactData;
import net.entetrs.xenon.commons.displays.AnimatedSprite;
import net.entetrs.xenon.commons.libs.AnimationAsset;
import net.entetrs.xenon.commons.libs.SoundAsset;

public enum ShootType
{
	NORMAL_LASER(AnimationAsset.FRIENDLY_SHOOT, 0, 400f, SoundAsset.SHOOT.getSound(), 5, 5), 
	BIG_FLAMES(AnimationAsset.FRIENDLY_BIGSHOOT, 0, 300f, SoundAsset.BIG_SHOOT.getSound(), 30, 20);

	private final AnimationAsset anim;
	private final Sound sound;
	private ArtefactData data;
	
	private ShootType(AnimationAsset anim, float vX, float vY, Sound sound, int lifeForce, int impactForce)
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
