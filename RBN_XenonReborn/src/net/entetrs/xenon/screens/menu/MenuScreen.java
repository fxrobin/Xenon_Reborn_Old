package net.entetrs.xenon.screens.menu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.displays.Blinker;
import net.entetrs.xenon.commons.fonts.GdxBitmapString;
import net.entetrs.xenon.commons.fonts.GdxTrueTypeString;
import net.entetrs.xenon.commons.fonts.TrueTypeFont;
import net.entetrs.xenon.commons.libs.ModAsset;
import net.entetrs.xenon.commons.libs.SoundAsset;
import net.entetrs.xenon.commons.libs.TextureAsset;
import net.entetrs.xenon.commons.utils.GdxCommons;
import net.entetrs.xenon.commons.utils.ModPlayer;
import net.entetrs.xenon.screens.AbstractScreen;
import net.entetrs.xenon.screens.XenonControler;
import net.entetrs.xenon.screens.XenonScreen;

public class MenuScreen extends AbstractScreen
{
	private static final String MSG = "PRESS SPACEBAR";

	private Log log = LogFactory.getLog(this.getClass());

	private BackgroundTravelling backgroundTravelling;
	
	private Blinker msgBlinker;

	private Texture titleTexture;
	private float titleX;
	private float titleY;

	private Monitor monitor;
	private DisplayMode currentMode;
	private GdxTrueTypeString message;
	private int currentMusic = 0;

	public MenuScreen(XenonControler controler, SpriteBatch batch)
	{
		super(controler, batch);
		log.info("Instanciation de MenuScreen");
		backgroundTravelling = new BackgroundTravelling();
		titleTexture = TextureAsset.TITLE.get();
		titleX = (Global.width - titleTexture.getWidth()) / 2f;
		titleY = (Global.height - titleTexture.getHeight()) / 2f;
		monitor = Gdx.graphics.getMonitor();
		currentMode = Gdx.graphics.getDisplayMode(monitor);
		message = new GdxTrueTypeString(TrueTypeFont.SHARETECH_30.getFont(), "");	
		this.createBlinkingMessage();
		
		
	}

	private void createBlinkingMessage()
	{
		GdxBitmapString pressSpaceBarMessage = new GdxBitmapString(MSG);
		pressSpaceBarMessage.setPosition((Global.width - pressSpaceBarMessage.getWidth()) / 2f, (float)(Global.height - titleTexture.getHeight()) / 2 - 50);
		msgBlinker = new Blinker(1f, pressSpaceBarMessage);
	}

	@Override
	public void show()
	{
		ModPlayer.play(ModAsset.values()[currentMusic].toString());
	}

	@Override
	public void hide()
	{
		ModPlayer.stop();
	}

	@Override
	public void render(float deltaTime)
	{
			this.checkInput();
			this.backgroundTravelling.translateBackGround(deltaTime);
			
			this.getBatch().begin();
			this.backgroundTravelling.drawBackGround(this.getBatch());
			this.drawTitle();
			this.drawDisplayMode();
			this.msgBlinker.render(this.getBatch(), deltaTime);
			this.getBatch().end();
	}

	private void drawDisplayMode()
	{
		String msgDisplayMode = String.format("%s / %s / %d FPS", currentMode, monitor.name, Gdx.graphics.getFramesPerSecond());
		message.setText(msgDisplayMode);
		message.draw(this.getBatch(), (Global.width - message.getWidth()) / 2f, 60);	
		message.setText("< " + ModPlayer.getMusicName() + " >");
		message.draw(this.getBatch(), (Global.width - message.getWidth()) / 2f, 120);
		message.setText(String.format("Virtual Width : %d px / Virtual Height : %d px", Global.width, Global.height));
		message.draw(this.getBatch(), (Global.width - message.getWidth()) / 2f, 30);
	}


	private void drawTitle()
	{
		SpriteBatch batch = this.getBatch();
		batch.draw(titleTexture, titleX, titleY);
	}

	private void checkInput()
	{
		checkNextScreen();
		checkMetaKeys();
		checkMusicSwitcher();		
	}

	private void checkNextScreen()
	{
		if (Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			SoundAsset.CLIC.play();
			this.getControler().showScreen(XenonScreen.GAME_PLAY);
		}
	}

	private void checkMetaKeys()
	{
		if (Gdx.input.isKeyJustPressed(Keys.F1))
		{
			GdxCommons.switchFullScreen();
		}

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			Gdx.app.exit();
		}
	}

	private void checkMusicSwitcher()
	{
		if (Gdx.input.isKeyJustPressed(Keys.LEFT) && currentMusic > 0)
		{
			currentMusic--;
			updateMusic();
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && currentMusic < ModAsset.values().length-1)
		{
			currentMusic++;
			updateMusic();
		}
	}

	private void updateMusic()
	{
		ModPlayer.stop();
		ModPlayer.play(ModAsset.values()[currentMusic].toString());
	}
}
