package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener{
	
	private Timer timeLived;
	private int lives;
	private int speed;
	private Polygon shape;
	
	public Player(int x, int y, int lives, int speed) {
		this.lives = lives;
		this.speed = speed;
		this.timeLived = new Timer();
		
		Point[] points = {new Point(0,50), new Point(10,0), new Point(20, 50)};
		this.shape = new Polygon(points, new Point(x,y), 0);
	}
	
	public Polygon getShape() {
		return shape;
	}
	
	public void moveY(boolean up) {
		if(up) {
			shape.position.y -= speed;
		} else {
			shape.position.y += speed;

		}
		
	}
	
	public void moveX(boolean left) {
		if(left) {
			shape.position.x -= speed;
		} else {
			shape.position.x += speed;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	//still need to add keys for moving up, left, right
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			moveY(false);
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			moveY(true);
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			moveX(true);
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveX(false);
		}
			
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	private class Timer {
		int seconds;
		
		public Timer() {
			this.seconds = 0;
		}
		
		public int getSeconds() {
			return seconds;
		}
		
		public void increment() {
			seconds++;
		}
	}

	
}
