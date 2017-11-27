package net.entetrs.xenon.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

public interface Artefact
{
	public abstract Circle getBoundingCircle();

	public void decreaseLife(int force);

	public int getImpactForce();

	public boolean isAlive();

	public Sprite getSprite();
}
