package snake_game;

// We will store the snake body positions using a linked list
import java.util.LinkedList;

import java.awt.Color;

// We will represent the snake using a linked list
public class Snake {
	// The first element will be the end, and the last element will be the head
	private LinkedList<SnakeBodyPart> snakeBody;
	private Color colour;
	private Direction direction;
	
	// Flags representing the snake's direction
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	// This constructor sets the default variable values
	public Snake() {
		this.snakeBody = new LinkedList<>();
		this.colour = Color.YELLOW;
		
		// The snake will have a length of 6 and always begin here
		for (int row = 0; row < 6; ++row) {
			this.snakeBody.addLast(new SnakeBodyPart(0, row));
		}
		
		// The snake will begin with a default direction (downwards)
		this.direction = Direction.DOWN;
	}
	
	public void move() {
		// Get the position of the snake head
		int headCol = this.snakeBody.getLast().getCol();
		int headRow = this.snakeBody.getLast().getRow();
		
		// Remove the end of the snake
		this.snakeBody.removeFirst();

		switch(this.direction)
		{
			case UP:
				this.snakeBody.addLast(new SnakeBodyPart(headCol, headRow - 1));
				break;
			case DOWN:
				this.snakeBody.addLast(new SnakeBodyPart(headCol, headRow + 1));
				break;
			case LEFT:
				this.snakeBody.addLast(new SnakeBodyPart(headCol - 1, headRow));
				break;
			case RIGHT:
				this.snakeBody.addLast(new SnakeBodyPart(headCol + 1, headRow));
				break;
		}
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
	public Color getColour() { return this.colour; }
	public int getHeadCol() { return this.snakeBody.getLast().getCol(); }
	public int getHeadRow() { return this.snakeBody.getLast().getRow(); }
	
	public int length() { return this.snakeBody.size(); }
}
