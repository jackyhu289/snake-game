package snake_game;

import java.util.LinkedList;

// Board display
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Color;

import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
	
	private Snake snake;
	
	public Board() {
		this.grid = new JPanel[ROW_COUNT][COLUMN_COUNT];
		
		this.snake = new Snake();
		
		// Load images
		this.loadImages();
		
		// Initialize the board
		this.initBoard();
		
		this.displayBoard();
		
		// Display the snake
		this.displaySnake();
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
	
	// This function displays the grid for the snake
	private void displayBoard() {
		this.setLayout(new GridLayout(COLUMN_COUNT, ROW_COUNT));
		
		for (int c = 0; c < ROW_COUNT; ++c) {
			for (int r = 0; r < COLUMN_COUNT; ++r) {
				this.grid[r][c] = new JPanel();
				
				//ImageIcon image = new ImageIcon("/home/jacky/eclipse-workspace/snake_game/img/lava-tile2.png");
				JLabel label = new JLabel();
				//label.setIcon(image);
				
				this.grid[r][c].setBackground(Color.GREEN);
				this.grid[r][c].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				this.grid[r][c].add(label);

				this.add(this.grid[r][c]);
			}
		}
	}
	
	private void displaySnake() {
		for (int c = 0; c < COLUMN_COUNT; ++c) {
			for (int r = 0; r < ROW_COUNT; ++r) {
				this.grid[c][r].setBackground(Color.GREEN);
			}
		}
		
		LinkedList<SnakeBodyPart> snakeBody = this.snake.getSnakeBody();
		
		this.grid[5][2].setBackground(Color.RED);
		
		for (SnakeBodyPart currentBodyPart : snakeBody) {
			int row = currentBodyPart.getRow();
			int col = currentBodyPart.getCol();
			
			this.grid[row][col].setBackground(this.snake.getColour());;
		}
	}
	
	// Sets all of the board details for display
	private void initBoard() {
	}
	
	
	public void changeSnakeDirection(int keystrokeAscii) {
		// Call the snake method, only so we can access the direction flags
		this.snake.setDirection(keystrokeAscii);
		
		// Move the snake
		this.displaySnake();
	}
}
