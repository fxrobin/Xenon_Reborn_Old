package net.entetrs.xenon.screens.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.libs.TextureAsset;

public final class BackgroundParallaxScrolling
{
	private float position;
	private float speed;
	private SpriteBatch batch;

	private static BackgroundParallaxScrolling instance = new BackgroundParallaxScrolling();

	public static BackgroundParallaxScrolling getInstance()
	{
		return instance;
	}

	private BackgroundParallaxScrolling()
	{
		/* protection */
	}

	public void init(SpriteBatch batch)
	{
		this.position = 0;
		this.speed = 9f;
		this.batch = batch;
	}

	public void checkInput()
	{
		if (Gdx.input.isKeyPressed(Keys.PAGE_UP)) speed += 0.5f;

		if (Gdx.input.isKeyPressed(Keys.PAGE_DOWN)) speed -= 0.5f;
	}

	public void render(float delta)
	{
		position -= 10f * delta * speed;
		Texture space = TextureAsset.BACKGROUND_SPACE.get();
		Texture leftbg = TextureAsset.BACKGROUND_LEFT.get();
		Texture rightbg = TextureAsset.BACKGROUND_RIGHT.get();
		batch.draw(space, 0f, 0f, 0, (int) position, Global.width, Global.height);
		batch.draw(leftbg, 0f, 0f, 0, (int) position * 2, leftbg.getWidth(), Global.height);
		batch.draw(rightbg, (float) Global.width - rightbg.getWidth(), 0f, 0, (int) position * 2, Global.width, Global.height);
	}

	public float getSpeed()
	{
		return speed;
	}

}
