package net.entetrs.xenon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.MainControler;
import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.libs.TextureLib;

public class BackgroundScrolling
{
	private float position;
	private float speed;

	private static BackgroundScrolling instance = new BackgroundScrolling();

	public static BackgroundScrolling getInstance()
	{
		return instance;
	}

	private BackgroundScrolling()
	{
		/* protection */
	}

	public void init()
	{
		this.position = 0;
		this.speed = 9f;
	}

	public void checkInput()
	{
		if (Gdx.input.isKeyPressed(Keys.PAGE_UP)) speed += 0.5f;

		if (Gdx.input.isKeyPressed(Keys.PAGE_DOWN)) speed -= 0.5f;
	}

	public void render(float delta)
	{
		position -= 10f * delta * speed;

		SpriteBatch batch = MainControler.getInstance().getBatch();
		Texture space = TextureLib.BACKGROUND_SPACE.get();
		Texture leftbg = TextureLib.BACKGROUND_LEFT.get();
		Texture rightbg = TextureLib.BACKGROUND_RIGHT.get();
		batch.draw(space, 0f, 0f, 0, (int) position, C.width, C.height);
		batch.draw(leftbg, 0f, 0f, 0, (int) position * 2, leftbg.getWidth(), C.height);
		batch.draw(rightbg, (float) C.width - rightbg.getWidth(), 0f, 0, (int) position * 2, C.width, C.height);
	}

	public float getSpeed()
	{
		return speed;
	}

}
