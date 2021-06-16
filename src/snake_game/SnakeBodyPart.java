package snake_game;

// This class will store each component of the snake, having a pointer to the next body part
public class SnakeBodyPart {
	public SnakeBodyPart next;
	
	// Coordinates of the snake body part
	private int row;
	private int col;
	
	public SnakeBodyPart(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	// Getters
	public int getCol() { return this.col; }
	public int getRow() { return this.row; }
}
