package snake_game;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import java.lang.Math;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReduceLavaTiles extends PowerUp {
	ReduceLavaTiles() {
		this.icon = new ImageIcon("img/ice-cube.png");
	}
	public void modify(Board board, Snake snake) {
		// Reduce the total amount of lava tiles by a random amount for 10-15 seconds
		int duration = (int)Math.floor((Math.random() * 6) + 10);
		
		boolean[][] lavaTiles = board.getLavaTiles();
		
		int lavaTilesRemovedCount = 0;
		
		for (int col = 0; col < board.getColumnCount(); ++col) {
			for (int row = 0; row < board.getRowCount(); ++row) {
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
		Timer timer = new Timer(12000, taskPerformer);
		timer.setRepeats(false);
		timer.start();
	}
}
