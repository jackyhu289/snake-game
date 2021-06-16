package snake_game;

import java.util.LinkedList;

// User input
import java.util.Scanner;

// Board display
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Color;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Random;
import java.lang.Math;

// maybe use JLabel to display images

public class Board extends JPanel {
	
	// Constant variables
	private JPanel[][] grid;
	private boolean[][] lavaTiles;
	boolean inGame = true;
	
	// Store the location of the randomly generated apple
	private int apple_col;
	private int apple_row;
	
	// Count the number of apples the player collected
	private int applesCollected = 0;
	
	// We will use this to verify the apple was collected before time ran out
	private boolean appleCollected;
	
	public final int BOARD_WIDTH = 600;
	public final int BOARD_HEIGHT = 600;
	
	// Grid dimensions
	public final int ROW_COUNT = 15;
	public final int COLUMN_COUNT = 15;
	
	// Images
	private ImageIcon appleImage;
	
	private Snake snake;
	
	private Color gridColour = Color.GREEN;
	
	private JLabel currentAppleLabel;
	
	// User input
	private Scanner input;
	
	// Random number generation
	private Random rand;
	
	public Board() {
		this.grid = new JPanel[COLUMN_COUNT][ROW_COUNT];
		this.lavaTiles = new boolean[COLUMN_COUNT][ROW_COUNT];
		
		this.snake = new Snake();
		this.rand = new Random();
		
		input = new Scanner(System.in);
		
		// Load images
		this.loadImages();
		
		// Display the board
		this.displayBoard();
		
		// Display the snake
		this.displaySnake();
		
		// Generate the apple
		this.placeApple(0, 5);
		
		// Generate the lava tiles
		this.generateLavaTiles(15);
	}
	
	// This function will allow for all of the drawing and display
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	// This function loads all of the image icons
	private void loadImages() {
		this.appleImage = new ImageIcon("img/apple.png");
	}
	
	// This function displays the grid for the snake
	private void displayBoard() {
		this.setLayout(new GridLayout(COLUMN_COUNT, ROW_COUNT));
		
		for (int c = 0; c < ROW_COUNT; ++c) {
			for (int r = 0; r < COLUMN_COUNT; ++r) {
				this.grid[r][c] = new JPanel();
				
				JLabel label = new JLabel();
				
				this.grid[r][c].setBackground(this.gridColour);
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
				this.grid[c][r].setBackground(this.gridColour);
			}
		}
		
		LinkedList<SnakeBodyPart> snakeBody = this.snake.getSnakeBody();
				
		for (SnakeBodyPart currentBodyPart : snakeBody) {
			int col = currentBodyPart.getCol();
			int row = currentBodyPart.getRow();
			
			this.grid[col][row].setBackground(this.snake.getColour());;
		}
	}
	
	public void moveSnake(int keystrokeAscii) {
		// First, we want to remove the colour of the snake's end
		// Get the column and row of the snake's last
		int endCol = this.snake.getSnakeBody().getFirst().getCol();
		int endRow = this.snake.getSnakeBody().getFirst().getRow();
				
		// Revert the colour back to the default
		this.grid[endCol][endRow].setBackground(this.gridColour);
		
		// Set the snake's direction depending on the keystroke, and move the snake
		this.snake.setDirection(keystrokeAscii);
		this.snake.move();
		
		/*
		 * Set the new tile containing the snake's head to the snake colour. 
		 * We also want to verify the snake boundaries by analyzing the snake's head's new position
		*/
		int headCol = this.snake.getHeadCol();
		int headRow = this.snake.getHeadRow();
		
		/* Verify that the player does not lose */
		
		// Verify if out of bounds
		if (headCol < 0 || headCol >= this.COLUMN_COUNT || headRow < 0 || headRow >= this.ROW_COUNT) {
			this.declareLoss();
			return;
		}
		// Verify if the snake reached a lava tile
		if (this.lavaTiles[headCol][headRow]) {
			this.declareLoss();
			return;
		}
		
		this.grid[headCol][headRow].setBackground(snake.getColour());
		
		// Check if the tile the snake went on has an apple
		if (headCol == this.apple_col && headRow == this.apple_row) {
			// Remove the apple image to show the player that the apple was caught
			this.currentAppleLabel.setIcon(null);
			
			// Increment the total number of apples the player caught
			++this.applesCollected;
			
			// Generate a new apple
			this.placeApple(headCol, headRow);
			
			this.appleCollected = true;
			
			return;
		}
		
		// Check if there was a special item the snake collected
		
		
		// Increase the snake length
		this.snake.increaseSnakeLength();
	}
	
	private void placeApple(int headCol, int headRow) {
		do {
			// Select a random tile to place the apple in
			// Choose a random number between 0 and (row count) - 1, due to 0 based indexing
			this.apple_col = this.rand.nextInt(COLUMN_COUNT);
			
			// Choose a random number 0 and (column count) - 1, due to 0 based indexing
			this.apple_row = this.rand.nextInt(ROW_COUNT);
			
		} while (this.lavaTiles[this.apple_col][this.apple_row]); // verify the apple is not on a lava tile
		
		// Distance formula
		double distance = Math.sqrt(Math.pow(this.apple_col - headCol, 2) + Math.pow(this.apple_row - headRow, 2));
		
		int timeLimit = (int)(distance*372.2);
		
		// The player has a time limit to collect the apple, depending on the distance
		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (appleCollected == false) declareLoss();
			}
		};
		Timer timer = new Timer(timeLimit, taskPerformer);
		timer.setRepeats(false);
		timer.start();
		this.appleCollected = false;

		// Create a new JLabel, which will contain the image
		JLabel image = new JLabel();
		image.setIcon(this.appleImage);
		
		this.currentAppleLabel = image;
		
		// Add the JLabel to the tile
		this.grid[this.apple_col][this.apple_row].add(image);
	}
	// This function randomly generates lava tiles on places the snake will have to avoid
	private void generateLavaTiles(int amount) {
		for (int i = 0; i < amount; ++i) {
			// Select a random row and column
			int randomRow = this.rand.nextInt(ROW_COUNT);
			int randomCol = this.rand.nextInt(COLUMN_COUNT);
			
			this.grid[randomCol][randomRow].setBackground(Color.RED);
			
			/*
			 * Store the tile location in the tile array.
			 * Used for checking if the sn3ake reached a lava tile
			*/
			this.lavaTiles[randomCol][randomRow] = true;
		}
	}
	
	/*
	 * This function tells the player that he/she lost using the console,
	 * and asks if the player wants to play again
	*/
	private void declareLoss() {
		this.inGame = false;
		int option;
		
		System.out.println("You lost the game! You have collected " + this.applesCollected + " apples");
		System.out.println("Do you want to play again? Enter y/n");
		option = input.nextInt();
		
		if (option == 'y' || option == 'Y') {
			this.inGame = true;
		} else {
			System.out.println("Game ended.");
		}
	}
	
	// This function restarts the game
	private void restartGame() {
		
	}
}
