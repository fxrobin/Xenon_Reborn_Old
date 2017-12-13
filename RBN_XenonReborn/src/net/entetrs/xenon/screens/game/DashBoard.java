package net.entetrs.xenon.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import net.entetrs.xenon.artefacts.friendly.Ship;
import net.entetrs.xenon.artefacts.managers.ScoreManager;
import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.fonts.FontUtils;
import net.entetrs.xenon.commons.fonts.TrueTypeFont;
import net.entetrs.xenon.commons.libs.TextureAsset;

/**
 * classe responsable des affichages "tableau" de bord : le score et la status bar.
 * 
 * @author francois.robin
 *
 */

public class DashBoard
{
	private static final String FMT_MSG_BAR = "XENON Reborn // FPS : %d // lifePoints : %d // Shield : %02.2f %%";

	private static final int MARGE = 10;
	
	private int hauteurBarre;
	private Color transparentGreen = new Color(0, 1f ,0 ,0.5f);
	private Color transparentRed = new Color(1f, 0 ,0 ,0.5f);
	
	private GamePlayScreen gamePlayScreen;
	private Texture footer;
	private BitmapFont font = TrueTypeFont.SHARETECH_12.getFont();

	public DashBoard(GamePlayScreen gamePlayScreen)
	{
		super();
		this.gamePlayScreen = gamePlayScreen;
		this.footer = TextureAsset.FOOTER.get();
		this.hauteurBarre = (Global.height - footer.getHeight() - (MARGE * 2) - 48 ); // 48 à cause du score.
	}
	
	public void render()
	{
		this.renderShieldBar();
		this.renderScore();
		this.renderStatusBar();
	}

	/**
	 * affiche le niveau d'énergie du bouclier.
	 */
	public void renderShieldBar()
	{
		/* on ferme le spriteBatch, cf doc libgdx */
		this.gamePlayScreen.getBatch().end();	
		Gdx.gl.glEnable(GL20.GL_BLEND);

		Ship ship = gamePlayScreen.getShip();	
		renderShieldBarOfShip(ship);
		
		/* on crée un nouveau spriteBatch, cf doc libgdx */
		this.gamePlayScreen.getBatch().begin();
	}

	/**
	 * @param shapeRenderer
	 * @param ship
	 */
	public void renderShieldBarOfShip(Ship ship)
	{
		ShapeRenderer shapeRenderer = gamePlayScreen.getShapeRenderer();
		Color color = computeShieldBarColor(ship);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color); 
		float currentSize = gamePlayScreen.getShip().getShieldEnergy() * hauteurBarre / 100f;
		shapeRenderer.rect(MARGE, footer.getHeight() + (float) MARGE, 50, currentSize);
		shapeRenderer.end();
	}

	/**
	 * @param ship
	 * @return
	 */
	public Color computeShieldBarColor(Ship ship)
	{
		return ship.getShieldEnergy() >= Global.SHIELD_ACTIVATION_THRESHOLD ? transparentGreen : transparentRed;
	}
		
	private void renderScore()
	{
		SpriteBatch batch = gamePlayScreen.getBatch();
		FontUtils.print(batch, 5, Global.height - 43f, String.format("%010d", ScoreManager.getInstance().getScore()));
	}
	
	private void renderStatusBar()
	{
		SpriteBatch batch = gamePlayScreen.getBatch();
		int fps = Gdx.graphics.getFramesPerSecond();
		batch.draw(footer, 0, 0, Global.width, footer.getHeight());
		Ship ship = gamePlayScreen.getShip();
		String titleBar = String.format(FMT_MSG_BAR, fps, ship.getLifePoints(), ship.getShieldEnergy());
		font.draw(batch, titleBar, 6, 6 + font.getCapHeight());
	}

}
