package net.entetrs.xenon.screens.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import net.entetrs.xenon.commons.Global;

public class TiledMapScrolling
{
	private OrthographicCamera cam;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private float speed;
	private SpriteBatch batch;

	public TiledMapScrolling(float speed)
	{
		this.speed = speed;
		batch = new SpriteBatch();
		tiledMap = new TmxMapLoader().load("maps/map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);

		cam = new OrthographicCamera(Global.width, Global.height);
		cam.position.set(Global.width / 2f - 112f, Global.height / 2f, 0);
		batch.setProjectionMatrix(cam.combined);
	}
	
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
	
	public void update(float delta)
	{
		cam.translate(0, speed * delta);
		cam.update();
	}

	public void render()
	{
		tiledMapRenderer.setView(cam);
		tiledMapRenderer.render();
	}

}
