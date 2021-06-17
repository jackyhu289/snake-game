package snake_game;

import java.util.Collections;
import java.util.LinkedList;

// User input
import java.util.Scanner;

// Board display
import java.awt.GridLayout;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Random;
import java.lang.Math;

// Used for storing high scores
import java.util.SortedSet;
import java.util.TreeSet;

public class Board extends JPanel {
	
	/* Constant variables */
	
	// 2-Dimensional storage
	private JPanel[][] grid;
	private boolean[][] lavaTiles;
	
	// Declare the possible powerups
	private PowerUp powerUps[];
	private int powerUpCol;
	private int powerUpRow;
	
	// The player will have a high score
	private SortedSet<Integer> highScores = new TreeSet<Integer>(Collections.reverseOrder());
	
	boolean inGame = true;
	
	// Store the location of the randomly generated apple
	private int apple_col;
	private int apple_row;
	
	// Count the number of apples the player collected
	private int applesCollected = 0;
	
	// Count the total lava tiles on the board (for use later)
	private int totalLavaTiles = 0;
	
	// Grid dimensions (in pixels)
	public final int BOARD_WIDTH = 600;
	public final int BOARD_HEIGHT = 600;
	
	// Grid dimensions
	public final int ROW_COUNT = 15;
	public final int COLUMN_COUNT = 15;
	
	// Image(s)
	private ImageIcon appleImage = new ImageIcon("img/apple.png");
	
	// The snake will be used for display on the board
	private Snake snake;
	
	// The default colour of the grid will be green
	private Color gridColour = Color.GREEN;
	
	// The labels will be used to store images, and for removal later
	private JLabel currentAppleLabel;
	private JLabel currentPowerUpLabel;
	
	// Take in user input
	private Scanner input;
	
	// Random number generation
	private Random rand;
	
	// Time management
	private Timer appleTimeLimit;
	public int timeLimitConstant = 500;
	
	// Store the random powerup
	private PowerUp powerUp;
	
	public Board() {
		this.grid = new JPanel[COLUMN_COUNT][ROW_COUNT];
		this.lavaTiles = new boolean[COLUMN_COUNT][ROW_COUNT];
				
		this.snake = new Snake();
		this.rand = new Random();
		
		// Declare the possible powerups
		this.powerUps = new PowerUp[3];
		powerUps[0] = new TimeDelay();
		powerUps[1] = new ReduceLavaTiles();
		
		// The player will use the console to make decisions
		input = new Scanner(System.in);
		
		// Display the board
		this.displayBoard();
		
		// Display the snake
		this.displaySnake();
		
		// Generate the lava tiles
		this.generateLavaTiles(50);
		
		// Generate the apple (It will also regenerate after the snake reaches it)
		this.placeApple(0, 5);
		
		// Place the random powerup
		this.placePowerup();
	}
	
	// This function displays the grid for the snake
	private void displayBoard() {
		this.setLayout(new GridLayout(COLUMN_COUNT, ROW_COUNT));
		
		for (int c = 0; c < ROW_COUNT; ++c) {
			for (int r = 0; r < COLUMN_COUNT; ++r) {
				this.grid[r][c] = new JPanel();
				
				JLabel label = new JLabel();
				
				// Set the default colour of each tile
				this.grid[r][c].setBackground(this.gridColour);
				
				// There will be borders to denote column and row numbers
				this.grid[r][c].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				// Each tile will contain a JLabel (to allow input)
				this.grid[r][c].add(label);

				// Add the grid to the JPanel for display
				this.add(this.grid[r][c]);
			}
		}
	}
	
	// Displays the snake onto the grid
	private void displaySnake() {
		/*
		 * The snake body contains all of the information about the snake
		 * (position, colour, length, etc)
		 */
		LinkedList<SnakeBodyPart> snakeBody = this.snake.getSnakeBody();
			
		// Iterate through each body part, and display each of them on the grid
		for (SnakeBodyPart currentBodyPart : snakeBody) {
			
			// The body part will be placed based on its column and row data
			int col = currentBodyPart.getCol();
			int row = currentBodyPart.getRow();
			
			// The snake will be denoted by its default colour
			this.grid[col][row].setBackground(this.snake.getColour());;
		}
	}
	
