package game;

import java.util.Random;

/**
 * Implements the Singleton pattern for the generation of random numbers
 *
 */
public class LanderGameRandom
{
	/**
	 * The java.util.Random used to generate the random numbers
	 */
	private Random rnd;
	/**
	 * The Singleton instance of LanderGameRandom
	 */
	private static LanderGameRandom me = new LanderGameRandom();
	
	/**
	 * Since this is a Singleton we do not allow external callers to construct the object.
	 */
	private LanderGameRandom()
	{
		rnd = new Random();
	}
	
	/**
	 * @param n The maximum value of the random number desired
	 * @return A random integer between 0 and n
	 */
	public int nextInt(int n)
	{
		return rnd.nextInt(n);
	}
	
	/**
	 * @return A reference to the Singleton instance of LanderGameRandom
	 */
	public static LanderGameRandom getLanderGameRandom()
	{
		return me;
	}
}
