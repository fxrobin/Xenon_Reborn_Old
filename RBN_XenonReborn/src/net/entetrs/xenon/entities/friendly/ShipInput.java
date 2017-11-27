package net.entetrs.xenon.entities.friendly;

public final class ShipInput
{
	private ShipInput()
	{
		// class utilitaire. Protection.
	}

	public enum Horizontal
	{
		LEFT, RIGHT, NONE;
	}

	public enum Vertical
	{
		UP, DOWN, NONE;
	}

}
