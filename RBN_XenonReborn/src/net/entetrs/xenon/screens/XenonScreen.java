package net.entetrs.xenon.screens;

import java.util.function.Supplier;

import com.badlogic.gdx.Screen;

/**
 * enum complexe avec lambda pour la création des écrans associé à leur
 * constructeur.
 * 
 * @author robin
 *
 */
public enum XenonScreen
{
	MENU(MenuScreen::new), GAME_PLAY(GamePlayScreen::new);

	private Supplier<Screen> supplier;

	private XenonScreen(Supplier<Screen> supplier)
	{
		this.supplier = supplier;
	}

	public Screen createScreen()
	{
		return supplier.get();
	}
}