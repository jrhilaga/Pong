package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle {
	

	public int paddleNumber;

	public int x, y, width = 25, height = 100; // dimension of the paddle

	public int score; 

	public Paddle(Pong pong, int paddleNumber) {
		
		this.paddleNumber = paddleNumber;

		if (paddleNumber == 1) { 
			
			this.x = 0; //positions the paddle to the leftmost
		}

		if (paddleNumber == 2) {
			
			this.x = pong.width - width; //positions the paddle to the rightmost
		}

		this.y = pong.height / 2 - this.height / 2; //positions the paddle to the middle
	}

	public void render(Graphics g) {
		
		if (paddleNumber == 1) {
			
			g.setColor(Color.RED); //sets the color of player 1's paddle to RED
		}

		if (paddleNumber == 2) {
			
			g.setColor(Color.BLUE); //sets the color of player 1's paddle to RED
		}
		g.fillRect(x, y, width, height);
	}
	
	public void move(boolean up) { // responsible for the movement of the paddle: up or down
		
		int speed = 15;

		if (up) { 
			
			if (y - speed > 0) {
				
				y -= speed;
			}
			else {
				
				y = 0;
			}
		}
		else { 
			
			if (y + height + speed < Pong.pong.height) {
				
				y += speed;
			}
			else {
				
				y = Pong.pong.height - height;
			}
		}
	}

}