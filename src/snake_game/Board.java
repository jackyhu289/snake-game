package snake_game;

// Board display
import java.awt.Color;
import java.awt.Graphics;

import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JPanel {
	
	// Constant variables
	public final int BOARD_WIDTH = 600;
	public final int BOARD_HEIGHT = 600;
	
	// Grid dimensions
	public final int GRID_LENGTH = 15;
	public final int GRID_WIDTH = 15;
	
	// Images
	private Image grid;
	private Image apple;
	
	public Board() {
		// Load images
		this.loadImages();
		
		// Initialize the board
		initBoard();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(this.grid, 0, 0, 600, 600, null);
		g.drawImage(this.apple, 0, 0, 600, 600, null);
	}
	
	private void loadImages() {	
		this.grid = new ImageIcon("img/test.png").getImage();
		this.apple = new ImageIcon("img/apple.png").getImage();
	}
	
	// Sets all of the board details for display
	private void initBoard() {
		
	}
}
