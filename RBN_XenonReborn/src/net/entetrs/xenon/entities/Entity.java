package net.entetrs.xenon.entities;

import com.badlogic.gdx.math.Circle;

public interface Entity 
{	
	public abstract Circle getCircle();
	public void decreaseLife(int force);
	public int getImpactForce();
	public boolean isAlive();
}
