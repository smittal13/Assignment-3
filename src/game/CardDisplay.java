package game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CardDisplay extends JFrame {

	JPanel main;
	JLabel number;
	JTextArea function;
	ImageIcon backing;
	Font f;
	
	public CardDisplay(String s) {
		//Initialize window
		super("Card");
		setSize(300,400);
		setLocation(100,100);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		//Get and set backing of the card
		backing = new ImageIcon("src/images/cards/card_brown.png");
		main = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponents(g);
				g.drawImage(backing.getImage(), 0, 0, getWidth(), getHeight(), null);
				setOpaque(false);
			}
		};
		
		//Get standard font
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Arrange panel
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.setBorder(BorderFactory.createEmptyBorder(50, 20, 0, 20));
		
		//Split 
		String [] x = s.split("- ");
		
		//Add values
		number = new JLabel(x[0]);
		function = new JTextArea(x[1]);
		
		//Set text area functionality
		function.setLineWrap(true);
		function.setWrapStyleWord(true);
		function.setOpaque(false);
		function.setEditable(false);
		
		//Change font
		f = f.deriveFont(34f);
		number.setFont(f);
		f = f.deriveFont(18f);
		function.setFont(f);
		
		//Center text
		number.setAlignmentX(CENTER_ALIGNMENT);
		function.setAlignmentX(CENTER_ALIGNMENT);

		//Add to main panel
		main.add(number);
		main.add(function);
		
		//Finish up initialization
		add(main);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
