package net.entetrs.xenon.entities.ennemies;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.commons.GdxCommons;
import net.entetrs.xenon.commons.R;
import net.entetrs.xenon.entities.Artefact;
import net.entetrs.xenon.libs.TextureLib;

public class Enemy extends Sprite implements Artefact
{

	private static Texture enemyTexture = TextureLib.ENEMY.get();
	private static Texture bugTexture = TextureLib.BUG.get();
	private static Texture perforatorTexture = TextureLib.PERFORATOR.get();

	private static Texture[] textures = { enemyTexture, bugTexture, perforatorTexture };
	private static Random randomGenerator = new Random();

	private float vX;
	private float vY;
	private Circle boundingCircle;
	private int force = 10;

	public Enemy(Texture texture)
	{
		super(texture);
		boundingCircle = new Circle();
		boundingCircle.setRadius(texture.getWidth() / 2f);
	}

	public static Enemy random()
	{
		int choosen = randomGenerator.nextInt(textures.length);
		Enemy e = new Enemy(textures[choosen]);
		e.setOriginCenter();
		e.setX((float) Math.random() * R.width);
		e.setY((float) Math.random() * 100 + R.height);
		e.vX = (float) Math.random() * 200f - 100;
		e.vY = -((float) Math.random() * 500f + 100f);
		return e;
	}

	public void move(float delta)
	{
		this.setX(this.getX() + (vX * delta));
		this.setY(this.getY() + (vY * delta));
		boundingCircle.setX(GdxCommons.getCenterX(this));
		boundingCircle.setY(GdxCommons.getCenterY(this));
	}

	@Override
	public Circle getBoundingCircle()
	{
		return boundingCircle;
	}

	@Override
	public void decreaseLife(int impactForce)
	{
		force = force - impactForce;
	}

	@Override
	public boolean isAlive()
	{
		return force > 0;
	}

	@Override
	public int getImpactForce()
	{
		return 10;
	}

	@Override
	public Sprite getSprite()
	{
		return this;
	}

}
