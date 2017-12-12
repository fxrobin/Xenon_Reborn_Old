package net.entetrs.xenon.screens.game;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import net.entetrs.xenon.commons.libs.SoundAsset;
import net.entetrs.xenon.commons.utils.GdxCommons;
import net.entetrs.xenon.screens.AbstractScreen;
import net.entetrs.xenon.screens.XenonControler;
import net.entetrs.xenon.screens.XenonScreen;

public class GamePlayScreen extends AbstractScreen implements ArtefactsScene
{

	private Log log = LogFactory.getLog(this.getClass());

	private BackgroundParallaxScrolling scrolling;
	private DashBoard dashBoard;
	
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
		dashBoard = new DashBoard(this);
		ship = new Ship();
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
		dashBoard.render();
	}

	

	private void renderShoots(SpriteBatch batch, float delta)
	{
		ProjectileManager.getInstance().renderShoots(batch, delta);
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
		this.checkEscapeAndFullScreen();
		this.scrolling.checkInput();
	}

	private void checkEscapeAndFullScreen()
	{
		/* test avec un if classique */
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			SoundAsset.CLIC.play();
			this.getControler().showScreen(XenonScreen.MENU);
		};
		
		/* test avec une référence de méthode : c'est plus joli ... */
		GdxCommons.runIfKeyJustPressed(Keys.F1, GdxCommons::switchFullScreen);

	}

	@Override
	public void show()
	{
		SoundAsset.MUSIC.loop(0.6f);
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
	
	public Ship getShip()
	{
		return ship;
	}

}
