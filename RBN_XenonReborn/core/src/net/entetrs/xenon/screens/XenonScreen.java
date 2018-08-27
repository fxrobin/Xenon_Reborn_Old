package net.entetrs.xenon.screens;

import java.util.function.BiFunction;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.screens.game.GamePlayScreen;
import net.entetrs.xenon.screens.loading.LoadingScreen;
import net.entetrs.xenon.screens.menu.MenuScreen;

/**
 * enum complexe avec lambda pour la création des écrans associés à leur
 * constructeur.
 * 
 * @author robin
 *
 */
public enum XenonScreen
{
	LOADING(LoadingScreen::new), 
	MENU(MenuScreen::new), 
	GAME_PLAY(GamePlayScreen::new);

	private BiFunction<XenonControler, SpriteBatch, Screen> supplier;

	private XenonScreen(BiFunction<XenonControler, SpriteBatch, Screen> supplier)
	{
		this.supplier = supplier;
	}

	public Screen createScreen(XenonControler controler)
	{
		return supplier.apply(controler, controler.getBatch());
	}
}