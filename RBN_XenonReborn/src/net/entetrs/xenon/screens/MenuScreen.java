package net.entetrs.xenon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.MainControler;
import net.entetrs.xenon.commons.AnimatedSprite;
import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.commons.FontCommons;
import net.entetrs.xenon.libs.AnimationLib;
import net.entetrs.xenon.libs.SoundLib;
import net.entetrs.xenon.libs.TextureLib;

public class MenuScreen implements Screen
{

	private MainControler ctrl;

	private Texture titleTexture;
	private Texture spaceTexture;

	private float pX = 0;
	private float pY = 0;

	private float vX = random() * 10.0f;
	private float vY = random() * 10.0f;

	private float aX = 0;
	private float aY = 0;

	private float titleX;
	private float titleY;

	private AnimatedSprite animatedSprite = new AnimatedSprite(AnimationLib.EXPLOSION);

	private int accumulator = 0;

	private boolean explode = false;

	public MenuScreen(MainControler ctrl)
	{
		System.out.println("Instanciation de MenuScreen");
		this.ctrl = ctrl;
		titleTexture = TextureLib.TITLE.get();
		titleX = (C.WIDTH - titleTexture.getWidth()) / 2;
		titleY = (C.HEIGHT - titleTexture.getHeight()) / 2;
		spaceTexture = TextureLib.BACKGROUND_SPACE.get();
	}

	@Override
	public void show()
	{
		SoundLib.INTRO.loop();
	}

	private float random()
	{
		return (float) (Math.random() * 10.0 - 5.0);
	}

	@Override
	public void render(float delta)
	{

		this.checkInput();
		this.translateBackGround(delta);
		this.drawBackGround();
		this.drawTitle();
		this.drawExplosionIfAppend(delta);
		this.drawMessage();
	}

	private void drawExplosionIfAppend(float delta)
	{
		if (explode)
		{
			SpriteBatch batch = ctrl.getBatch();
			this.animatedSprite.render(batch, delta);
			explode = !this.animatedSprite.isFinished();
		}

	}

	private void drawMessage()
	{
		String MSG = "PRESS SPACEBAR";
		int W = FontCommons.getWidth(MSG);
		FontCommons.print(this.ctrl.getBatch(), (C.WIDTH - W) / 2, 60, MSG);
	}

	private void drawTitle()
	{
		SpriteBatch batch = ctrl.getBatch();
		batch.draw(titleTexture, titleX, titleY);
	}

	private void drawBackGround()
	{
		SpriteBatch batch = ctrl.getBatch();
		batch.draw(spaceTexture, 0f, 0f, (int) pX, (int) pY, C.WIDTH, C.HEIGHT);
	}

	private void translateBackGround(float delta)
	{
		accumulator++;
		if (accumulator % (4 * (Gdx.graphics.getFramesPerSecond() + 1)) == 0)
		{
			accumulator = 0;
			aX = random();
			aY = random();
		}

		vX += aX * delta;
		vY += aY * delta;

		pX += vX * delta;
		pY += vY * delta;
	}

	private void checkInput()
	{

		if (Gdx.input.isKeyJustPressed(Keys.D))
		{
			SoundLib.EXPLOSION.play();
			animatedSprite = new AnimatedSprite(AnimationLib.EXPLOSION);
			animatedSprite.setCenter(640, 480);
			explode = true;
		}

		if (Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			SoundLib.CLIC.play();
			ctrl.showScreen(MainControler.XenonScreen.GAME_PLAY);
		}

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			Gdx.app.exit();
		}
	}

	@Override
	public void resize(int arg0, int arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hide()
	{
		SoundLib.INTRO.stop();
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

}
