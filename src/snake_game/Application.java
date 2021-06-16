package snake_game;

import javax.swing.JFrame;
import java.awt.EventQueue;

// For keyboard events
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

// Timer events
import java.awt.event.ActionListener;
import javax.swing.Timer;

// The Main class will use the functions of JFrame
public class Application extends JFrame implements KeyListener, ActionListener {
	
	private final String GAME_TITLE = "Snake game";
	private Board board;
	
	public Application() {
		// Initialize and display the user interface
		this.board = new Board();
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
		
		this.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent keystroke) {
		System.out.println(keystroke.getKeyCode());
		int ascii = keystroke.getKeyCode();
		
		// Update the snake direction passing the keystroke ASCII
		this.board.changeSnakeDirection(ascii);
	}
	
	/*
	 * Note - The functions keyReleased and keyTyped must be declared otherwise we get an error.
	 * These functions will be empty.
	*/
	public void keyReleased(KeyEvent e) {
		// Do nothing here
	}
	public void keyTyped(KeyEvent e) {
		// Do nothing here
	}
	
	private void moveSnake() {
		Timer timer = new Timer(1000, this);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Application ex = new Application();
			ex.setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