	/*
	 * This function does many tasks. It moves the snake, but is also 
	 * responsble for checking for collision, obtained powerups, obtained apples, etc
	 */
	public void moveSnake(int keystrokeAscii) {
		// First, we want to remove the colour of the snake's end
		// Get the column and row of the snake's last
		int endCol = this.snake.getSnakeBody().getFirst().getCol();
		int endRow = this.snake.getSnakeBody().getFirst().getRow();
				
		// Revert the colour back to the default (since the end is now different)
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
		// If the player loses, declareLoss() will be called
		
		// Verify if the snake crashes into the border (walls)
		if (headCol < 0 || headCol >= this.COLUMN_COUNT || headRow < 0 || headRow >= this.ROW_COUNT) {
			this.declareLoss();
			return;
		}
		// Verify if the snake reached a lava tile
		if (this.lavaTiles[headCol][headRow]) {
			this.declareLoss();
			return;
		}
		
		// Denote the new snake head position by setting the snake colour
		this.grid[headCol][headRow].setBackground(snake.getColour());
		
		// Check if the tile the snake went on has an apple
		if (headCol == this.apple_col && headRow == this.apple_row) {
			// Remove the apple image to show the player that the apple was caught
			this.currentAppleLabel.setIcon(null);
			
			// Increment the total number of apples the player caught (for display later)
			++this.applesCollected;
			
			// Stop the apple time limit (since the player got the apple)
			this.appleTimeLimit.stop();
			
			// Generate a new apple
			this.placeApple(headCol, headRow);
			
			return; // exit the function
		}
		
		// Check if the snake collected a powerup
		if (this.powerUpCol == headCol && this.powerUpRow == headRow) {
			// Each powerup class has its own modify function (since unique powerups)
			this.powerUp.modify(this, this.snake);
			
			// Remove the powerup image now that the player already collected it
			this.currentPowerUpLabel.setIcon(null);
			
			// Remove the power up location
			this.powerUpCol = -1;
			this.powerUpRow = -1;
			
			// Generate a new powerup in 30 seconds
			ActionListener taskPerformer = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					placePowerup();
				}
			};
			Timer timer = new Timer(30000, taskPerformer);
			
			// Generate the powerup only once (no interval)
			timer.setRepeats(false);
			
			timer.start(); // start the timer
			
