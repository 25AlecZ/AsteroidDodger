package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Represents the player character of the game. Has a timer that will end the game if 
 * it reaches 0, has a health that will also end the game when it reaches 0, 
 * and is controlled using the arrow keys
 * 
 *  @author Alec, Vedant, and Saanvi
 */

public class Player implements KeyListener, GameObject{
	
	private Timer timeLived;
	private int health;
	private int speed;
	private Polygon shape;
	private boolean upPressed, downPressed, leftPressed, rightPressed;
	private boolean gameOver = false;
	private boolean active = true;
	
	/**
     * Create a player character centered at (x,y)
     * @param x coordinate
     * @param y coordinate
     * @param player's health
     * @param speed
     */
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
	
	/**
     * 
     * Helps the player character move based on what key is pressed without leaving
     * the screen's width
     * 
     * @param screenWidth
     */
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
	
	/**
	 * returns the Polygon associated with the asteroid
	 * 
	 * @return Polygon
	 */
	@Override
	public Polygon getShape() {
		return shape;
	}
	
	/**
	 * Sets the color to red and draws the polygon based on its points
	 * 
	 * @param Graphics g
	 */
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
	
	/**
	 * returns if the HealthPack is active or not
	 * @return boolean
	 */
	@Override
    public boolean isActive() {
		return active; 
	}

	/**
	 * assigns active to the parameter active
	 * 
	 * @param active
	 */
    @Override
    public void setActive(boolean active) {
    	this.active = active; 
    }
    
    /**
	 * checked what key was typed in 
	 * 
	 * @param KeyEvent e
	 */
	@Override
	public void keyTyped(KeyEvent e) {}
	
	
	/**
	 * based on the pressed key, it sets one of the booleans for each respective key 
	 * to true
	 * 
	 * @param KeyEvent e
	 */
	@Override
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

	/**
	 * when the key is released, it sets the boolean for the respective key to be 
	 * false again
	 * 
	 * @param KeyEvent e
	 */
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
	
	/**
	 * Sets game to be over and active to be false
	 *
	 */
	public void setGameOver() {
		gameOver = true;
		active = false;
	}
	
	/**
	 * returns true if the game is over
	 * 
	 * @return gameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	
	/**
	 * returns how long the player has been alive for 
	 * 
	 * @param int
	 */
	public int getTimeLived() {
		return timeLived.getSeconds();
	}
	
	/**
	 * increments the player's timer
	 * 
	 */
	public void incrementTimer() {
		this.timeLived.seconds++;
	}
	
	/**
	 * returns the player's current health
	 * 
	 * @param int health
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Decrements the player's health by 1
	 * 
	 */
	public void decrementHealth() {
		health--;
	}
	
	/**
	 * Increments the player's health by 1
	 * 
	 */
	public void incrementHealth() {
	    health++;
	}
	
	/**
	 * Move's the player up based on its speed if up is true, or down if up is false
	 * 
	 * @param boolean up
	 * 
	 */
	public void moveY(boolean up) {
		if(up) {
			shape.position.y -= speed;
			
		} else {
			shape.position.y += speed;

		}
		
	}
	
	/**
	 * Move's the player up based on its speed if left is true, or right if left is false
	 * 
	 * @param boolean left
	 */
	public void moveX(boolean left) {
		if(left) {
			shape.position.x -= speed;
		} else {
			shape.position.x += speed;
		}
	}
	
	/**
	 * A private class that acts as the timer, keeping track of the time that the player
	 * has lived for 
	 * 
	 *  @author Alec, Vedant, and Saanvi
	 */
	private class Timer {
		int seconds;
		
		/**
	     * Sets the timer to begin at 0 seconds
	     */
		public Timer() {
			this.seconds = 0;
		}
		
		/**
	     * returns the number of seconds recorded for the player character
	     * 
	     * @return int seconds
	     */
		
		public int getSeconds() {
			return seconds;
		}
		
		/**
	     * increases the timer by 1 second
	     */
		public void increment() {
			seconds++;
		}
	}

	
}
