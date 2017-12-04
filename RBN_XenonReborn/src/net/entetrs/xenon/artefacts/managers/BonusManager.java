package net.entetrs.xenon.artefacts.managers;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.entetrs.xenon.artefacts.Artefact;
import net.entetrs.xenon.artefacts.extra.Bonus;
import net.entetrs.xenon.artefacts.extra.BonusType;
import net.entetrs.xenon.artefacts.friendly.Ship;
import net.entetrs.xenon.commons.libs.SoundLib;

public class BonusManager
{
	private List<Artefact> bonuses = new LinkedList<>();
	
	private static BonusManager instance = new BonusManager();
	
	public static BonusManager getInstance()
	{
		return instance;
	}
	
	private BonusManager()
	{
		// protection
	}
	
	public void addBonus(BonusType bonusType, float x, float y)
	{
		Bonus bonus = new Bonus(bonusType.createAnimatedSprite(), bonusType.getLifeForce(), bonusType.getLifeForce(), bonusType.getVX(), bonusType.getVY());
		bonus.getSprite().setCenter(x, y);
		bonuses.add(bonus);
		System.out.printf("Ajout d'un bonus %f,%f %n", bonus.getSprite().getX(), bonus.getSprite().getY());
	}
	
	public void render(SpriteBatch batch, float delta)
	{
		System.out.println("-------------------");
		for(Artefact bonus : bonuses)
		{
			bonus.render(batch, delta);
			System.out.printf("acting .. & affichage %f,%f %n", bonus.getSprite().getX(), bonus.getSprite().getY());
			
		}
		bonuses.removeIf(e -> e.getBoundingCircle().x < -e.getBoundingCircle().radius || !e.isAlive());
	}
	
	public void checkBonus(Ship ship)
	{
		for(Artefact bonus : bonuses)
		{
			if (bonus.isCollision(ship))
			{
				bonus.decreaseLife(100); // on tue le bonus...
				SoundLib.BONUS.play();
			}
		}
	}
}
