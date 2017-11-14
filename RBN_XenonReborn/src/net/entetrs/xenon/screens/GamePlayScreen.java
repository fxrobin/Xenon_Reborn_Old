package net.entetrs.xenon.screens;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.MainControler;
import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.commons.Fader;
import net.entetrs.xenon.entities.Artefact;
import net.entetrs.xenon.entities.Ship;
import net.entetrs.xenon.libs.FontLib;
import net.entetrs.xenon.libs.SoundLib;
import net.entetrs.xenon.libs.TextureLib;
import net.entetrs.xenon.managers.CollisionManager;
import net.entetrs.xenon.managers.EnemyManager;
import net.entetrs.xenon.managers.ExplosionManager;
import net.entetrs.xenon.managers.ProjectileManager;

public class GamePlayScreen extends AbstractScreen implements ArtefactsScene
{
	private static final String FMT_MSG_BAR = "XENON Reborn // FPS : %d // nbLaser : %d // CurrentSpeed : %f ";

	private BackgroundScrolling scrolling;
	private Ship ship;
	
	public GamePlayScreen()
	{
		System.out.println("Instanciation de GamePlay");
		scrolling = new BackgroundScrolling();
	}

	@Override
	public void render(float delta)
	{
		this.checkInputKeys(delta);
		this.setSpritesAlpha(); // pour l'effet fade-in / fade-out
		EnemyManager.getInstance().generateEnemies(delta);
		this.translateWorld(delta);
		CollisionManager.checkCollision(EnemyManager.getInstance().getEnemies(), ProjectileManager.getInstance().getShoots());
		this.renderWorld(delta);
	}

	private void setSpritesAlpha()
	{
		Fader.getInstance().setSpriteAlpha(Arrays.asList(ship.getShipSprite(), ship.getShieldSprite()).stream());
		Fader.getInstance().setSpriteAlpha(EnemyManager.getInstance().getEnemies().stream());
	}

	private void renderWorld(float delta)
	{
		SpriteBatch batch = MainControler.getInstance().getBatch();
		this.scrolling.render(delta);
		this.renderShoots(batch, delta);
		EnemyManager.getInstance().displayEnemies(batch);
		this.renderShip(delta);
		ExplosionManager.render(batch, delta);
		this.renderStatusBar();
	}

	private void renderShoots(SpriteBatch batch, float delta)
	{
		ProjectileManager.getInstance().getShoots().forEach(s -> {
			if (s.isAlive()) s.render(batch, delta);
		});
	}

	private void renderStatusBar()
	{
		BitmapFont font = FontLib.DEFAULT.getFont();
		SpriteBatch batch = MainControler.getInstance().getBatch();
		int fps = Gdx.graphics.getFramesPerSecond();
		batch.draw(TextureLib.FOOTER.get(), 0, 0);
		String titleBar = String.format(FMT_MSG_BAR, fps, ProjectileManager.getInstance().getShoots().size(), scrolling.getSpeed());
		font.draw(batch, titleBar, 6, 6 + font.getCapHeight());
	}

	private void renderShip(float delta)
	{
		ship.render(delta, MainControler.getInstance().getBatch());
	}

	private void translateWorld(float delta)
	{
		ProjectileManager.getInstance().translateShoots(delta);
		EnemyManager.getInstance().translateEnemies(delta);
	}

	private void checkInputKeys(float delta)
	{
		this.checkFire(delta);
		ship.checkShield(delta);
		ship.checkShipMoves(delta);
		this.checkEscape(delta);
		this.scrolling.checkInput();
		this.checkExtraKeys(delta);
	}

	private void checkExtraKeys(float delta)
	{
		if (Gdx.input.isKeyPressed(Keys.D))
		{
			float x = (float) Math.random() * C.WIDTH;
			float y = (float) Math.random() * C.HEIGHT;
			ExplosionManager.addExplosion(x, y);
		}
	}

	private void checkEscape(float delta)
	{
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			SoundLib.CLIC.play();
			MainControler.getInstance().showScreen(MainControler.XenonScreen.MENU);
		}
	}

	private void checkFire(float delta)
	{
		if (Gdx.input.isKeyJustPressed(Keys.CONTROL_RIGHT) && !ship.isShieldActivated())
		{
			ProjectileManager.getInstance().addShoot(ship.getCenterX(), ship.getCenterY());
		}
	}

	@Override
	public void show()
	{
		SoundLib.MUSIC.loop(0.6f);
		ship = new Ship();
	}

	@Override
	public void hide()
	{
		SoundLib.MUSIC.stop();
	}

	@Override
	public List<? extends Artefact> getArtefacts()
	{
		List<Artefact> world = new LinkedList<>(EnemyManager.getInstance().getEnemies());
		world.addAll(ProjectileManager.getInstance().getShoots());
		return world;
	}

}
