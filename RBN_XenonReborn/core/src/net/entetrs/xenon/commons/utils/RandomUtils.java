package net.entetrs.xenon.commons.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * classe utilitaire liée à l'usage du hasard.
 * 
 * @author fxrobin
 *
 */
public class RandomUtils 
{
	private static Random random = new Random();
	
	/**
	 * retourne une valeur au hasard prise dans le tableau.
	 * 
	 * @param values
	 * 		tableau des valeurs
	 * @return instance prise au hasard
	 */
	@SuppressWarnings("unchecked")
	public static <T> T pick(T... values) 
	{
		List<T> data = Arrays.asList(values);
		Collections.shuffle(data);
		return data.get(0);
	}
	
	/**
	 * retourne un index au hasard conformément aux bornes 
	 * du tableau.
	 * 
	 * @param values
	 * 		tableau des valeurs.
	 * @return un index
	 * 		
	 */
	public static int pickIndex(Object[] values)
	{
		return random.nextInt(values.length);
	}
	
	
	/**
	 * retourne un float dans l'intervalle donné.
	 * 
	 * @param min
	 * 		borne min de l'intervalle
	 * @param max
	 * 		borne max de l'intervalle
	 * @return
	 *    float pris au hasard dans l'intervalle.
	 */
	public static float randomRange(float min, float max)
	{
		return random.nextFloat() * (max-min) + min;
	}

}
