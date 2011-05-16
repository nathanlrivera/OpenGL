package game;
import javax.swing.*;
import javax.swing.border.LineBorder;

import commands.AboutCommand;
import commands.HelpCommand;
import commands.NewGameCommand;
import commands.QuitCommand;
import commands.SaveCommand;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Starts a new game of Lunar Lander.
 *
 */
public class LanderGame extends JFrame implements WindowListener
{
	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private QuitCommand quitCommand;
	
	private GameWorld gw;

	/**
	 * Create a LanderGame object with an empty world.
	 */
	public LanderGame()
	{
		newGame();
	}
	
	public void newGame()
	{
		this.getContentPane().removeAll();
		gw = new GameWorld();
		Scoreboard sb = new Scoreboard();
		MapView map = new MapView();
		gw.addObserver(map);
		ScoreboardView score = new ScoreboardView();
		sb.addObserver(score);
		
		setTitle("Lunar Lander");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setResizable(false);
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		setLayout(new BorderLayout());
		
		quitCommand = new QuitCommand();
		HelpCommand helpCommand = new HelpCommand();
		NewGameCommand newGameCommand = new NewGameCommand(this);
		GameController gameController = new GameController(gw, sb, helpCommand, quitCommand, newGameCommand);
		getContentPane().add(gameController, BorderLayout.WEST);
		score.setBackground(Color.white);
		score.setBorder(new LineBorder(Color.blue));
		getContentPane().add(score, BorderLayout.NORTH);
		map.setBackground(Color.black);
		getContentPane().add(map, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem(newGameCommand));
		fileMenu.add(new JMenuItem(new SaveCommand()));
		
		fileMenu.add(new JMenuItem(quitCommand));
		menuBar.add(fileMenu);
		
		JMenu helpMenu = new JMenu("Help");
		
		helpMenu.add(new JMenuItem(helpCommand));
		helpMenu.add(new JMenuItem(new AboutCommand()));

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyHandler());

		setVisible(true);
		
		gw.initializeWorld(sb);
		
		gw.notifyObservers();
		sb.notifyObservers();
	}

	public void windowActivated(WindowEvent e)
	{
	}

	public void windowClosed(WindowEvent e)
	{
	}

	public void windowClosing(WindowEvent e)
	{
		quitCommand.actionPerformed(null);
	}

	public void windowDeactivated(WindowEvent e)
	{
	}

	public void windowDeiconified(WindowEvent e)
	{
	}

	public void windowIconified(WindowEvent e)
	{
	}

	public void windowOpened(WindowEvent e)
	{
	}

	private class KeyHandler implements KeyEventDispatcher
	{
		public boolean dispatchKeyEvent(KeyEvent arg0)
		{
			switch (arg0.getKeyCode())
			{
			case KeyEvent.VK_DOWN:
				gw.fireBottomJet();
				break;
			case KeyEvent.VK_LEFT:
				gw.fireLeftJet();
				break;
			case KeyEvent.VK_RIGHT:
				gw.fireRightJet();
				break;
			}
			return false;
		}
	}
}
