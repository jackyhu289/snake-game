package snake_game;

import javax.swing.ImageIcon;

public class LavaTiles extends Modifier {
	
	LavaTiles() {
		this.icon = new ImageIcon("img/lava-tile.png").getImage();
	}
	
	public void modify(Snake snake, Board board) {
		
	}
}
