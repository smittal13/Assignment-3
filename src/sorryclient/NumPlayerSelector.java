package sorryclient;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/*
 * NumPlayerSelector
 * Menu to select a number of players 2-3
 * */
public class NumPlayerSelector extends JPanel {
	private static final long serialVersionUID = -4510696620583889943L;
	
	//The options for number of player selection
	private int selection = -1;
	private final int numOptions = 3;
	private final JRadioButton[] optionButtons;
	private final ButtonGroup buttonGroup;
	
	private JButton confirmButton;
	
	private final String selectNumPlayerString = "Select the number of players";
	
	//The spacing of the border
	private static final Insets spacing = new Insets(20,20,20,20);
	
	public int getNumberOfPlayers() {
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
	public NumPlayerSelector(ActionListener confirmAction){
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
		JLabel selectPlayerLabel = new JLabel(selectNumPlayerString);
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
			f = f.deriveFont(24f);
			selectPlayerLabel.setFont(f);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selectPlayerLabel.setVerticalAlignment(JLabel.BOTTOM);
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createEmptyBorder(75, 0, 0, 0));
		topPanel.setOpaque(false);
		topPanel.add(selectPlayerLabel);
		
		//The center panel to hold the button panel
		JPanel centerPanel = new JPanel();
		centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		//The button panel to hold the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		buttonGroup = new ButtonGroup();
		optionButtons = new JRadioButton[numOptions];
		for(int i = 0; i < numOptions; ++i) {
			JPanel numPanel = new JPanel();
			optionButtons[i] = new JRadioButton(""+(i+2));
			optionButtons[i].setIcon(new ImageIcon("src/images/checkboxes/grey_circle.png"));
			optionButtons[i].setSelectedIcon(new ImageIcon("src/images/checkboxes/grey_boxTick.png"));
			optionButtons[i].setOpaque(false);
			try {
				Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
				f = f.deriveFont(26f);
				optionButtons[i].setFont(f);
			} catch (FontFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final int buttonSelection = i+2;
			optionButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					selection = buttonSelection;
					confirmButton.setEnabled(true);
				}
			});
			buttonGroup.add(optionButtons[i]);
			numPanel.add(optionButtons[i]);
			
			buttonPanel.add(Box.createGlue());
			buttonPanel.add(numPanel);
			buttonPanel.add(Box.createGlue());
		}
		
		centerPanel.add(Box.createGlue());
		centerPanel.add(buttonPanel);
		centerPanel.add(Box.createGlue());
		
		//The bottom panel to hold the confirm button
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
