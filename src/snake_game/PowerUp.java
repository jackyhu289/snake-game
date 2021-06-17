package snake_game;

import javax.swing.ImageIcon;

public abstract class PowerUp {
	protected ImageIcon icon;
	
	public abstract void modify(Board board, Snake snake);
}
