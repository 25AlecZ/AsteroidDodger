package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener, gameObject{
	
	private Timer timeLived;
	private int health;
	private int speed;
	private Polygon shape;
	private boolean upPressed, downPressed, leftPressed, rightPressed;
	private boolean gameOver = false;
	private boolean active = true;
	
	public Player(int x, int y, int health, int speed) {
		this.health = health;
		this.speed = speed;
		this.timeLived = new Timer();
		Point[] points = {
				new Point(0, 0), new Point(4, 10), new Point(8, 20),
			    new Point(8, 40), new Point(10, 60),
			    new Point(20, 80), new Point(8, 80),
			    new Point(0, 90),
			    new Point(-8, 80), new Point(-20, 80),
			    new Point(-10, 60), new Point(-8, 40),
			    new Point(-8, 20), new Point(-4, 10), new Point(0, 0)
		};
		this.shape = new Polygon(points, new Point(x,y), 0);
	}
	
	@Override
	public void move(int screenWidth) {
		if(!gameOver) {
		    if (upPressed) {moveY(true);}
		    if (downPressed) {moveY(false);}
		    if (leftPressed) {moveX(true);}
		    if (rightPressed) {moveX(false);}
		    int minLocalX = -20, maxLocalX = 20;
		    int minLocalY = 0,   maxLocalY = 90;

		    if (shape.position.x < -minLocalX) {
		        shape.position.x = -minLocalX;
		    } else if (shape.position.x > screenWidth - maxLocalX) {
		        shape.position.x = screenWidth - maxLocalX;
		    }
		    if (shape.position.y < -minLocalY) {
		        shape.position.y = -minLocalY;
		    } else if (shape.position.y > screenWidth - maxLocalY) {
		        shape.position.y = screenWidth - maxLocalY;
		    }
		}
	}
	
	@Override
	public Polygon getShape() {
		return shape;
	}
	
	@Override
	public void draw(Graphics g) {
	    g.setColor(Color.RED);
	    Point[] pts = shape.getPoints();
	    int[] xs = new int[pts.length], ys = new int[pts.length];
	    for (int i = 0; i < pts.length; i++) {
	        xs[i] = (int) pts[i].x;
	        ys[i] = (int) pts[i].y;
	    }
	    g.fillPolygon(xs, ys, pts.length);
	}
	
	@Override
    public boolean isActive() {
		return active; 
	}

    @Override
    public void setActive(boolean active) {
    	this.active = active; 
    }
    
	@Override
	public void keyTyped(KeyEvent e) {}
	
	
	@Override
	//still need to add keys for moving up, left, right
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			upPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = true;
			break;
		case KeyEvent.VK_RIGHT: 
			rightPressed = true;
			break;
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				upPressed = false;
				break;
			case KeyEvent.VK_DOWN:
				downPressed = false;
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = false;
				break;
			case KeyEvent.VK_RIGHT: 
				rightPressed = false;
				break;
		}
	}
	
	public void setGameOver() {
		gameOver = true;
		active = false;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public int getTimeLived() {
		return timeLived.getSeconds();
	}
	
	public void incrementTimer() {
		this.timeLived.seconds++;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void decrementHealth() {
		health--;
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
