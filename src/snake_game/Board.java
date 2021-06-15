package snake_game;

// Board display
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Color;

import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JPanel;

// maybe use JLabel to display images

public class Board extends JPanel {
	
	// Constant variables
	private JPanel[][] grid;
	public final int BOARD_WIDTH = 600;
	public final int BOARD_HEIGHT = 600;
	
	// Grid dimensions
	public final int ROW_COUNT = 15;
	public final int COLUMN_COUNT = 15;
	
	// Images
	private Image appleImage;
	
	public Board() {
		this.grid = new JPanel[ROW_COUNT][COLUMN_COUNT];
		// Load images
		this.loadImages();
		
		// Initialize the board
		this.initBoard();
	}
	
	// This function will allow for all of the drawing and display
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	// This function loads all of the image icons
	private void loadImages() {
		//this.grid = new ImageIcon("img/grid.png").getImage();
		//this.apple = new ImageIcon("img/apple.png").getImage();
	}
	
	private void displayBoard() {
		this.setLayout(new GridLayout(COLUMN_COUNT, ROW_COUNT));
		
		for (int c = 0; c < ROW_COUNT; ++c) {
			for (int r = 0; r < COLUMN_COUNT; ++r) {
				this.grid[r][c] = new JPanel();
				
				this.grid[r][c].setBackground(Color.YELLOW);
				this.add(this.grid[r][c]);
			}
		}
	}
	
	private void displaySnake(Snake snake) {
		
	}
	
	// Sets all of the board details for display
	private void initBoard() {
	}
}
