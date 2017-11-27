package net.entetrs.xenon.screens;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.MainControler;
import net.entetrs.xenon.commons.FontCommons;
import net.entetrs.xenon.commons.R;
import net.entetrs.xenon.entities.Artefact;
import net.entetrs.xenon.entities.friendly.Ship;
import net.entetrs.xenon.libs.AnimationLib;
import net.entetrs.xenon.libs.FontLib;
import net.entetrs.xenon.libs.SoundLib;
import net.entetrs.xenon.libs.TextureLib;
import net.entetrs.xenon.managers.CollisionManager;
import net.entetrs.xenon.managers.EnemyManager;
import net.entetrs.xenon.managers.ExplosionManager;
import net.entetrs.xenon.managers.ProjectileManager;
import net.entetrs.xenon.managers.ScoreManager;

public class GamePlayScreen extends AbstractScreen implements ArtefactsScene
{
	private static final String FMT_MSG_BAR = "XENON Reborn // FPS : %d // nbLaser : %d // CurrentSpeed : %f // life : %d";

	private Log log = LogFactory.getLog(this.getClass());

	private BackgroundScrolling scrolling;
	private EnemyManager em;
	private CollisionManager cm;
	private Ship ship;

	public GamePlayScreen()
	{
		log.info("Instanciation de GamePlay");
		scrolling = BackgroundScrolling.getInstance();
		scrolling.init();
		em = EnemyManager.getInstance();
		cm = CollisionManager.getInstance();
	}

	@Override
	public void render(float delta)
	{
		this.checkInputKeys(delta);
		em.generateEnemies(delta);
		this.translateWorld(delta);
		List<Artefact> allPlayerObjects = new LinkedList<>(ProjectileManager.getInstance().getShoots());
		allPlayerObjects.add(ship);
		cm.checkCollision(em.getEnemies(), allPlayerObjects);
		this.renderWorld(delta);
	}

	private void renderWorld(float delta)
	{
		SpriteBatch batch = MainControler.getInstance().getBatch();
		this.scrolling.render(delta);
		this.renderShoots(batch, delta);
		em.render(batch, delta);
		this.renderShip(delta);
		ExplosionManager.render(batch, delta);
		this.renderStatusBar();
		this.renderScore();
	}

	private void renderScore()
	{
		SpriteBatch batch = MainControler.getInstance().getBatch();
		FontCommons.print(batch, 5, R.height - 43f, String.format("%010d", ScoreManager.getInstance().getScore()));
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
		String titleBar = String.format(FMT_MSG_BAR, fps, ProjectileManager.getInstance().getShoots().size(), scrolling.getSpeed(), ship.getLife());
		font.draw(batch, titleBar, 6, 6 + font.getCapHeight());
	}

	private void renderShip(float delta)
	{
		ship.render(MainControler.getInstance().getBatch(), delta);
	}

	private void translateWorld(float delta)
	{
		ProjectileManager.getInstance().translateShoots(delta);
		em.translateEnemies(delta);
	}

	private void checkInputKeys(float delta)
	{
		this.checkFire();
		ship.checkShield();
		ship.checkShipMoves(delta);
		this.checkEscape();
		this.scrolling.checkInput();
		this.checkExtraKeys();
	}

	private void checkExtraKeys()
	{
		if (Gdx.input.isKeyPressed(Keys.D))
		{
			float x = (float) Math.random() * R.width;
			float y = (float) Math.random() * R.height;
			ExplosionManager.addExplosion(x, y);
		}
	}

	private void checkEscape()
	{
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			SoundLib.CLIC.play();
			MainControler.getInstance().showScreen(XenonScreen.MENU);
		}
	}

	private void checkFire()
	{
		if (Gdx.input.isKeyJustPressed(Keys.CONTROL_RIGHT) && !ship.isShieldActivated())
		{
			ProjectileManager.getInstance().addShoot(ship.getCenterX(), ship.getCenterY());
		}

		if (Gdx.input.isKeyJustPressed(Keys.SHIFT_RIGHT) && !ship.isShieldActivated())
		{
			ProjectileManager.getInstance().addShoot(ship.getCenterX(), ship.getCenterY(), 15, 10, AnimationLib.FRIENDLY_BIGSHOOT, SoundLib.BIG_SHOOT);
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
	public List<Artefact> getArtefacts()
	{
		List<Artefact> world = new LinkedList<>(em.getEnemies());
		world.addAll(ProjectileManager.getInstance().getShoots());
		return world;
	}

}
