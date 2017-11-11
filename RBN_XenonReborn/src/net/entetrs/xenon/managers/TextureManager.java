package net.entetrs.xenon.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.Disposable;

import net.entetrs.xenon.commons.GdxCommons;

public class TextureManager implements Disposable
{

	private Texture leftbg;
	private Texture rightbg;
	private Texture space;
	private Texture shoot;
	private Texture footer;
	private Texture enemy;
	private Texture bug;
	private Texture perforator;
	private Texture shield;
	private Texture ship;
	private Texture shipLeft;
	private Texture shipRight;
	private Texture shipNoReactor;
	private Texture title;

	private Texture fontAZ;
	private Texture font09;

	public TextureManager()
	{

		// loading main title
		title = new Texture("commons/xenon-reborn.png");

		// loading backgrounds
		footer = new Texture("backgrounds/footer.png");
		leftbg = new Texture("backgrounds/left_bg.png");
		rightbg = new Texture("backgrounds/right_bg.png");
		space = new Texture("backgrounds/space.jpg");

		// loading enemies
		enemy = new Texture("enemies/enemy.png");
		bug = new Texture("enemies/bug.png");
		perforator = new Texture("enemies/perforator.png");

		// loading ships and ship-addons
		shield = new Texture("ships/shield.png");
		ship = new Texture("ships/ship_normal.png");
		shipLeft = new Texture("ships/ship_left.png");
		shipRight = new Texture("ships/ship_right.png");
		shipNoReactor = new Texture("ships/ship_noreactor.png");

		// loading shoots & explosions
		shoot = new Texture("shoots/shoot.png");

		// loading font
		fontAZ = new Texture("fonts/font-AZ.png");
		font09 = new Texture("fonts/font-09.png");

		// wrapping pour la répétition des backgrounds
		space.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
		leftbg.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		rightbg.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	}

	public Texture getLeftbg()
	{
		return leftbg;
	}

	public Texture getRightbg()
	{
		return rightbg;
	}

	public Texture getSpace()
	{
		return space;
	}

	public Texture getShoot()
	{
		return shoot;
	}

	public Texture getFooter()
	{
		return footer;
	}

	public Texture getEnemy()
	{
		return enemy;
	}

	public Texture getBug()
	{
		return bug;
	}

	public Texture getPerforator()
	{
		return perforator;
	}

	public Texture getShield()
	{
		return shield;
	}

	public Texture getShip()
	{
		return ship;
	}

	public Texture getShipLeft()
	{
		return shipLeft;
	}

	public Texture getShipRight()
	{
		return shipRight;
	}

	public Texture getShipNoReactor()
	{
		return shipNoReactor;
	}

	public Texture getTitle()
	{
		return title;
	}

	public Texture getFont09()
	{
		return font09;
	}

	public Texture getFontAZ()
	{
		return fontAZ;
	}

	@Override
	public void dispose()
	{
		System.out.print("DISPOSE TEXTURES ...");
		GdxCommons.disposeAll(leftbg, rightbg, space, shoot, ship, footer, enemy, bug, perforator, shield, title);
		GdxCommons.disposeAll(fontAZ, font09);
		System.out.println("DONE");
	}

}
