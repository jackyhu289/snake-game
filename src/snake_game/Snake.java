package snake_game;

import java.util.LinkedList;

public class Snake {
	private int speed;
	private int length;
	
	// Store the snakes position
	private int posX;
	private int posY;
	
	// This constructor sets the default variable values
	Snake() {
		this.speed = 0;
		this.length = 3;
		
		this.posX = 5;
		this.posY = 5;
	}
	
	// Setters
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	// Getters
	public int getSpeed() { return this.speed; }
	public int getLength() { return this.length; }
	public int getPosX() { return this.posX; }
	public int getPosY() { return this.posY; }
}
