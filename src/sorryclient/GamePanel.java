package sorryclient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import game.GameHelpers;
import game.GameManager;
import game.Tile;

/*
 * GamePanel
 * Panel to hold the graphical game
 * */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1593344194657112518L;
	
	//A grid to hold all the tiles in the game
	private final static int boardSize = 16;
	private final TilePanel[][] tileGrid;
	
	//The card button for the game
	private final JButton cardButton;
	private final JLabel cardLabel = new JLabel("");
	
	//The game manager that runs the actual logic
	private final GameManager mGameManager;
	
	//The way to exit the game menu
	private final ActionListener mQuitAction;
	
	private final ImageIcon logo;
	
	{
		//Create and set-up the card button
		cardButton = new JButton("",new ImageIcon("src/images/cards/cardBack_red.png"));
		cardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mGameManager.drawCard();
				redraw();
			}
		});
		try {
			cardLabel.setText("Cards:");
			Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
			f = f.deriveFont(8f);
			cardLabel.setFont(f);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GamePanel(ActionListener inQuitAction, GameManager inGameManager){
		mQuitAction = inQuitAction;
		mGameManager = inGameManager;
		
		//Create the GUI to be a grid for all the tiles
		setLayout(new GridLayout(boardSize,boardSize));
		tileGrid = new TilePanel[boardSize][boardSize];
		
		//Create each grid square
		//Either give it a Start/Home label panel, or a TilePanel
		for(int y = 0; y < boardSize; ++y) {
			for(int x = 0; x < boardSize; ++x) {
				if(x == 4 && y == 2) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.YELLOW));}
				else if (x == 2 && y == 7) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.YELLOW));}
				else if(x == 13 && y == 4) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.GREEN));}
				else if(x == 8 && y == 2) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.GREEN));}
				else if(x == 11 && y == 13) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.RED));}
				else if(x == 13 && y == 8) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.RED));}
				else if(x == 2 && y == 11) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.BLUE));}
				else if(x == 7 && y == 13) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.BLUE));}
				else {tileGrid[x][y] = new TilePanel(mGameManager.getTile(x,y));}
				
				add(tileGrid[x][y]);
			}
		}
		
		logo = new ImageIcon("src/fonts/sorry.png");
		
		//Set in the card
		TilePanel cardLabelTile = tileGrid[boardSize/2-1][boardSize/2-1];
		cardLabelTile.setLayout(new GridLayout(1,1));
		cardLabelTile.add(cardLabel);
		
		TilePanel cardButtonTile = tileGrid[boardSize/2][boardSize/2-1];
		cardButtonTile.setLayout(new GridLayout(1,1));
		cardButtonTile.add(cardButton);
		
		//This is used to make sure the GameManager can redraw the GUI
		inGameManager.setGamePanel(this);
		
		redraw();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(logo.getImage(), getWidth()/3 + getWidth()/15, getHeight()/5, getWidth()/4, getHeight()/5, null);
	}
	public void redraw() {
		for(TilePanel row[] : tileGrid) {
			for(TilePanel tp : row) {
				tp.update();
			}
		}
		revalidate();
		repaint();
	}

	//Each tile is a square in the grid, it can be null to hold a blank square
	class TilePanel extends JPanel {
		private static final long serialVersionUID = -9071191204545371340L;
		
		private final Tile mTile;
		private final Stack<Component> components;
		
		private JPanel pawn = new JPanel() {
			private static final long serialVersionUID = -9071191204545371340L;
			//Draw a pawn onto the board
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Color color = this.getBackground();
				ImageIcon[] pawns = {new ImageIcon("src/images/pawns/red_pawn.png"),
					new ImageIcon("src/images/pawns/blue_pawn.png"),
					new ImageIcon("src/images/pawns/green_pawn.png"),
					new ImageIcon("src/images/pawns/yellow_pawn.png")};
				if (color == Color.RED) {
					g.drawImage(pawns[0].getImage(), 0, 0, getWidth(), getHeight(), null);
				}
				else if (color == Color.BLUE) {
					g.drawImage(pawns[1].getImage(), 0, 0, getWidth(), getHeight(), null);					
				}
				else if (color == Color.GREEN) {
					g.drawImage(pawns[2].getImage(), 0, 0, getWidth(), getHeight(), null);
				}
				else if (color == Color.YELLOW) {
					g.drawImage(pawns[3].getImage(), 0, 0, getWidth(), getHeight(), null);
				}
			}
		};
		private boolean pawnDisplayed = false;
		
		Image i, j, k;
		String fileString = "";
		String backString = "";
		String slideString = "";
		
		TilePanel(Tile tile) {
			mTile = tile;
			setOpaque(false);
			//Used to keep track what component should be displayed
			components = new Stack<Component>();
			
			if (mTile != null) {
				if(mTile.isHome()) {
					try {
						JLabel j = new JLabel("Home");
						Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
						f = f.deriveFont(7f);
						j.setFont(f);
						components.push(j);
					} catch (FontFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(mTile.isStart()) {
					try {
						JLabel j = new JLabel("Start");
						Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("src/fonts/kenvector_future.ttf"));
						f = f.deriveFont(7f);
						j.setFont(f);
						components.push(j);
					} catch (FontFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			
			//If the tile is clicked by the user...
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent me) {
					//Update this in the action manager
					mGameManager.tileClicked(mTile,mGameManager.getMainPlayer());
				}
			});
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			//If we are a meaningful tile in the game
			if(mTile != null) {				
				if (mTile.getColor() == Color.RED) {
					fileString = "src/images/tiles/red_tile.png";
				}
				else if (mTile.getColor() == Color.BLUE) {
					fileString = "src/images/tiles/blue_tile.png";
				}
				else if (mTile.getColor() == Color.GREEN) {
					fileString = "src/images/tiles/green_tile.png";
				}
				else if (mTile.getColor() == Color.YELLOW) {
					fileString = "src/images/tiles/yellow_tile.png";
				}
				else {
					fileString = "src/images/tiles/grey_tile.png";
				}
				
				//Set any special looks based on the tiles properties
				if(mTile.doesSlide()) {
					if (mTile.getColor() == Color.RED) {
						slideString = "src/images/sliders/red_slide.png";
						backString = "src/images/tiles/red_tile.png";
					}
					else if (mTile.getColor() == Color.BLUE) {
						slideString = "src/images/sliders/blue_slide.png";
						backString = "src/images/tiles/blue_tile.png";
					}
					else if (mTile.getColor() == Color.GREEN) {
						slideString = "src/images/sliders/green_slide.png";
						backString = "src/images/tiles/green_tile.png";
					}
					else if (mTile.getColor() == Color.YELLOW) {
						slideString = "src/images/s)liders/yellow_slide.png";
						backString = "src/images/tiles/yellow_tile.png";
					}
				}
				if(mTile.isStart()) {
					if (mTile.getColor() == Color.RED) {
						backString = "src/images/panels/red_panel.png";
					}
					else if (mTile.getColor() == Color.BLUE) {
						backString = "src/images/panels/blue_panel.png";
					}
					else if (mTile.getColor() == Color.GREEN) {
						backString = "src/images/panels/green_panel.png";
					}
					else if (mTile.getColor() == Color.YELLOW) {
						backString = "src/images/panels/yellow_panel.png";
					}
				}
				if(mTile.isHome()) {
					if (mTile.getColor() == Color.RED) {
						backString = "src/images/panels/red_panel.png";
					}
					else if (mTile.getColor() == Color.BLUE) {
						backString = "src/images/panels/blue_panel.png";
					}
					else if (mTile.getColor() == Color.GREEN) {
						backString = "src/images/panels/green_panel.png";
					}
					else if (mTile.getColor() == Color.YELLOW) {
						backString = "src/images/panels/yellow_panel.png";
					}
				}
				try {
					i = ImageIO.read(new File(fileString));
					j = ImageIO.read(new File(backString));
					k = ImageIO.read(new File(slideString));
				} catch (IOException e) {
					e.getMessage();
				}
				g.drawImage(j, 0, 0, getWidth(), getHeight(), null);
				g.drawImage(i, 0, 0, getWidth(), getHeight(), null);
				g.drawImage(k, 9, 5, getWidth()/2, getHeight()/2, null);
			}
		}
		
		//Update the tile based on its properties
		protected void update() {
			if(mTile == null) return;
			if(mTile.isOccupied() && !pawnDisplayed) {
				pawnDisplayed = true;
				components.push(pawn);
			}
			if(mTile.isOccupied() && pawnDisplayed) {
				pawn.setBackground(mTile.getPawnColor());
			}
			if(!mTile.isOccupied() && pawnDisplayed) {
				pawnDisplayed = false;
				components.pop();
			}
			removeAll();
			if(!components.isEmpty())add(components.peek());
		}
	}
	
	//Used for the start counter display
	class StartLabelPanel extends TilePanel{
		private static final long serialVersionUID = -9166979703140637366L;
		private final int mPlayerNum;
		JLabel mLabel;
		{
			mLabel = new JLabel();
			add(mLabel);
		}
		StartLabelPanel(int numPlayer) {
			super(null);
			mPlayerNum = numPlayer;
		}
		@Override
		protected void update() {
			mLabel.setText(mGameManager.getPlayerStartCount(mPlayerNum));
		}
	}
	
	//Used for the home counter display
	class HomeLabelPanel extends TilePanel{
		private static final long serialVersionUID = -9166979703540637366L;
		
		private final int mPlayerNum;
				
		JLabel mLabel;
		{
			mLabel = new JLabel();
			add(mLabel);
		}
		HomeLabelPanel(int numPlayer) {
			super(null);
			mPlayerNum = numPlayer;
		}
		@Override
		protected void update() {
			mLabel.setText(mGameManager.getPlayerHomeCount(mPlayerNum));
		}
	}
	
	public void endGame(String winnerName) {
		JOptionPane.showMessageDialog(
				null, 
				mGameManager.getWinner() + " player won!", 
				"Sorry!", 
				JOptionPane.NO_OPTION
			);
		//Quit out if over
		JButton exit = new JButton("");
		exit.addActionListener(mQuitAction);
		exit.doClick();
	}
}
