package net.entetrs.xenon.commons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.entities.Entity;
import net.entetrs.xenon.libs.AnimationLib;

/**
 * classe pour gérer les animations d'un sprite en fonction des animations du
 * catalogue.
 * 
 * @author CDT RBN
 *
 */

public class AnimatedSprite extends Sprite implements Entity {
	private float stateTime;
	private Animation<TextureRegion> animation;
	private Circle boundingCircle;
	private float radius;
	private int force = 2;

	public AnimatedSprite(AnimationLib anim) {
		super(anim.getAnimation().getKeyFrames()[0]);
		radius = anim.getAnimation().getKeyFrames()[0].getRegionWidth() / 2;
		stateTime = 0f;
		this.animation = anim.getAnimation();
		boundingCircle = new Circle();
	}

	public AnimatedSprite(AnimationLib anim, float centerX, float centerY) {
		this(anim);
		this.setOrigin(centerX, centerY);
	}

	public void act(float delta) {
		stateTime += delta;
		this.setRegion(animation.getKeyFrame(stateTime));
		boundingCircle.set(GdxCommons.getCenterX(this), GdxCommons.getCenterY(this), radius);
	}

	public void render(Batch batch, float delta) {
		this.act(delta);
		this.draw(batch);
	}

	public boolean isFinished() {
		return animation.isAnimationFinished(stateTime);
	}

	@Override
	public Circle getCircle() {
		return boundingCircle;
	}

	@Override
	public void decreaseLife(int impactForce) {
		force = force - impactForce;

	}

	@Override
	public boolean isAlive() {
		return force > 0;
	}

	@Override
	public int getImpactForce() {
		return 5;
	}

}
