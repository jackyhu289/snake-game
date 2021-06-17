package snake_game;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import java.lang.Math;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * This class reduces the total amount of lava tiles for a brief amount of time
*/
public class ReduceLavaTiles extends PowerUp {
	ReduceLavaTiles() {
		// Set the icon of the powerup
		this.icon = new ImageIcon("img/ice-cube.png");
	}
	
	public void modify(Board board, Snake snake) {
		// Lava tiles will be removed for 12 seconds
		int duration = 12;
		
		// Find out which tiles on the board is covered with lava
		boolean[][] lavaTiles = board.getLavaTiles();
		
		// Store the total lava tiles removed so we know how many to generate again after the duration
		int lavaTilesRemovedCount = 0;
		
		// Iterate through each of the board's tiles
		for (int col = 0; col < board.getColumnCount(); ++col) {
			for (int row = 0; row < board.getRowCount(); ++row) {
				// If the current tile is not covered with lava
				if (!lavaTiles[col][row]) continue;
				
				// There is 1/5 chance the lava tile will be removed
				double chance = Math.random();
				
				if (chance <= 0.2) {
					// Remove the lava tile
					board.removeLavaTile(col, row);
					++lavaTilesRemovedCount;
				}
			}
		}
		/*
		 * The local variable lavaTilesRemovedCount cannot be used inside an
		 * inner class, that's why we declare a new final variable
		*/
		final int totalLavaTilesRemoved = lavaTilesRemovedCount;
		
		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Regenerate the lava tiles on random spots
				board.generateLavaTiles(totalLavaTilesRemoved);
			}
		};
		// Set the timer with the duration
		Timer timer = new Timer(duration*1000, taskPerformer);
		
		// We want to execute the task only once
		timer.setRepeats(false);
		
		// Start the timer
		timer.start();
	}
}
