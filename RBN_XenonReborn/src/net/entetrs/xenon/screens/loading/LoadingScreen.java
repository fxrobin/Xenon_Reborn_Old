package net.entetrs.xenon.screens.loading;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;

import net.entetrs.xenon.commons.Global;
import net.entetrs.xenon.commons.SingleExecutor;
import net.entetrs.xenon.commons.displays.Interpolator;
import net.entetrs.xenon.commons.fonts.TrueTypeFont;
import net.entetrs.xenon.commons.libs.AssetLib;
import net.entetrs.xenon.commons.libs.ModAsset;
import net.entetrs.xenon.commons.libs.TextureAsset;
import net.entetrs.xenon.commons.utils.GdxCommons;
import net.entetrs.xenon.commons.utils.ModPlayer;
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
	private BitmapFont font = TrueTypeFont.SHARETECH_30_BLACK.getFont();
	private Texture background = TextureAsset.BACKGROUND_BOMBING_PIXELS.get();
	private SingleExecutor singleExecutor;
	private Interpolator interpolatorX = new Interpolator(Interpolation.sine, 2f, 20, 0);
	private Interpolator interpolatorY = new Interpolator(Interpolation.pow2, 1f, 20, 0);

	public LoadingScreen(XenonControler controler, SpriteBatch batch)
	{
		super(controler, batch);
		ModPlayer.getInstance();
		log.info("Instanciation de LoadingScreen");
		layout = new GlyphLayout();
		singleExecutor = new SingleExecutor(() -> {
			this.getControler().showScreen(XenonScreen.MENU);
			ModPlayer.getInstance().stop();
		});
	}

	@Override
	public void show()
	{
		ModPlayer.getInstance().play(ModAsset.INTRO.toString());
	}

	@Override
	public void render(float delta)
	{
		GdxCommons.clearScreen(Color.WHITE);
		this.checkInput();
		
		
		this.getBatch().begin();
		this.renderBackground(delta);
		this.renderProgress();
		this.getBatch().end();
		this.renderMusicBars();
	}

	private void renderMusicBars()
	{
		Gdx.gl.glEnable(GL20.GL_BLEND);
		ShapeRenderer shapeRenderer = this.getShapeRenderer();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED); 
		shapeRenderer.rect(10 , 10 , 50, ModPlayer.getInstance().getLeftLevel());
		shapeRenderer.setColor(Color.GREEN); 
		shapeRenderer.rect(Global.width - 50 - 10 , 10 , 50, ModPlayer.getInstance().getRightLevel());
		shapeRenderer.end();
		
	}

	private void renderProgress()
	{
		String message = getProgressString();
		layout.setText(font, message);
		font.draw(this.getBatch(), message, (Global.width - layout.width) / 2, 80);
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
			message = "All resources are loaded ... PRESS SpaceBar";
		}
		return message;
	}

	private void renderBackground(float delta)
	{
		float positionX = interpolatorX.calculate(delta);
		float positionY = interpolatorY.calculate(delta);
		this.getBatch().draw(background, positionX, positionY, Global.width, Global.height);
	}

	private void checkInput()
	{
		if (Gdx.input.isKeyJustPressed(Keys.F1))
		{
			GdxCommons.switchFullScreen();
		}

		if (Gdx.input.isKeyJustPressed(Keys.SPACE) && AssetLib.getInstance().isLoadingFinished())
		{
			singleExecutor.execute(); // n'execute la méthode qu'une seule fois.
		}
	}

}
