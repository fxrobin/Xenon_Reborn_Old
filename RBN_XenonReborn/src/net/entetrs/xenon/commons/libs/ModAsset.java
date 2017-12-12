package net.entetrs.xenon.commons.libs;

public enum ModAsset
{
	FAKTORY("mods/faktory.mod"),
	DOODLE_DOO("mods/doodle-doo.mod"),
	TRANCE_124("mods/trance-124.mod"),
	BREATH_TAKER("mods/breathtaker.mod");

	final String modName;

	private ModAsset(String modName)
	{
		this.modName = modName;
	}

	@Override
	public String toString()
	{
		return modName;
	}
	
}