			return;
		}
	}
	
	// This function places an apple in a random tile
	private void placeApple(int headCol, int headRow) {
		// Generate a random column and random row to place the apple in
		int randomCol;
		int randomRow;
		do {
			// Select a random tile to place the apple in
			// Choose a random number between 0 and (row count) - 1, due to 0 based indexing
			randomCol = this.rand.nextInt(COLUMN_COUNT);
			
			// Choose a random number 0 and (column count) - 1, due to 0 based indexing
			randomRow = this.rand.nextInt(ROW_COUNT);
			
		} while (this.lavaTiles[randomCol][randomRow]); // verify the apple is not on a lava tile
		
		// Set the apple column and apple row so we can access it from other functions
		this.apple_col = randomCol;
		this.apple_row = randomRow;
		
		/* 
		 * We will calculate how much time the player has to get the generated apple,
		 * depending on the location of the snake head and the location of the newly
		 * generated apple
		*/
		// Distance formula
		double distance = Math.sqrt(Math.pow(this.apple_col - headCol, 2) + Math.pow(this.apple_row - headRow, 2));
		
		// The time limit equation formula
		int timeLimit = (int)(distance*this.timeLimitConstant);
		
		// The player has a time limit to collect the apple, depending on the distance
		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				declareLoss(); // declare loss if time limit is reached
				return;
			}
		};
		Timer timer = new Timer(timeLimit, taskPerformer);
		
		timer.setRepeats(false); // The timer will only execute once
		
		// We want to access the apple time limit from other functions
		this.appleTimeLimit = timer;
		timer.start(); // start the timer

		// Create a new JLabel, which will contain the image
		JLabel image = new JLabel();
		image.setIcon(this.appleImage);
		
		// We want to access the apple label from other functions (to remove it once apple caught)
		this.currentAppleLabel = image;
		
		// Add the JLabel to the tile
		this.grid[this.apple_col][this.apple_row].add(image);
	}
	// This function places a random powerup in a random tile
	private void placePowerup() {
		// Generate a random column and row to place our powerup in
		int randomCol;
		int randomRow;
		do {
			// Select a random tile to place the apple in
			// Choose a random number between 0 and (row count) - 1, due to 0 based indexing
			randomCol = this.rand.nextInt(COLUMN_COUNT);
			
			// Choose a random number 0 and (column count) - 1, due to 0 based indexing
			randomRow = this.rand.nextInt(ROW_COUNT);
			
		} while (this.lavaTiles[randomCol][randomRow]); // verify the powerup is not on a lava tile
		
		// Choose a random powerup
		int index = this.rand.nextInt(2);
		
		this.powerUp = this.powerUps[index];
		
		// Create a new JLabel, which will contain the image
		JLabel image = new JLabel();
		image.setIcon(powerUp.icon);
		
		// We want to access the power up label from other functions (to remove it later)
		this.currentPowerUpLabel = image;
		
		// Store the column and row of the powerup (to access later)
		this.powerUpCol = randomCol;
		this.powerUpRow = randomRow;
		
		// Add the image to the grid tile
		this.grid[randomCol][randomRow].add(image);
	}
	
	// This function randomly generates lava tiles on places the snake will have to avoid
	public void generateLavaTiles(int amount) {
		for (int i = 0; i < amount; ++i) {
			
			// Select a random row and column
			int randomRow = this.rand.nextInt(ROW_COUNT);
			int randomCol = this.rand.nextInt(COLUMN_COUNT);
			
			// Tile will be red to denote lava
			this.grid[randomCol][randomRow].setBackground(Color.RED);
			
			/*
			 * Store the tile location in the tile array.
			 * Used for checking if the snake reached a lava tile
			*/
			this.lavaTiles[randomCol][randomRow] = true;
			
			// Increment total lava tiles (see usage in ReduceLavaTiles class)
			++this.totalLavaTiles;
		}
	}
	// This function removes a single lava tile given the column and row
	public void removeLavaTile(int col, int row) {
		// Set to false to denote non lava tile
		this.lavaTiles[col][row] = false;
		
		// Set grid tile colour to default again (to signify no lava)
		this.grid[col][row].setBackground(this.gridColour);
	}
	
	/*
	 * This function tells the player that he/she lost using the console, and has options
	 * for the user
	*/
	private void declareLoss() {
		// Set inGame variable to false to denote end of game
		this.inGame = false;
		
		// Store the user option
		int option;
		
		System.out.println("You lost the game! You have collected " + this.applesCollected + " apples");
		
		while (true) {
			// Prompt user for a selection
			System.out.println("Please select an option.");
			System.out.println("1 - View high scores");
			System.out.println("2 - Exit");
			
			option = input.nextInt();
			
			// Append to high scores list
			this.highScores.add(this.applesCollected);
			int ranking = -1;
			
			switch(option)
			{
			case 1:
				// Print out a list of all the high scores
				int i = 1;
				
				/*
				 * The highscores are already sorted, since SortedSet automatically sorts,
				 * so we don't need to manually sort
				 */
				for (int highScore : this.highScores) {
					System.out.println(i + ": " + highScore);
					
					// Set the ranking of the user as well
					if (this.applesCollected == highScore) ranking = i;
					
					if (i == 5) break;
					++i;
				}
				
				// Ranking will not be in the top 5 if it is equal to -1
				if (ranking == -1) {
					System.out.println("Your score is not in the top 5.");
				} else {
					// Print out the user's ranking (only if in top 5)
					System.out.println("Your ranking is " + ranking);
				}
				System.out.println();
				break;
			case 2:
				// End the game and close the window
				System.out.println("Close the window to end the game.");
				break;
			}
		}
	}
	
	// Getters
	public int getRowCount() { return this.ROW_COUNT; }
	public int getColumnCount() { return this.COLUMN_COUNT; }
	public JPanel[][] getGrid() { return this.grid; }
	public boolean[][] getLavaTiles() { return this.lavaTiles; }
	public int getTotalLavaTiles() { return this.totalLavaTiles; }
}
