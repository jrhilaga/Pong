package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener, KeyListener {

	public static Pong pong;

	public int width = 700, height = 700; 

	public Renderer renderer; //call renderer

	public Paddle player1; //call player 1

	public Paddle player2; //call player 2

	public Ball ball; // call ball

	public boolean w, s, up, down; //key controls

	public int status = 0, matchPoint = 3, playerWon; // Status: 0 - Main Menu, 1 - Paused, 2 - Playing, 3 - Game Over

	public Random random;

	public JFrame jframe; //call jframe

	public Pong() throws IOException {
		
		Timer timer = new Timer(20, this);
		random = new Random();

		jframe = new JFrame("Pong");

		renderer = new Renderer();
		jframe.setSize(width + 15, height + 35); // set the dimensions of the jframe
		jframe.setVisible(true); // true = shows the jframe window
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if you use the Close operation it will EXIT
		jframe.add(renderer); // adds the renderer
		jframe.addKeyListener(this); // adds KeyListener for the controls

		timer.start(); // starts timer
	}

	public void start() { 
		
		status = 2; // sets the status to "Playing"
		player1 = new Paddle(this, 1); // calls Player1's Paddle
		player2 = new Paddle(this, 2); // calls Player2's Paddle
		ball = new Ball(this); // calls ball
	}

	public void update() {
		
		if (player1.score >= matchPoint) { // decides whether the game is over or not
			
			playerWon = 1;
			status = 3;
		}

		if (player2.score >= matchPoint) { // decides whether the game is over or not
			
			status = 3;
			playerWon = 2;
		}
		
		if (w) { // when variable w becomes true the player's paddle also moves
			
			player1.move(true);
		}
		if (s) { // when variable s becomes true the player's paddle also moves
			
			player1.move(false);
		}

		if (up) { // when variable up becomes true the player's paddle also moves
			player2.move(true);
		}
		if (down) { // when variable down becomes true the player's paddle also moves
			player2.move(false);
		}
		
		ball.update(player1, player2);
	}

	public void render(Graphics2D g) { // rendering is basically Drawing graphics
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (status == 0 ) { //Main Menu
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("PONG", width / 2 - 75, 50);

			g.setFont(new Font("Arial", 1, 30));

			g.drawString("Press Space to Play", width / 2 - 150, height / 2 - 25);
			
		}

		if (status == 1) { // Paused Window
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PAUSED", width / 2 - 103, height / 2 - 25);
		}

		if (status == 1 || status == 2) { // Playing Field 
			
			g.setFont(new Font("Arial", 1, 50));
			g.setColor(Color.RED);
			g.drawString(String.valueOf(player1.score), width / 2 - 90, 50);
			g.setColor(Color.BLUE);
			g.drawString(String.valueOf(player2.score), width / 2 + 65, 50);

			player1.render(g);
			player2.render(g);
			ball.render(g);
		}

		if (status == 3) { // Game over Screen
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("PONG", width / 2 - 75, 50);

			if (playerWon == 1){
				g.setColor(Color.RED);
				g.drawString("Player " + playerWon + " Wins!", width / 2 - 165, 200);
			}
			if (playerWon == 2){
				g.setColor(Color.BLUE);
				g.drawString("Player " + playerWon + " Wins!", width / 2 - 165, 200);
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 30));

			g.drawString("Press Space to Play Again", width / 2 - 185, height / 2 - 25);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		
		if (status == 2)
		{
			update();
		}

		renderer.repaint();
	}

	public static void main(String[] args) throws IOException {
		
		pong = new Pong();
	}

	@Override
	public void keyPressed(KeyEvent e) { 
		
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W) { // when the 'W' key is pressed, variable w becomes true 
			
			w = true;
		}
		else if (id == KeyEvent.VK_S) { // when the 'S' key is pressed, variable s becomes true
			
			s = true;
		}
		else if (id == KeyEvent.VK_UP) { // when the 'UP' key is pressed, variable up becomes true
			
			up = true;
		}
		else if (id == KeyEvent.VK_DOWN) { // when the 'DOWN' key is pressed, variable down becomes true
			
			down = true;
		}
		else if (id == KeyEvent.VK_SPACE) { // signals to either start or pause the game
			
			if (status == 0 || status == 3) {
				
				start();
			}
			else if (status == 1) {
				
				status = 2;
			}
			else if (status == 2) {
				
				status = 1;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W) { // when the 'W' key is released, variable w becomes false
			
			w = false;
		}
		else if (id == KeyEvent.VK_S) { // when the 'S' key is released, variable s becomes false
			
			s = false;
		}
		else if (id == KeyEvent.VK_UP) { // when the 'UP' key is released, variable up becomes false
			
			up = false;
		}
		else if (id == KeyEvent.VK_DOWN) { // when the 'DOWN' key is released, variable down becomes false
			
			down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}