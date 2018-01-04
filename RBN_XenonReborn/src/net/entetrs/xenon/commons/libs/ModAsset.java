package net.entetrs.xenon.commons.libs;

public enum ModAsset
{
	/* temporaire : utiliser implémentation XM : XENON_REMIX("mods/aa_xenon.xm") */
	FAKTORY("mods/faktory.mod"),
	DOODLE_DOO("mods/doodle-doo.mod"),
	TRANCE_124("mods/trance-124.mod"),
	BREATH_TAKER("mods/breathtaker.mod"),
	XENON_ORIGINAL("mods/xenon2.mod"),
	/* temporaire : utiliser implémentation XM : XENON_200("mods/xenon220.xm") */
	;

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
