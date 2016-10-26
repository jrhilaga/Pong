package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball {
	
	public int x, y, width = 25, height = 25; //dimensions of the ball

	public int moveX, moveY; //position of ball

	public Random random;

	private Pong pong;

	public int amountOfHits; // counter for amount of hits: the greater the amount, the faster the fall

	public Ball(Pong pong) {
		
		this.pong = pong;

		this.random = new Random();

		spawn(); 
	}

	public void update(Paddle paddle1, Paddle paddle2) { //movement of the ball
		
		int speed = 5; //speed of ball

		this.x += moveX * speed;
		this.y += moveY * speed;

		if (this.y + height - moveY > pong.height || this.y + moveY < 0) { 
			
			if (this.moveY < 0) {
				
				this.y = 0;
				this.moveY = random.nextInt(3);

				if (moveY == 0) {
					
					moveY = 1;
				}
			}
			else {
				
				this.moveY = -random.nextInt(3);
				this.y = pong.height - height;

				if (moveY == 0) {
					
					moveY = -1;
				}
			}
		}

		if (checkCollision(paddle1) == 1) {
			
			this.moveX = 1 + (amountOfHits / 5);
			this.moveY = -2 + random.nextInt(3);

			if (moveY == 0) {
				
				moveY = 1;
			}

			amountOfHits++;
		}
		else if (checkCollision(paddle2) == 1) {
			
			this.moveX = -1 - (amountOfHits / 5);
			this.moveY = -2 + random.nextInt(3);

			if (moveY == 0) {
				
				moveY = 1;
			}

			amountOfHits++;
		}

		if (checkCollision(paddle1) == 2) {
			
			paddle2.score++;
			spawn();
		}
		else if (checkCollision(paddle2) == 2) {
			
			paddle1.score++;
			spawn();
		}
	}

	public void spawn() { //spawns the ball at the center of the field
		
		this.amountOfHits = 0;
		this.x = pong.width / 2 - this.width / 2;
		this.y = pong.height / 2 - this.height / 2;

		this.moveY = -2 + random.nextInt(3);

		if (moveY == 0) {
			
			moveY = 1;
		}

		if (random.nextBoolean()) {
			
			moveX = 1;
		}
		else {
			
			moveX = -1;
		}
	}

	public int checkCollision(Paddle paddle) { //checks whether the ball has hit something which is either the walls up and down or the paddles
		
		if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y) {
			
			return 1; //bounces
		}
		else if ((paddle.x > x && paddle.paddleNumber == 1) || (paddle.x < x - width && paddle.paddleNumber == 2)) {
			
			return 2; //scores
		}

		return 0; //do nothing
	}

	public void render(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
	}

}