package net.entetrs.xenon.entities.ennemies;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.commons.GdxCommons;
import net.entetrs.xenon.entities.Artefact;
import net.entetrs.xenon.libs.TextureLib;

public class Enemy extends Sprite implements Artefact
{
	
	private float vX;
	private float vY;
	private Circle boundingCircle;
	private int force;
	private int impactForce; 

	public Enemy(Texture texture, int force, int impactForce)
	{
		super(texture);
		boundingCircle = new Circle();
		boundingCircle.setRadius(texture.getWidth() / 2f);
		this.force = force;
		this.impactForce = impactForce;
	}

	

	public void move(float delta)
	{
		this.setX(this.getX() + (getvX() * delta));
		this.setY(this.getY() + (getvY() * delta));
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
		return this.impactForce;
	}

	@Override
	public Sprite getSprite()
	{
		return this;
	}



	public float getvX()
	{
		return vX;
	}



	public void setvX(float vX)
	{
		this.vX = vX;
	}



	public float getvY()
	{
		return vY;
	}



	public void setvY(float vY)
	{
		this.vY = vY;
	}

}
