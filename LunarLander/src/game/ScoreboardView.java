package game;

import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Implements a View of the Scoreboard in the game.
 *
 * Represents the View part of the Model View Controller architecture.
 */
public class ScoreboardView extends JPanel implements Observer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel theLabel;
	
	public ScoreboardView()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER));
		theLabel = new JLabel();
		add(theLabel);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg)
	{
		Scoreboard score = (Scoreboard)o;
		theLabel.setText("Elapsed time: " + score.getTimeElapsed() + "     Remaining lives: " + score.getLivesRemaining() + "     Score: " + score.getPointsEarned());
	}
}
