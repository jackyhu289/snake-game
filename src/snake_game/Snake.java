package snake_game;

public class Snake {
	private int speed;
	private int length;
	
	// This constructor sets the default variable values
	Snake() {
		this.speed = 0;
		this.length = 3;
	}
	
	// Setters
	public void changeSpeed(int changeAmount) {
		this.speed += changeAmount;
	}
	public void increaseLength(int increaseAmount) {
		this.length += increaseAmount;
	}
}
