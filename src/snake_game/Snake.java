package snake_game;

import java.util.LinkedList;

import java.awt.Color;

// We will represent the snake using a linked list
public class Snake {
	// The first element will be the tail, and the last element will be the head
	private LinkedList<SnakeBodyPart> snakeBody;
	private Color colour;
	private int speed;
	
	private Direction direction;
	
	// Flags representing the snake's direction
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	// This constructor sets the default variable values
	public Snake() {
		this.snakeBody = new LinkedList<>();
		this.colour = Color.YELLOW;
		this.speed = 0;
		
		// TESTING, MAKE SURE TO REMOVE LATER!!!!!
		this.snakeBody.addLast(new SnakeBodyPart(0, 0));
		this.snakeBody.addLast(new SnakeBodyPart(0, 1));
		this.snakeBody.addLast(new SnakeBodyPart(0, 2));
		this.snakeBody.addLast(new SnakeBodyPart(0, 3));
		this.snakeBody.addLast(new SnakeBodyPart(0, 4));
		this.snakeBody.addLast(new SnakeBodyPart(0, 5));
		
		// DEFAULT
		this.direction = Direction.DOWN;
	}
	
	public void move() {
		// Get the position of the snake head
		int headCol = this.snakeBody.getLast().getCol();
		int headRow = this.snakeBody.getLast().getRow();
		
		this.snakeBody.removeFirst();

		switch(this.direction)
		{
			case UP:
				this.snakeBody.addLast(new SnakeBodyPart(headCol - 1, headRow));
				break;
			case DOWN:
				this.snakeBody.addLast(new SnakeBodyPart(headCol + 1, headRow));
				break;
			case LEFT:
				this.snakeBody.addLast(new SnakeBodyPart(headCol, headRow - 1));
				break;
			case RIGHT:
				this.snakeBody.addLast(new SnakeBodyPart(headCol, headRow + 1));
				break;
		}
	}
	
	// Setters
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setDirection(int keystrokeAscii) {
		// If player presses w or the "up" arrow key, set direction to up
		if (keystrokeAscii == 87 || keystrokeAscii == 38) this.direction = Direction.UP;
		
		// If player presses d or the "right" arrow key, set direction to right
		else if (keystrokeAscii == 68 || keystrokeAscii == 39) this.direction = Direction.RIGHT;
		
		// If player presses s or the "down" arrow key, set direction to down
		else if (keystrokeAscii == 83 || keystrokeAscii == 40) this.direction = Direction.DOWN;
		
		// If player presses a or the "left" arrow key, set direction to left
		else if (keystrokeAscii == 65 || keystrokeAscii == 37) this.direction = Direction.LEFT;
	}
	
	// Getters
	public LinkedList<SnakeBodyPart> getSnakeBody() { return this.snakeBody; }
	public int getSpeed() { return this.speed; }
	public Color getColour() { return this.colour; }
	public int getHeadCol() { return this.snakeBody.getLast().getCol(); }
	public int getHeadRow() { return this.snakeBody.getLast().getRow(); }
	
	public int length() { return this.snakeBody.size(); }
}
