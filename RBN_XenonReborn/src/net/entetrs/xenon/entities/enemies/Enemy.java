package net.entetrs.xenon.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.artefacts.AbstractArtefact;

/**
 * réprésente un ennemie.
 * 
 * @author robin
 *
 */

public class Enemy extends AbstractArtefact
{
	private Sprite sprite;

	public Enemy(Texture texture, int force, int impactForce, float radius)
	{
		super(0,0,force, impactForce);
		sprite = new Sprite(texture);
		this.setRadius(radius);
	}

	@Override
	public Sprite getSprite()
	{
		return this.sprite;
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		sprite.draw(batch);
	}

	public void setOriginCenter()
	{
		sprite.setOriginCenter();
	}

	public void setX(float x)
	{
		sprite.setX(x);
	}

	public void setY(float y)
	{
		sprite.setY(y);
	}

	public float getX()
	{
		return sprite.getX();
	}

	public float getY()
	{
		return sprite.getY();
	}

	public float getHeight()
	{
		return sprite.getHeight();
	}

}
