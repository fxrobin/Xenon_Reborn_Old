package net.entetrs.xenon.entities;

public final class ShipControl
{
	private ShipControl()
	{
		// protection
	}
	
	public enum Horizontal
	{
		LEFT,RIGHT,NONE;
	}
	
	public enum Vertical
	{
		UP,DOWN,NONE;
	}
}
