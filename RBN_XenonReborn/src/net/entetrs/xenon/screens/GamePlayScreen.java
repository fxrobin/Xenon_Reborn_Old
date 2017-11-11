package net.entetrs.xenon.screens;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.MainControler;
import net.entetrs.xenon.commons.AnimatedSprite;
import net.entetrs.xenon.commons.C;
import net.entetrs.xenon.entities.Enemy;
import net.entetrs.xenon.entities.Ship;
import net.entetrs.xenon.helpers.AnimationLib;
import net.entetrs.xenon.helpers.FontLib;
import net.entetrs.xenon.helpers.SoundLib;
import net.entetrs.xenon.helpers.TextureManager;
import net.entetrs.xenon.managers.ExplosionManager;

public class GamePlayScreen implements Screen {
	private static final String FMT_MSG_BAR = "XENON Reborn // FPS : %d // nbLaser : %d // CurrentSpeed : %f ";

	private MainControler main;

	private Texture space;
	private Texture leftbg;
	private Texture rightbg;
	private Texture footer;

	private List<AnimatedSprite> shoots = new LinkedList<>();
	private List<Enemy> enemies = new LinkedList<>();
	private Ship ship;

	float position = 0;

	float speed = 3f;
	float speedLaser = 400f;
	long accumulator = 0;

	public GamePlayScreen(MainControler ctrl) {
		System.out.println("Instanciation de GamePlay");
		this.main = ctrl;
		space = TextureManager.BACKGROUND_SPACE.get();
		leftbg = TextureManager.BACKGROUND_LEFT.get();
		rightbg = TextureManager.BACKGROUND_RIGHT.get();
		footer = TextureManager.FOOTER.get();
	}

	private void generateEnemies() {
		accumulator++;
		int fps = Gdx.graphics.getFramesPerSecond();
		if (fps != 0 && accumulator / fps > 2) // toutes les 4 secondes
		{
			accumulator = 0; // on réinit.
			// on génère 5 enemies.
			for (int i = 0; i < 4; i++) {
				Enemy e = Enemy.random();
				enemies.add(e);
			}
		}
	}

	private void translateShoots(float delta) {
		shoots.forEach(s -> s.translateY(delta * speedLaser));
		shoots.removeIf(s -> s.getY() > C.HEIGHT);
	}

	private void translateEnemies(float delta) {
		enemies.forEach(e -> e.move(delta));
		enemies.removeIf(e -> e.getY() < -e.getHeight());
	}

	private void checkInput(float delta) {
		this.checkFire(delta);
		this.checkShield(delta);
		this.checkShipMoves(delta);
		this.checkEscape(delta);
		this.checkScrollingSpeed(delta);
		this.checkExtra(delta);
	}

	private void checkExtra(float delta) {
		if (Gdx.input.isKeyPressed(Keys.D)) {
			float x = (float) Math.random() * C.WIDTH;
			float y = (float) Math.random() * C.HEIGHT;
			ExplosionManager.addExplosion(x, y);
		}

	}

	private void checkScrollingSpeed(float delta) {
		if (Gdx.input.isKeyPressed(Keys.PAGE_UP))
			speed += 0.5f;

		if (Gdx.input.isKeyPressed(Keys.PAGE_DOWN))
			speed -= 0.5f;
	}

	private void checkEscape(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			SoundLib.CLIC.play();
			this.main.showScreen(MainControler.XenonScreen.MENU);
		}
	}

	private void checkShipMoves(float delta) {
		ship.checkShipMoves(delta);
	}

	private void checkShield(float delta) {
		ship.checkShield(delta);
	}

	private void checkFire(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.CONTROL_RIGHT) && !ship.isShieldActivated()) {
			AnimatedSprite s = AnimationLib.FRIENDLY_SHOOT.createAnimatedSprite();
			s.setOriginCenter();
			s.setCenter(ship.getCenterX(), ship.getCenterY());
			shoots.add(s);
			SoundLib.SHOOT.play();
		}
	}

	@Override
	public void render(float delta) {
		this.checkInput(delta);
		this.setSpritesAlpha(); // pour l'effet fade-in / fade-out
		this.generateEnemies();
		this.translateWorld(delta);
		this.drawWorld(delta);
	}

	private void setSpritesAlpha() {
		this.main.setSpriteAlpha(Arrays.asList(ship.getShipSprite(), ship.getShieldSprite()).stream());
		this.main.setSpriteAlpha(enemies.stream());
		this.main.setSpriteAlpha(shoots.stream());
	}

	private void drawWorld(float delta) {
		SpriteBatch batch = this.main.getBatch();
		this.drawBackgrounds();
		this.displayShoots(batch, delta);
		this.displayEnemies(batch);
		this.drawShip(delta);
		ExplosionManager.render(batch, delta);
		this.drawStatusBar();
	}

	private void displayShoots(SpriteBatch batch, float delta) {
		shoots.forEach(s -> s.render(batch, delta));
	}

	private void displayEnemies(SpriteBatch batch) {
		enemies.forEach(e -> e.draw(batch));
	}

	private void drawStatusBar() {
		BitmapFont font = FontLib.DEFAULT.getFont();
		SpriteBatch batch = this.main.getBatch();
		int FPS = Gdx.graphics.getFramesPerSecond();
		batch.draw(footer, 0, 0);
		String titleBar = String.format(FMT_MSG_BAR, FPS, shoots.size(), speed);
		font.draw(batch, titleBar, 6, 6 + font.getCapHeight());
	}

	private void drawShip(float delta) {
		ship.render(delta, this.main.getBatch());
	}

	private void drawBackgrounds() {
		SpriteBatch batch = this.main.getBatch();
		batch.draw(space, 0f, 0f, 0, (int) position, C.WIDTH, C.HEIGHT);
		batch.draw(leftbg, 0f, 0f, 0, (int) position * 2, leftbg.getWidth(), C.HEIGHT);
		batch.draw(rightbg, C.WIDTH - rightbg.getWidth(), 0f, 0, (int) position * 2, C.WIDTH, C.HEIGHT);
	}

	private void translateWorld(float delta) {
		this.translateShoots(delta);
		this.translateEnemies(delta);
		position -= 10f * delta * speed;
	}

	@Override
	public void show() {
		SoundLib.MUSIC.loop(0.6f);
		ship = new Ship();
		position = C.HEIGHT;
	}

	@Override
	public void hide() {
		SoundLib.MUSIC.stop();
	}

	@Override
	public void dispose() {

	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

}
