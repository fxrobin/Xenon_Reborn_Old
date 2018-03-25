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
import net.entetrs.xenon.artefacts.managers.ScoreManager;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.SingleExecutor;
import net.entetrs.xenon.commons.displays.Blinker;
import net.entetrs.xenon.commons.fonts.GdxBitmapString;
import net.entetrs.xenon.commons.libs.ModAsset;
import net.entetrs.xenon.commons.libs.MusicAsset;
import net.entetrs.xenon.commons.libs.SoundAsset;
import net.entetrs.xenon.commons.libs.TextureAsset;
import net.entetrs.xenon.commons.utils.GdxCommons;
import net.entetrs.xenon.commons.utils.ModPlayer;
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
	
	private Blinker msgBlinker;
	private SingleExecutor gameOverSoundExecutor;

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
		this.createBlinkingMessage();
		msgBlinker.hide();
		gameOverSoundExecutor = new SingleExecutor(SoundAsset.GAME_OVER::play);
	}
	
	private void createBlinkingMessage()
	{
		GdxBitmapString gameOverMessage = new GdxBitmapString("GAME OVER");
		gameOverMessage.setPosition((Global.width - gameOverMessage.getWidth()) / 2f, (float)(Global.height - TextureAsset.TITLE.get().getHeight() /2) / 2 );
		msgBlinker = new Blinker(1f, gameOverMessage, 5, this::closeGame);
	}
	
	private void closeGame()
	{
		this.getControler().showScreen(XenonScreen.MENU);
	}
	

	@Override
	public void render(float delta)
	{
		this.checkInputKeys(delta);
		em.generateEnemies(delta);
		this.translateWorld(delta);
		List<Artefact> allPlayerObjects = new LinkedList<>(ProjectileManager.getInstance().getShoots());
		if (!ship.isFullyDestroyed()) allPlayerObjects.add(ship);
		cm.checkCollision(em.getEnemies(), allPlayerObjects);
		BonusManager.getInstance().checkBonus(ship);
		this.renderWorld(delta);
	}

	private void renderWorld(float delta)
	{
		SpriteBatch batch = this.getBatch();
		
		batch.begin();
		this.scrolling.render(delta);
		this.renderShoots(batch, delta);
		this.em.render(batch, delta);
		BonusManager.getInstance().render(this.getBatch(), delta);
		this.renderShipOrGameOver(delta, batch);
		ExplosionManager.render(batch, delta);
		batch.end();
		
		/* le dashboard doit s'afficher en dehors du spritebatch précédent */
		dashBoard.render();
		
	}

	private void renderShipOrGameOver(float delta, SpriteBatch batch)
	{
		if (ship.isFullyDestroyed())
		{
			gameOverSoundExecutor.execute();
			renderGameOverBlinker(delta, batch);
			GdxBitmapString yourScore = new GdxBitmapString("SCORE " + ScoreManager.getInstance().getScore());
			yourScore.setPosition((Global.width - yourScore.getWidth()) / 2f, (float)(Global.height - TextureAsset.TITLE.get().getHeight() /2) / 2 - 50f );
			yourScore.render(batch, delta);
		}
		else
		{
			this.renderShip(delta);
		}
	}

	private void renderGameOverBlinker(float delta, SpriteBatch batch)
	{
		if (msgBlinker.isHidden())
		{
			msgBlinker.show();
		}
		else
		{
			msgBlinker.render(batch, delta);
		}
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
		ShipHandler.handle(ship,delta);
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
			this.closeGame();
		}
		
		/* test avec une référence de méthode : c'est plus joli ... */
		GdxCommons.runIfKeyJustPressed(Keys.F1, GdxCommons::switchFullScreen);

	}

	@Override
	public void show()
	{
		ModPlayer.getInstance().play(ModAsset.XENON_REMIX.toString());
		// MusicAsset.MUSIC.loop(0.6f);
	}

	@Override
	public void hide()
	{
		MusicAsset.MUSIC.fadeOut();
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
