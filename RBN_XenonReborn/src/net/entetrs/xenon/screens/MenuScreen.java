package net.entetrs.xenon.screens;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.MainControler;
import net.entetrs.xenon.commons.FontCommons;
import net.entetrs.xenon.commons.R;
import net.entetrs.xenon.libs.FontLib;
import net.entetrs.xenon.libs.SoundLib;
import net.entetrs.xenon.libs.TextureLib;

public class MenuScreen extends AbstractScreen
{
	private static final String MSG = "PRESS SPACEBAR";
	private static final int MSG_WIDTH = FontCommons.getWidth(MSG);

	private Log log = LogFactory.getLog(this.getClass());

	private MainControler ctrl = MainControler.getInstance();

	private BackgroundTravelling backgroundTravelling;

	private Texture titleTexture;
	private float titleX;
	private float titleY;

	private Monitor monitor;
	private DisplayMode currentMode;
	private GlyphLayout layout;

	public MenuScreen()
	{
		log.info("Instanciation de MenuScreen");
		backgroundTravelling = new BackgroundTravelling();
		titleTexture = TextureLib.TITLE.get();
		titleX = (R.width - titleTexture.getWidth()) / 2f;
		titleY = (R.height - titleTexture.getHeight()) / 2f;
		monitor = Gdx.graphics.getMonitor();
		currentMode = Gdx.graphics.getDisplayMode(monitor);
		layout = new GlyphLayout();
	}

	@Override
	public void show()
	{
		SoundLib.INTRO.loop();
	}
	
	@Override
	public void hide()
	{
		SoundLib.INTRO.stop();
	}

	@Override
	public void render(float delta)
	{
		this.checkInput();
		this.backgroundTravelling.translateBackGround(delta);
		this.backgroundTravelling.drawBackGround(this.ctrl.getBatch());
		this.drawTitle();
		this.drawDisplayMode();
		this.drawMessage();
	}

	private void drawDisplayMode()
	{
		String msgDisplayMode = String.format("%s / %s", currentMode, monitor.name);
		layout.setText(FontLib.DEFAULT.getFont(), msgDisplayMode);
		BitmapFont font = FontLib.DEFAULT.getFont();
		font.draw(ctrl.getBatch(), msgDisplayMode, (R.width - layout.width) / 2, 150);
	}

	private void drawMessage()
	{
		FontCommons.print(ctrl.getBatch(), (R.width - MSG_WIDTH) / 2f, 60, MSG);
	}

	private void drawTitle()
	{
		SpriteBatch batch = ctrl.getBatch();
		batch.draw(titleTexture, titleX, titleY);
	}

	private void checkInput()
	{

		if (Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			SoundLib.CLIC.play();
			ctrl.showScreen(XenonScreen.GAME_PLAY);
		}

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			Gdx.app.exit();
		}
	}
}
