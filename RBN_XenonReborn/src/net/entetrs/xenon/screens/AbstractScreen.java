package net.entetrs.xenon.screens;

import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen
{

	@Override
	public void hide()
	{
		System.out.println("HIDE SCREEN: NO ACTION");
	}

	@Override
	public void pause()
	{
		System.out.println("PAUSE SCREEN : NO ACTION");
	}

	@Override
	public void resize(int arg0, int arg1)
	{
		System.out.println("RESIZE SCREEN : NO ACTION");

	}

	@Override
	public void resume()
	{
		System.out.println("RESUME SCREEN : NO ACTION");
	}
	
	@Override
	public void dispose()
	{
		System.out.println("DISPOSE SCREEN : NO ACTION");	
	}

}
