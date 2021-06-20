import javax.swing.ImageIcon;

/*
 * This is an abstract class for all possible powerups
*/
public abstract class PowerUp {
	// Each powerup will have an image icon
	protected ImageIcon icon;
	
	// Each powerup will make unique changes to the gameplay
	public abstract void modify(Board board, Snake snake);
}
