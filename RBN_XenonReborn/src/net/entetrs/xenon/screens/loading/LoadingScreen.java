package net.entetrs.xenon.screens.loading;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.SingleExecutor;
import net.entetrs.xenon.commons.fonts.TrueTypeFont;
import net.entetrs.xenon.commons.libs.AssetLib;
import net.entetrs.xenon.commons.libs.SoundAsset;
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
	private BitmapFont font = TrueTypeFont.DEFAULT.getFont();
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
		layout.setText(font, message);
		font.draw(this.getBatch(), message, (Global.width - layout.width) / 2, (Global.height - layout.height) / 2);
	}

}
