package net.entetrs.xenon.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	
	private GamePlayScreen gamePlayScreen;

	public DashBoard(GamePlayScreen gamePlayScreen)
	{
		super();
		this.gamePlayScreen = gamePlayScreen;
	}
	
	public void render()
	{
		this.renderScore();
		this.renderStatusBar();
	}
		
	private void renderScore()
	{
		SpriteBatch batch = gamePlayScreen.getBatch();
		FontUtils.print(batch, 5, Global.height - 43f, String.format("%010d", ScoreManager.getInstance().getScore()));
	}
	
	private void renderStatusBar()
	{
		BitmapFont font = TrueTypeFont.SHARETECH_12.getFont();
		SpriteBatch batch = gamePlayScreen.getBatch();
		int fps = Gdx.graphics.getFramesPerSecond();
		batch.draw(TextureAsset.FOOTER.get(), 0, 0);
		Ship ship = gamePlayScreen.getShip();
		String titleBar = String.format(FMT_MSG_BAR, fps, ship.getLifePoints(), ship.getShieldEnergy());
		font.draw(batch, titleBar, 6, 6 + font.getCapHeight());
	}

}
