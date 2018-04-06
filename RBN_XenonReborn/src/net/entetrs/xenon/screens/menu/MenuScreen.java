package net.entetrs.xenon.screens.menu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.displays.Blinker;
import net.entetrs.xenon.commons.displays.Interpolator;
import net.entetrs.xenon.commons.fonts.GdxBitmapString;
import net.entetrs.xenon.commons.fonts.GdxTrueTypeString;
import net.entetrs.xenon.commons.fonts.TrueTypeFont;
import net.entetrs.xenon.commons.libs.AnimationAsset;
import net.entetrs.xenon.commons.libs.ModAsset;
import net.entetrs.xenon.commons.libs.MusicAsset;
import net.entetrs.xenon.commons.libs.SoundAsset;
import net.entetrs.xenon.commons.libs.TextureAsset;
import net.entetrs.xenon.commons.utils.GdxCommons;
import net.entetrs.xenon.commons.utils.ModPlayer;
import net.entetrs.xenon.commons.utils.RandomUtils;
import net.entetrs.xenon.screens.AbstractScreen;
import net.entetrs.xenon.screens.XenonControler;
import net.entetrs.xenon.screens.XenonScreen;

public class MenuScreen extends AbstractScreen
{
	private static final String MSG = "PRESS SPACEBAR";
	private static Log log = LogFactory.getLog(MenuScreen.class);

	private BackgroundTravelling backgroundTravelling;
	
	private Blinker msgBlinker;
	private Interpolator interpolatorX;
	private Interpolator interpolatorY;

	private Texture titleTexture;
	private float titleX;
	private float titleY;

	private Monitor monitor;
	private DisplayMode currentMode;
	private GdxTrueTypeString message;
	
	private ModPlayer modPlayer;
	private int currentMusic;
	private GdxBitmapString pressSpaceBarMessage;
	
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
		modPlayer = ModPlayer.getInstance();
		currentMusic = RandomUtils.pickIndex(ModAsset.values());
		log.info("Instanciation de MenuScreen OK");	
	}

	private void createBlinkingMessage()
	{
		pressSpaceBarMessage = new GdxBitmapString(MSG, 1.5f);
		interpolatorX = new Interpolator(Interpolation.sine, 1f, 5, (Global.width - pressSpaceBarMessage.getWidth()) / 2f);
		interpolatorY = new Interpolator(Interpolation.pow2, 0.5f, 10, (float)(Global.height - titleTexture.getHeight()) / 2 - 50);
		pressSpaceBarMessage.setPosition(interpolatorX.getOriginalValue() , interpolatorY.getOriginalValue());
		msgBlinker = new Blinker(0.15f, pressSpaceBarMessage);
	}

	@Override
	public void show()
	{
		modPlayer.playLoop(ModAsset.values()[currentMusic].toString());
	}

	@Override
	public void hide()
	{
		modPlayer.stop();
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
			pressSpaceBarMessage.setPosition(interpolatorX.calculate(deltaTime), interpolatorY.calculate(deltaTime));
			this.msgBlinker.render(this.getBatch(), deltaTime);
			this.getBatch().end();
	}

	private void drawDisplayMode()
	{
		String msgDisplayMode = String.format("%s / %s / %d FPS", currentMode, monitor.name, Gdx.graphics.getFramesPerSecond());
		message.setText(msgDisplayMode);
		message.draw(this.getBatch(), (Global.width - message.getWidth()) / 2f, 60);	
		message.setText("< " + modPlayer.getMusicName() + " >");
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
			log.info("DisposeAll");
			AnimationAsset.disposeAll();
			MusicAsset.disposeAll();
			SoundAsset.disposeAll();
			TextureAsset.disposeAll();
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
		modPlayer.stop();
		modPlayer.playLoop(ModAsset.values()[currentMusic].toString());
	}
}
