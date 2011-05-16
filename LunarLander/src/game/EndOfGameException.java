package game;

public class EndOfGameException extends Exception {

	/**
	 * Use the default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param s The message to be displayed at the end of the game
	 */
	public EndOfGameException(String s)
	{
		super(s);
	}
	
	/**
	 * @param s The message to be displayed at the end of the game
	 * @param inner An inner exception to wrap this exception with
	 */
	public EndOfGameException(String s, Exception inner)
	{
		super(s, inner);
	}
}
