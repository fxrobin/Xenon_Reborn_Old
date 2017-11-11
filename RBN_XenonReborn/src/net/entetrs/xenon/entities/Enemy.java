package net.entetrs.xenon.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import net.entetrs.xenon.MainControler;
import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.helpers.TextureManager;

public class Enemy extends Sprite
{
	private static Texture enemyTexture = TextureManager.ENEMY.get();
	private static Texture bugTexture = TextureManager.BUG.get();
	private static Texture perforatorTexture = TextureManager.PERFORATOR.get();

	private static Texture[] textures = { enemyTexture, bugTexture, perforatorTexture };
	private static Random randomGenerator = new Random();

	private float vX;
	private float vY;

	public Enemy(Texture texture)
	{
		super(texture);
	}

	public static Enemy random()
	{
		int choosen = randomGenerator.nextInt(textures.length);
		Enemy e = new Enemy(textures[choosen]);
		e.setOriginCenter();
		e.setX((float) Math.random() * (float) C.WIDTH);
		e.setY((float) Math.random() * 100 + C.HEIGHT);
		e.vX = (float) Math.random() * 200f - 100;
		e.vY = -((float) Math.random() * 500f + 100f);
		return e;
	}

	public void move(float delta)
	{
		this.setX(this.getX() + (vX * delta));
		this.setY(this.getY() + (vY * delta));
	}

}
