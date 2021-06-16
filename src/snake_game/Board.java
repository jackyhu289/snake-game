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

import java.util.Random;

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
	
	// Random number generation
	private Random rand;
	
	public Board() {
		this.grid = new JPanel[COLUMN_COUNT][ROW_COUNT];
		
		this.snake = new Snake();
		this.rand = new Random();
		
		// Load images
		this.loadImages();
		
		// Initialize the board
		this.initBoard();
		
		this.displayBoard();
		
		// Display the snake
		this.displaySnake();
		
		// Generate the apple
		this.placeApple();
	}
	
	// This function will allow for all of the drawing and display
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	// This function loads all of the image icons
	private void loadImages() {
		this.appleImage = new ImageIcon("img/apple.png").getImage();
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
	
	// Displays the snake onto the grid
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
	
	public void moveSnake(int keystrokeAscii) {
		this.snake.setDirection(keystrokeAscii);
		this.snake.move();
		
		// Verify the snake boundaries
		int headCol = this.snake.getHeadCol();
		int headRow = this.snake.getHeadRow();
		
		if (headCol < 0 || headCol >= this.COLUMN_COUNT || headRow < 0 || headRow >= this.ROW_COUNT) {
			System.out.println("fail");
		} else {
			this.displaySnake();
		}
	}
	
	private void placeApple() {
		// Select a random cell to place the apple in
		// Choose a random number between 0 and (row count) - 1, due to 0 based indexing
		int randomRow = this.rand.nextInt(ROW_COUNT);
		
		// Choose a random number 0 and (column count) - 1, due to 0 based indexing
		int randomCol = this.rand.nextInt(COLUMN_COUNT);
		
		// Create a new JLabel, which will contain the image
		JLabel image = new JLabel();
		image.setIcon(new ImageIcon("img/apple.png"));
		
		// Add the JLabel to the cell
		this.grid[randomCol][randomRow].add(image);
	}
	private void removeApple() {
		
	}
}
