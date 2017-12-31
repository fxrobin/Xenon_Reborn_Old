package net.entetrs.xenon.screens.loading;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.SingleExecutor;
import net.entetrs.xenon.commons.fonts.TrueTypeFont;
import net.entetrs.xenon.commons.libs.AssetLib;
import net.entetrs.xenon.commons.libs.SoundAsset;
import net.entetrs.xenon.commons.libs.TextureAsset;
import net.entetrs.xenon.commons.utils.GdxCommons;
import net.entetrs.xenon.screens.AbstractScreen;
import net.entetrs.xenon.screens.XenonControler;
import net.entetrs.xenon.screens.XenonScreen;

/**
 * écran noir de chargement des assets.
 * 
 * @author robin
 *
 */
public class LoadingScreen extends AbstractScreen
{
	private Log log = LogFactory.getLog(this.getClass());
	private GlyphLayout layout;
	private BitmapFont font = TrueTypeFont.SHARETECH_30.getFont();
	private Texture background = TextureAsset.BACKGROUND_SPACE_DOCK.get();
	private SingleExecutor singleExecutor;

	public LoadingScreen(XenonControler controler, SpriteBatch batch)
	{
		super(controler, batch);
		log.info("Instanciation de LoadingScreen");
		layout = new GlyphLayout();
		singleExecutor = new SingleExecutor(() -> this.getControler().showScreen(XenonScreen.MENU));
	}

	@Override
	public void show()
	{
		SoundAsset.INTRO_SOUND.play();
	}

	@Override
	public void render(float delta)
	{
		checkInput();
		this.getBatch().begin();
		renderBackground();
		renderProgress();
		this.getBatch().end();
	}

	private void renderProgress()
	{
		String message = getProgressString();	
		layout.setText(font, message);
		font.draw(this.getBatch(), message, (Global.width - layout.width) / 2, (Global.height - layout.height) / 2);
	}

	private String getProgressString()
	{
		String message;
		// quand le loader n'a pas fini, on affiche une progression à l'écran.
		if (!AssetLib.getInstance().isLoadingFinished())
		{
			message = String.format("LOADING ... %02d %%", AssetLib.getInstance().getProgress());
		}
		else
		{
			message = "All resources are loaded ...";
			singleExecutor.execute(); // n'execute la méthode qu'une seule fois.
		}
		return message;
	}

	private void renderBackground()
	{
		this.getBatch().draw(background, 0, 0, Global.width, Global.height);
	}

	private void checkInput()
	{
		if (Gdx.input.isKeyJustPressed(Keys.F1))
		{
			GdxCommons.switchFullScreen();
		}
	}

}
