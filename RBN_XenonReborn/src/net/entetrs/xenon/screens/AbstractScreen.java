package net.entetrs.xenon.screens;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen
{	
	private Log log = LogFactory.getLog(this.getClass());


	@Override
	public void hide()
	{
		log.info("HIDE SCREEN: NO ACTION");
	}

	@Override
	public void pause()
	{
		log.info("PAUSE SCREEN : NO ACTION");
	}

	@Override
	public void resize(int arg0, int arg1)
	{
		log.info("RESIZE SCREEN : NO ACTION");

	}

	@Override
	public void resume()
	{
		log.info("RESUME SCREEN : NO ACTION");
	}
	
	@Override
	public void dispose()
	{
		log.info("DISPOSE SCREEN : NO ACTION");	
	}

}
