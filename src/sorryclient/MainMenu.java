package sorryclient;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * MainMenu
 * Menu to hold the start button
 * */
public class MainMenu extends JPanel{
	private static final long serialVersionUID = 3609831945869059312L;
	
	private final JButton start;
	
	private ImageIcon title, button;
	
	private final String menuTitleString = "Sorry!";
	
	public MainMenu(ActionListener startAction) {
		ImageIcon img = new ImageIcon("src/images/buttons/grey_button00.png");
		start = new JButton("Start",img);
		start.setHorizontalTextPosition(JButton.CENTER);
		start.addActionListener(startAction);
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
			f = f.deriveFont(16f);
			start.setFont(f);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start.setContentAreaFilled(false);
		start.setBorderPainted(false);
		
		title = new ImageIcon("src/fonts/sorry.png");
		JLabel titleLabel = new JLabel(title);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridy = 1;
		add(titleLabel,gbc);
		gbc.gridy = 2;
		add(start,gbc);
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
	
}
