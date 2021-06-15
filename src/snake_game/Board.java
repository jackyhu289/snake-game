package snake_game;

// Board display
import java.awt.Color;
import java.awt.Graphics;

import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;

// For keyboard events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class Board extends JPanel implements ActionListener {
	
	// Constant variables
	private int board[][];
	public final int BOARD_WIDTH = 600;
	public final int BOARD_HEIGHT = 600;
	
	// Grid dimensions
	public final int GRID_LENGTH = 15;
	public final int GRID_WIDTH = 15;
	
	// Images
	private Image grid;
	private Image apple;
	
	// Store the snakes position
	private int posX;
	private int posY;
	
	public Board() {
		this.board = new int[GRID_LENGTH][GRID_WIDTH];
		// Load images
		this.loadImages();
		
		// Initialize the board
		this.initBoard();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(this.grid, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);
	}
	
	private void loadImages() {
		this.grid = new ImageIcon("img/grid.png").getImage();
		this.apple = new ImageIcon("img/apple.png").getImage();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	private class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			System.out.println("KEY PRESSED");
		}
		
	}
	
	// Sets all of the board details for display
	private void initBoard() {
		addKeyListener(new TAdapter());
	}
}
