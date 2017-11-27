package net.entetrs.xenon.entities.friendly;

public class ShipInput
{
	private ShipInput()
	{
		// protection
	}
	
	public  enum Horizontal
	{
		LEFT, RIGHT, NONE;
	}

	public  enum Vertical
	{
		UP, DOWN, NONE;
	}
}
