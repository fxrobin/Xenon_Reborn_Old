package net.entetrs.xenon.screens.game;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.artefacts.Artefact;
import net.entetrs.xenon.artefacts.ArtefactsScene;
import net.entetrs.xenon.artefacts.friendly.Ship;
import net.entetrs.xenon.artefacts.friendly.ShipHandler;
import net.entetrs.xenon.artefacts.managers.BonusManager;
import net.entetrs.xenon.artefacts.managers.CollisionManager;
import net.entetrs.xenon.artefacts.managers.EnemyManager;
import net.entetrs.xenon.artefacts.managers.ExplosionManager;
import net.entetrs.xenon.artefacts.managers.ProjectileManager;
import net.entetrs.xenon.artefacts.managers.ScoreManager;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.fonts.TrueTypeFont;
import net.entetrs.xenon.commons.fonts.FontUtils;
import net.entetrs.xenon.commons.libs.SoundAsset;
import net.entetrs.xenon.commons.libs.TextureAsset;
import net.entetrs.xenon.screens.AbstractScreen;
import net.entetrs.xenon.screens.XenonControler;
import net.entetrs.xenon.screens.XenonScreen;

public class GamePlayScreen extends AbstractScreen implements ArtefactsScene
{
	private static final String FMT_MSG_BAR = "XENON Reborn // FPS : %d // nbLasers : %d // CurrentSpeed : %f // lifePoints : %d // Shield : %f";

	private Log log = LogFactory.getLog(this.getClass());

	private BackgroundParallaxScrolling scrolling;
	private EnemyManager em;
	private CollisionManager cm;
	private Ship ship;

	public GamePlayScreen(XenonControler controler, SpriteBatch batch)
	{
		super(controler, batch);
		log.info("Instanciation de GamePlay");
		scrolling = BackgroundParallaxScrolling.getInstance();
		scrolling.init(batch);
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
		BonusManager.getInstance().checkBonus(ship);
		this.renderWorld(delta);
	}

	private void renderWorld(float delta)
	{
		SpriteBatch batch = this.getBatch();
		this.scrolling.render(delta);
		this.renderShoots(batch, delta);
		this.em.render(batch, delta);
		BonusManager.getInstance().render(this.getBatch(), delta);
		this.renderShip(delta);
		ExplosionManager.render(batch, delta);
		this.renderStatusBar();
		this.renderScore();
	}

	private void renderScore()
	{
		SpriteBatch batch = this.getBatch();
		FontUtils.print(batch, 5, Global.height - 43f, String.format("%010d", ScoreManager.getInstance().getScore()));
	}

	private void renderShoots(SpriteBatch batch, float delta)
	{
		ProjectileManager.getInstance().renderShoots(batch, delta);
	}

	private void renderStatusBar()
	{
		BitmapFont font = TrueTypeFont.DEFAULT.getFont();
		SpriteBatch batch = this.getBatch();
		int fps = Gdx.graphics.getFramesPerSecond();
		batch.draw(TextureAsset.FOOTER.get(), 0, 0);
		String titleBar = String.format(FMT_MSG_BAR, fps, ProjectileManager.getInstance().getShoots().size(), scrolling.getSpeed(), ship.getLifePoints(), ship.getShieldEnergy());
		font.draw(batch, titleBar, 6, 6 + font.getCapHeight());
	}

	private void renderShip(float delta)
	{
		ship.render(this.getBatch(), delta);
	}

	private void translateWorld(float delta)
	{
		em.act(delta);
	}

	private void checkInputKeys(float delta)
	{
		ShipHandler.handle(ship);
		ship.act(delta);
		ProjectileManager.checkFire(ship);
		this.checkEscape();
		this.scrolling.checkInput();
	}

	private void checkEscape()
	{
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			SoundAsset.CLIC.play();
			this.getControler().showScreen(XenonScreen.MENU);
		}
	}


	@Override
	public void show()
	{
		SoundAsset.MUSIC.loop(0.6f);
		ship = new Ship();
	}

	@Override
	public void hide()
	{
		SoundAsset.MUSIC.stop();
	}

	@Override
	public List<Artefact> getArtefacts()
	{
		List<Artefact> world = new LinkedList<>(em.getEnemies());
		world.addAll(ProjectileManager.getInstance().getShoots());
		return world;
	}

}
