package sorryclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/*
 * ColorSelector
 * Menu to select Red,Blue,Green,Yellow
 * */
public class ColorSelector extends JPanel {
	
	private static final long serialVersionUID = 1900724217285760485L;
	
	//The options for color selection
	private Color selection;
	private final int numOptions = 4;
	private final JButton[] optionButtons;
	
	private final JButton confirmButton;
	
	private final static String selectColorString = "Select your color";
	
	private final static String[] colorNames = {"Red", "Blue", "Green", "Yellow"};
	private final static Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
	private final static String[] ColorBack = {"src/images/buttons/red_button00.png",
		"src/images/buttons/blue_button00.png", "src/images/buttons/green_button00.png",
		"src/images/buttons/yellow_button00.png"}; 
	
	//For spacing to the borders
	private final static Insets spacing = new Insets(40,40,40,40);
	
	public Color getPlayerColor() {
		return selection;
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			Image i = ImageIO.read(new File("src/images/panels/grey_panel.png"));
			g.drawImage(i, 0, 0, getWidth(), getHeight(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ColorSelector(ActionListener confirmAction) {
		//set up the button so we can proceed
		ImageIcon img = new ImageIcon("src/images/buttons/grey_button00.png");
		confirmButton = new JButton("Confirm", img);
		confirmButton.addActionListener(confirmAction);
		confirmButton.setEnabled(false);
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
			f = f.deriveFont(16f);
			confirmButton.setFont(f);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		confirmButton.setContentAreaFilled(false);
		confirmButton.setBorderPainted(false);
		confirmButton.setHorizontalTextPosition(JButton.CENTER);
		
		//The top of the panel, the label
		JLabel selectPlayerLabel = new JLabel(selectColorString);
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
			f = f.deriveFont(24f);
			selectPlayerLabel.setFont(f);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createEmptyBorder(75, 0, 0, 0));
		topPanel.setOpaque(false);
		topPanel.add(selectPlayerLabel);
		
		//The middle of the panel, the color buttons
		JPanel centerPanel = new JPanel(new GridLayout(2,2,20,20));
		centerPanel.setOpaque(false);
		Font buttonFont = new Font("",Font.BOLD,22);
		optionButtons = new JButton[numOptions];
		for(int i = 0; i < numOptions; ++i) {
			ImageIcon ii = new ImageIcon(ColorBack[i]);
			optionButtons[i] = new JButton(colorNames[i], ii);
			optionButtons[i].setBackground(colors[i]);
			final int buttonSelection = i;
			optionButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					selection = colors[buttonSelection];
					for(JButton button : optionButtons) button.setEnabled(true);
					optionButtons[buttonSelection].setEnabled(false);
					confirmButton.setEnabled(true);
				}
			});
			optionButtons[i].setFont(buttonFont);
			try {
				Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
				f = f.deriveFont(16f);
				optionButtons[i].setFont(f);
			} catch (FontFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			optionButtons[i].setContentAreaFilled(false);
			optionButtons[i].setBorderPainted(false);
			optionButtons[i].setHorizontalTextPosition(JButton.CENTER);
			centerPanel.add(optionButtons[i]);
		}
		centerPanel.setBorder(new EmptyBorder(spacing));
		
		//The bottom of the panel, the confirm button
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
		bottomPanel.setBorder(new EmptyBorder(spacing));
		bottomPanel.add(Box.createGlue());
		bottomPanel.add(confirmButton);
		
		setLayout(new BorderLayout());
		
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

}
