package net.entetrs.xenon.artefacts.managers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import net.entetrs.xenon.artefacts.Artefact;
import net.entetrs.xenon.commons.displays.AnimatedSprite;
import net.entetrs.xenon.commons.libs.AnimationAsset;
import net.entetrs.xenon.commons.libs.SoundAsset;
import net.entetrs.xenon.commons.utils.GdxCommons;
import net.entetrs.xenon.screens.game.BackgroundParallaxScrolling;

public final class ExplosionManager
{
	private static List<AnimatedSprite> explosions = Collections.synchronizedList(new LinkedList<>());

	private ExplosionManager()
	{
		/* protection */
	}
	
	public static void addExplosion(Sprite sprite,  AnimationAsset anim)
	{
		float x = GdxCommons.getCenterX(sprite);
		float y = GdxCommons.getCenterY(sprite);
		addExplosion(x, y, anim);
	}
	
	public static void addExplosion(Artefact artefact,  AnimationAsset anim)
	{
		addExplosion(artefact.getBoundingCircle().x, artefact.getBoundingCircle().y, anim);
	}
	
	

	public static void addExplosion(float centerX, float centerY, AnimationAsset anim)
	{
		AnimatedSprite animatedSprite = anim.createAnimatedSprite();
		animatedSprite.setOriginCenter();
		animatedSprite.setCenter(centerX, centerY);
		explosions.add(animatedSprite);
		SoundAsset.EXPLOSION.play();
	}

	public static void removeFinishedExplosions()
	{
		explosions.removeIf(AnimatedSprite::isFinished);
	}

	public static void render(Batch batch, float delta)
	{
		explosions.forEach(ex -> {
			ex.render(batch, delta);
			/* 2 vitesse du scroll des bords.*/
			ex.translateY(-BackgroundParallaxScrolling.getInstance().getSpeed() * 2 * delta); 
		});
		removeFinishedExplosions();
	}

}
