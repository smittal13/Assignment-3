package sorryclient;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/*
 * The main window for Sorry!
 * */
public class SorryClientWindow extends JFrame{
	private static final long serialVersionUID = 5147395078473323173L;
	
	//Dimensions for the game
	private final static Dimension minSize = new Dimension(640,480);
	private final static Dimension maxSize = new Dimension(960,640);
	
	//Create a menu bar and a help tab
	private JMenuBar jmb = new JMenuBar();
	private JMenuItem helpMenu = new JMenuItem("Help");
	private JMenuItem scoresMenu = new JMenuItem("Top Scores");
	private JPopupMenu jpm = new JPopupMenu();
	
	{ //Construct
		setTitle("Sorry!");
		setSize(minSize);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		add(new ClientPanel());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		//Add menu bar and help menu
		setJMenuBar(jmb);
		jmb.add(helpMenu);
		helpMenu.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
		
		//Modify the cursor
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("src/images/cursors/cursorHand_blue.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(0,0), "img");
		setCursor(c);
		
		//Create the high scores bar
		jmb.add(scoresMenu);
		scoresMenu.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		scoresMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTable j = InitializeTable();
				jpm.show(j, getWidth(), 0);
			}
		});
	}
	
	public JTable InitializeTable() {
		JTable scores;
		JScrollPane jsp = new JScrollPane();

		String [] headers = {"Name", "Score"};
		Object [][] data = {{"Player 1", 5}, {"Player 2", 4},
			{"Player 3", 3}, {"Player 4", 2}, {"Player 5", 1}};
		scores = new JTable(data, headers);
		scores.add(jsp);
		
		return scores;
	}
	
	public static void main(String[] args) {
		//Create a new SorryClient Window
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	new SorryClientWindow();
		    }
		});
	}
	
}
