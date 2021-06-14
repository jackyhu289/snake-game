package snake_game;

import snake_game.Board;

import javax.swing.JFrame;
import java.awt.EventQueue;

// The Main class will use the functions of JFrame
public class Main extends JFrame {
	// Global constants
	final String GAME_TITLE = "Snake game";
	
	public Main() {
		// Initialize and display the user interface
		Board board = new Board();
		this.add(board);
		
		// Set the dimensions of the board display
		this.setSize(board.BOARD_WIDTH, board.BOARD_HEIGHT);
		
		// Do not allow the player to resize the window
		this.setResizable(false);
		
		this.setTitle(GAME_TITLE);
		
		// The game will exit once user closes
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// If null is passed, window will appear in center of screen
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Main ex = new Main();
			ex.setVisible(true);
		});
	}
}
