package game;

import java.util.*;

/**
 * Player is resposible for getting input from the human.
 *
 */
public class Player {

	/**
	 * A Scanner for reading input.
	 */
	Scanner scan;
	
	/**
	 * Initialize the Scanner with console input.
	 */
	public Player()
	{
		scan = new Scanner(System.in);
	}
	
	/**
	 * @return The character the human typed on the keyboard
	 */
	public char getCommand()
	{
		String s = scan.next();
        return s.charAt(0);
	}
}
