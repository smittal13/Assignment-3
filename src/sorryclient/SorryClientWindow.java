package sorryclient;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
	private JTextArea helpText;
	
	private JTable j;
	private JFrame highScores, helpInfo;
	
	private final String help = "Sorry!\nTo Start the Game:\n   Click 'Start'"
		+ "\n   Select the number of players\n   Select your color"
		+ "\nTo Play the Game:\n   Draw a card"
		+ "\n   If possible, make a valid move\nTo Win the Game:"
		+ "\n   The player who moves all their pawns to home first wins!";
	
	Font f;
	
	{ //Construct
		setTitle("Sorry!");
		setSize(minSize);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		add(new ClientPanel());
		setLocationRelativeTo(null);
		setJMenuBar(jmb);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		InitializeHelpText();
		InitializeTable();
		InitializeMenuBar();
	}
	
	public void InitializeTable() {
		String [] headers = {"Name", "Score"};
		
		Object [][] data = null; 
		
		try {
			Scanner text = new Scanner(new File("src/readme.txt"));
			String s = text.toString();
			System.out.println("s");
			Object [] lines = s.split("\n");
			data = new Object[lines.length][lines.length];
			for (int i = 0; i < lines.length; i ++) {
				Object [][] temp = {(Object[]) lines[0], (Object[]) lines[1]};
				data[i][i] = temp;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		/*data = {{"Player 1", 5}, {"Player 2", 4},
			{"Player 3", 3}, {"Player 4", 2}, {"Player 5", 1}};*/
		j = new JTable(data, headers);
		j.add(new JScrollPane());
		
	}
	
	public void InitializeHelpText() {
		//Set text area functionality and add to help menu
		helpText = new JTextArea(help);
		helpText.setLineWrap(true);
		helpText.setWrapStyleWord(true);
		helpText.setOpaque(false);
		helpText.setEditable(false);
		helpText.add(new JScrollPane());
	}
	
	private void InitializeMenuBar() {
		//Create JFrames for the help menu and high scores
		highScores = new JFrame("High Scores");
		helpInfo = new JFrame("Help");
		
		//Get standard font
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
			f = f.deriveFont(18f);
			helpText.setFont(f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Set functionality for help menu and scores table
		helpInfo.setSize(250, 450);
		helpInfo.add(helpText);
		highScores.setSize(150, 200);
		highScores.add(j);

		//Create the help menu and key functionality
		jmb.add(helpMenu);
		helpMenu.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
		helpMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				helpInfo.setLocationRelativeTo(helpMenu);
				helpInfo.setVisible(true);
			}
		});
				
		//Create the high scores bar and key functionality
		jmb.add(scoresMenu);
		scoresMenu.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		scoresMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				highScores.setLocationRelativeTo(scoresMenu);
				highScores.setVisible(true);
			}
		});

		//Modify the cursor
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("src/images/cursors/cursorHand_blue.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(0,0), "img");
		setCursor(c);
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
